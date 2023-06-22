package dev.mvc.fboard;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cate.CateVO;
import dev.mvc.contents.Contents;
import dev.mvc.contents.ContentsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class FboardCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
  
  @Autowired
  @Qualifier("dev.mvc.fboard.FboardProc") 
  private FboardProcInter fboardProc;
  
  public FboardCont() {
	  System.out.println("-> FboardCont created.");
  }
  
  // 등록 폼, notice 테이블은 FK로 fboardno를 사용함.
  // http://localhost:9093/fboard/create.do
  @RequestMapping(value="/fboard/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.memberProc.isMember(session) == true) {
    	
    	mav.setViewName("/fboard/create"); // /webapp/WEB-INF/views/fboard/create.jsp
    	
    } else {
        mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
      }    
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9093/fboard/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    if (memberProc.isMember(session)) {  // 회원으로 로그인한 경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Fboard.getUploadDir();
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = fboardVO.getFile1MF();
      
      file1 = Tool.getFname(mf.getOriginalFilename()); // 원본 순수 파일명 산출
      System.out.println("-> file1: " + file1);
      
      long size1 = mf.getSize();  // 파일 크기
      
      if (size1 > 0) { // 파일 크기 체크
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성 후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
        }
        
      }    
      
      fboardVO.setFile1(file1);   // 순수 원본 파일명
      fboardVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
      fboardVO.setThumb1(thumb1);      // 원본 이미지 축소판
      fboardVO.setSize1(size1);  // 파일 크기
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------
      
      // Call By Reference: 메모리 공유, Hashcode 전달
      int memberno = (int)session.getAttribute("memberno"); // memberno FK
      fboardVO.setMemberno(memberno);
      int cnt = this.fboardProc.create(fboardVO); 
      
      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> fboardno: " + fboardVO.getFboardno());
      // mav.addObject("fboardno", fboardVO.getFboardno()); // redirect parameter 적용
      // ------------------------------------------------------------------------------
      
      if (cnt == 1) {

      	//mav.addObject("code", "create_success");
      	mav.setViewName("redirect:/fboard/list_all.do");     // 목록으로 자동 이동
                  
      } else {
        mav.addObject("code", "create_fail");
        
      }
      mav.addObject("cnt", cnt);

      
  } else {
      mav.setViewName("/member/login_need");
       
    }
    
    return mav; // forward
  }
  
