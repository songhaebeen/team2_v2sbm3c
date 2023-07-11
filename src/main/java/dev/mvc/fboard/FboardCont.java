package dev.mvc.fboard;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.good.GoodProcInter;
import dev.mvc.good.GoodVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.reply.ReplyProcInter;
import dev.mvc.reply.ReplyVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jdk.jfr.Description;

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
  
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc") 
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.good.GoodProc") 
  private GoodProcInter goodProc;
  
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
    
    if (fboardVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
        // youtube 영상의 크기를 width 기준 640 px로 변경 
        String youtube = Tool.youtubeResize(fboardVO.getYoutube().trim());
        fboardVO.setYoutube(youtube);
      }
    
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
   * 조회, 조회수 증가, 댓글수
   *  http://localhost:9093/fboard/read.do
   * @return
   */
  @RequestMapping(value="/fboard/read.do",  method=RequestMethod.GET )
  public ModelAndView read(int fboardno, HttpSession session, HttpServletRequest request, HttpServletResponse response, GoodVO goodVO) {
    ModelAndView mav = new ModelAndView();
    
    FboardVO read = fboardProc.read(fboardno);
    
    Cookie[] cookies = request.getCookies();
    
    //쿠키 중복 체크를 위해 사용되는 변수
    Cookie viewCookie = null;
    
  // 쿠키가 있을 경우 
  if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
          // Cookie의 name이 cookie + fboardno와 일치하는 쿠키를 viewCookie에 넣어줌 
          if (cookies[i].getName().equals("cookie"+fboardno)){ 
              //System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
              viewCookie = cookies[i];
              //viewCookie.setMaxAge(30); // 30 seconds
              viewCookie.setMaxAge(60 * 60 * 24); // 1 day
          }
      }
  }

	if (read != null) {
	//System.out.println("System - 해당 상세 페이지로 넘어감");
	
	  mav.addObject("read", read);
	
	// 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
	if (viewCookie == null) {    
	    //System.out.println("cookie 없음");
	    
	    // 쿠키 생성(이름, 값)
	    Cookie newCookie = new Cookie("cookie"+fboardno, "|" + fboardno + "|");
	    //newCookie.setMaxAge(30); // 30 seconds
	    newCookie.setMaxAge(60 * 60 * 24); // 1 day
	    
	    // 쿠키 추가
	    response.addCookie(newCookie);
	    
	    // 쿠키를 추가 시키고 조회수 증가시킴
	    int result = fboardProc.views(fboardno);
	    
	    if(result>0) {
	        //System.out.println("조회수 증가");
	        
	    }else {
	        //System.out.println("조회수 증가 에러");
	    }
	}
	// viewCookie가 null이 아닐 경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
	else {
	    //System.out.println("cookie 있음");
	    
	    // 쿠키 값 받아옴.
	    //String value = viewCookie.getValue();
	    viewCookie.setMaxAge(60 * 60 * 24); // 1 day
	    //viewCookie.setMaxAge(30); // 30 seconds
	    //System.out.println("cookie 값 : " + value);
	
		}
	}
	
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
    	
    	this.goodProc.findGood(goodVO);
    	//mav.addObject("goodVO", goodVO);
    	
    	 //회원 번호: memberno -> MemberVO -> id
    	String id = this.memberProc.read(fboardVO.getMemberno()).getId();
    	mav.addObject("mname", id);
    	
    	//mav.setViewName("/fboard/read"); // /WEB-INF/views/fboard/read.jsp
    	
    	// 댓글 기능 추가 
        //mav.setViewName("/fboard/read_ck_reply"); // /WEB-INF/views/fboard/read_cookie_reply.jsp
    	
    	// 댓글 + 더보기 버튼 기능 + 좋아요 추가 
        mav.setViewName("/fboard/read_ck_reply_add"); // /WEB-INF/views/fboard/read_ck_reply_add.jsp
        
        // -------------------------------------------------------------------------------
    	} else{ // 정상적인 로그인이 아닌 경우
    		mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
    		}
    
    	return mav;
      }
  
  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9093/fboard/youtube.do?fboardno=1
   * @return
   */
  @RequestMapping(value="/fboard/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int fboardno, HttpSession session) {
    ModelAndView mav = new ModelAndView();
	
		if (memberProc.isMember(session)) { // 회원으로 로그인한 경우       

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
      String youtube = Tool.youtubeResize(fboardVO.getYoutube().trim());
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
    //System.out.println("-> now_page: " +fboardVO.getNow_page() );
    //System.out.println("-> word: " +fboardVO.getWord() );
    
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
   * http://localhost:9093/fboard/update.do?fboardno=12
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int fboardno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.memberProc.isMember(session)) { // 로그인
      FboardVO fboardVO = this.fboardProc.read(fboardno);
      mav.addObject("fboardVO", fboardVO);
    
      mav.setViewName("/fboard/update"); // /WEB-INF/views/fboard/update.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("fboard", fboard);
      }else{ // 정상적인 로그인이 아닌 경우
		 mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
		 }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9093/fboard/update.do?fboardno=1
   * 
   * @return
   */
  @RequestMapping(value = "/fboard/update.do", method = RequestMethod.POST)
  public ModelAndView update(HttpSession session, FboardVO fboardVO) {
    ModelAndView mav = new ModelAndView();
    
    if (fboardVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
        // youtube 영상의 크기를 width 기준 640 px로 변경 
        String youtube = Tool.youtubeResize(fboardVO.getYoutube().trim());
        fboardVO.setYoutube(youtube);
      }
    
    // System.out.println("-> word: " + fboardVO.getWord());
    
    if (this.memberProc.isMember(session)) { // 로그인
      this.fboardProc.update(fboardVO);  
      
      mav.addObject("fboardno", fboardVO.getFboardno());
      mav.setViewName("redirect:/fboard/read.do");
    } else { // 정상적인 로그인이 아닌 경우
      if (this.fboardProc.password_check(fboardVO) == 1) {
        this.fboardProc.update(fboardVO);  
         
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
    }
    
    mav.addObject("now_page", fboardVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 데이터 저장 ★
    
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
    //fboardProc.count(fboardno);
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
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일 삭제
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
  
  /**
   * 좋아요 체크
   * http://localhost:9093/fboard/good.do?fboardno=2&memberno=3
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/fboard/good.do", 
                method = RequestMethod.GET,
                produces = "text/plain;charset=UTF-8")
  public String good(HttpSession session, GoodVO goodVO, Integer goodno, int fboardno) {
       
    int cnt = 0; //좋아요 체크
    int up_cnt = 0; //좋아요 증가
    int down_cnt = 0; //좋아요 감소
    boolean bol = this.memberProc.isMember(session);
    int findcnt = this.goodProc.findGood(goodVO);
    int flag = 0;
    
    System.out.println("memberno: " + session.getAttribute("memberno"));
    System.out.println("goodVO: " + goodVO);
    
    if ( bol &&  findcnt == 0) {  
      //회원 좋아요 1 증가
      cnt = goodProc.create(goodVO);
      //전체 좋아요 수 증가
      up_cnt = fboardProc.increaseRecom(fboardno);
      flag = 1;
      
     } else if(bol &&  findcnt == 1){
       //회원 좋아요 1 감소
       cnt = goodProc.delete(goodno);
       //좋아요 수 감소
       down_cnt = fboardProc.decreaseRecom(fboardno);
       flag = -1;
    }    
   
    FboardVO fboardVO = this.fboardProc.read(fboardno);
    
    JSONObject obj = new JSONObject();
    obj.put("recom", fboardVO.getRecom());
    obj.put("flag", flag);
    
    return obj.toString();
  }


}