//  /**
//   * 자유게시판에 등록된 글 목록, http://localhost:9093/fboard/list_all.do
//   * @return
//   */
//  @RequestMapping(value="/fboard/list_all.do", method=RequestMethod.GET)
//  public ModelAndView list_all() {
//    ModelAndView mav = new ModelAndView();
//    
//    ArrayList<FboardVO> list = this.fboardProc.list_all();
//    mav.addObject("list", list);
//    
//    mav.setViewName("/fboard/list_all"); // /webapp/WEB-INF/views/fboard/list_all.jsp
//    
//    return mav;
//  }
  
  /**
   * 조회
   *  http://localhost:9093/fboard/read.do
   * @return
   */
  @RequestMapping(value="/fboard/read.do", method=RequestMethod.GET )
  public ModelAndView read(int fboardno, HttpSession session) {
    ModelAndView mav = new ModelAndView();

    if (memberProc.isMember(session) || adminProc.isAdmin(session)) { // 관리자, 회원으로 로그인한 경우        
    	FboardVO fboardVO = this.fboardProc.read(fboardno);
    
    	String title = fboardVO.getFtitle();
    	String content = fboardVO.getFcontent();
    
    	title = Tool.convertChar(title);  // 특수 문자 처리
    	content = Tool.convertChar(content); 
    
    	fboardVO.setFtitle(title);
    	fboardVO.setFcontent(content);  
    
    	long size1 = fboardVO.getSize1();
    	fboardVO.setSize1_label(Tool.unit(size1)); 
    
    	mav.addObject("fboardVO", fboardVO); // request.setAttribute("fboardVO", fboardVO);

    	// 회원 번호: memberno -> MemberVO -> mname
//    	String mname = this.memberProc.read(fboardVO.getMemberno()).getMname();
//    	mav.addObject("mname", mname);

    	mav.setViewName("/fboard/read"); // /WEB-INF/views/fboard/read.jsp
    	} else{ // 정상적인 로그인이 아닌 경우
    		mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
    		}
    
    	return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9091/fboard/youtube.do?contentsno=1
   * @return
   */
  @RequestMapping(value="/fboard/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int fboardno, HttpSession session) {
	ModelAndView mav = new ModelAndView();
	
		if (memberProc.isMember(session)) { // 관리자, 회원으로 로그인한 경우       

		FboardVO fboardVO = this.fboardProc.read(fboardno); // youtube 정보 읽어 오기
		mav.addObject("fboardVO", fboardVO); // request.setAttribute("fboardVO", fboardVO);

		mav.setViewName("/fboard/youtube"); // /WEB-INF/views/fboard/youtube.jsp
		} else{ // 정상적인 로그인이 아닌 경우
		mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
		}

    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 처리
   * http://localhost:9093/fboard/youtube.do
   * @param fboardVO
   * @return
   */
  @RequestMapping(value="/fboard/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    if (fboardVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      // youtube 영상의 크기를 width 기준 640 px로 변경 
      String youtube = Tool.youtubeResize(fboardVO.getYoutube());
      fboardVO.setYoutube(youtube);
    }
    
    this.fboardProc.youtube(fboardVO);

    // youtube 크기 조절
    // <iframe width="1019" height="573" src="https://www.youtube.com/embed/Aubh5KOpz-4" title="교보문고에서 가장 잘나가는 일본 추리소설 베스트셀러 10위부터 1위까지 소개해드려요📚" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
    
    
    mav.setViewName("redirect:/fboard/read.do?fboardno=" + fboardVO.getFboardno()); 
    // /webapp/WEB-INF/views/fboard/read.jsp
    
    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 지원
   * http://localhost:9093/fboard/list_all.do?word=화장품&now_page=1
   * @return
   */
  @RequestMapping(value="/fboard/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_by_search_paging(FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    // 검색된 전체 글 수
    int search_count = this.fboardProc.search_count(fboardVO);
    mav.addObject("search_count", search_count);

    // 검색 목록: 검색된 레코드를 페이지 단위로 분할하여 가져옴
    ArrayList <FboardVO> list= this.fboardProc.list_by_search_paging(fboardVO);
    mav.addObject("list", list);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = fboardProc.pagingBox(fboardVO.getNow_page(), fboardVO.getWord(), "list_all.do");
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);

    mav.setViewName("/fboard/list_by_search_paging"); // /WEB-INF/views/fboard/list_by_search_paging.jsp
        
    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 + Grid(갤러리) 지원
   * http://localhost:9091/contents/list_by_cateno_grid.do?cateno=1&word=스위스&now_page=1
   * 
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/fboard/list_grid.do", method = RequestMethod.GET)
  public ModelAndView list_grid(FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();

    // 검색된 전체 글 수
    int search_count = this.fboardProc.search_count(fboardVO);
    mav.addObject("search_count", search_count);
    
    // 검색 목록
    ArrayList<FboardVO> list = fboardProc.list_by_search_paging(fboardVO);
    mav.addObject("list", list);


    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param cateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = fboardProc.pagingBox(fboardVO.getNow_page(), fboardVO.getWord(), "list_grid.do");
    mav.addObject("paging", paging);

    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/fboard/list_search_paging_grid");  // /fboard/list_search_paging_grid.jsp

    return mav;
  }
  
  /**
   * fboardno, passwd를 GET 방식으로 전달 받아 패스워드 일치 검사를 하고 결과 1또는 0을 Console에 출력하세요.
   * http://localhost:9093/fboard/password_check.do?fboardno=12&passwd=1234
   * @return
   */
  @RequestMapping(value="/fboard/password_check.do", method=RequestMethod.GET )
  public ModelAndView password_check(FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.fboardProc.password_check(fboardVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 0) {
      mav.setViewName("/fboard/passwd_check"); // /WEB-INF/views/fboard/passwd_check.jsp
    }
        
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9093/fboard/update_text.do?fboardno=12
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int fboardno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.memberProc.isMember(session)) { // 로그인
      FboardVO fboardVO = this.fboardProc.read(fboardno);
      mav.addObject("fboardVO", fboardVO);
    
      mav.setViewName("/fboard/update_text"); // /WEB-INF/views/fboard/update_text.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("fboard", fboard);
      }else{ // 정상적인 로그인이 아닌 경우
		 mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
		 }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9093/fboard/update_text.do?fboardno=1
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + fboardVO.getWord());
    
    if (this.memberProc.isMember(session)) { // 로그인
      this.fboardProc.update_text(fboardVO);  
      
      mav.addObject("fboardno", fboardVO.getFboardno());
      mav.setViewName("redirect:/fboard/read.do");
    } else { // 정상적인 로그인이 아닌 경우
      if (this.fboardProc.password_check(fboardVO) == 1) {
        this.fboardProc.update_text(fboardVO);  
         
        // mav 객체 이용
        mav.addObject("fboardno", fboardVO.getFboardno());
        mav.setViewName("redirect:/fboard/read.do");
      } else {
        mav.addObject("url", "/fboard/passwd_check"); // /WEB-INF/views/fboard/passwd_check.jsp
        mav.setViewName("redirect:/fboard/msg.do");  // POST -> GET -> JSP 출력
      }    
    }
    
    mav.addObject("now_page", fboardVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시 한 번 데이터 저장 ★
    
    return mav; // forward
  }
  
  /**
   * 파일 수정 폼
   * http://localhost:9093/fboard/update_file.do?fboardno=1
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int fboardno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.memberProc.isMember(session)) { // 로그인
      FboardVO fboardVO = this.fboardProc.read(fboardno);
      mav.addObject("fboardVO", fboardVO);
    
      mav.setViewName("/fboard/update_file"); // /WEB-INF/views/fboard/update_file.jsp
      }else{ // 정상적인 로그인이 아닌 경우
		 mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
		 }

    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9093/fboard/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.memberProc.isMember(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
    	FboardVO fboardVO_old = fboardProc.read(fboardVO.getFboardno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = fboardVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = fboardVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Fboard.getUploadDir(); // C:/kd/deploy/team2_v2sbm3c/fboard/storage/
      
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = fboardVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
          
      fboardVO.setFile1(file1);
      fboardVO.setFile1saved(file1saved);
      fboardVO.setThumb1(thumb1);
      fboardVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.fboardProc.update_file(fboardVO); // Oracle 처리

      mav.addObject("fboardno", fboardVO.getFboardno());
      mav.setViewName("redirect:/fboard/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/member/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/fboard/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", fboardVO.getNow_page());
    
    return mav; // forward
  }   
  
  /**
   * 삭제 폼
   * @param fboardno
   * @return
   */
  @RequestMapping(value="/fboard/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int fboardno) { 
    ModelAndView mav = new  ModelAndView();
    
    // 삭제할 정보를 조회하여 확인
    FboardVO fboardVO = this.fboardProc.read(fboardno);
    mav.addObject("fboardVO", fboardVO);
    
    mav.setViewName("/fboard/delete");  // /webapp/WEB-INF/views/fboard/delete.jsp
    
    return mav; 
  }
  
  /**
   * 삭제 처리 http://localhost:9093/fboard/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    FboardVO fboardVO_read = fboardProc.read(fboardVO.getFboardno());
        
    String file1saved = fboardVO.getFile1saved();
    String thumb1 = fboardVO.getThumb1();
    
    String uploadDir = Fboard.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.fboardProc.delete(fboardVO.getFboardno()); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = fboardVO.getNow_page();
    if (fboardProc.search_count(fboardVO) % Fboard.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/fboard/list_all.do"); 
    
    return mav;
  }   

}
