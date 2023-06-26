package dev.mvc.notice;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.member.MemberProcInter;

import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class NoticeCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc = null;;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;;
  
  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc") 
  private NoticeProcInter noticeProc;
  
  public NoticeCont () {
    System.out.println("-> NoticeCont created.");
  		}
  
  // 등록 폼, notice 테이블은 FK로 noticeno를 사용함.
  // http://localhost:9093/notice/create.do
  @RequestMapping(value="/notice/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
    	
    	mav.setViewName("/notice/create"); // /webapp/WEB-INF/views/notice/create.jsp
    	
    } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }    
    
    return mav;
  }
  
  /**
   * 등록 처리 http://localhost:9093/notice/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/notice/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한 경우   
    	// ------------------------------------------------------------------------------
        // 파일 전송 코드 시작
        // ------------------------------------------------------------------------------
        String file1 = "";          // 원본 파일명 image
        String file1saved = "";   // 저장된 파일명, image
        String thumb1 = "";     // preview image

        String upDir =  Notice.getUploadDir();
        System.out.println("-> upDir: " + upDir);
        
        // 전송 파일이 없어도 file1MF 객체가 생성됨.
        // <input type='file' class="form-control" name='file1MF' id='file1MF' 
        //           value='' placeholder="파일 선택">
        MultipartFile mf = noticeVO.getFile1MF();
        
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
        
        noticeVO.setFile1(file1);   // 순수 원본 파일명
        noticeVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        noticeVO.setThumb1(thumb1);      // 원본 이미지 축소판
        noticeVO.setSize1(size1);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
       
        // Call By Reference: 메모리 공유, Hashcode 전달
        int adminno = (int)session.getAttribute("adminno"); // adminno FK
        noticeVO.setAdminno(adminno);
        int cnt = this.noticeProc.create(noticeVO);
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> noticeno: " + noticeVO.getNoticeno());
        // mav.addObject("noticeno", noticeVO.getNoticeno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {

        	//mav.addObject("code", "create_success");
        	mav.setViewName("redirect:/notice/list_all.do");     // 목록으로 자동 이동
                    
        } else {
          mav.addObject("code", "create_fail");
          
        }
        mav.addObject("cnt", cnt);
 
        
    } else {
        mav.setViewName("/admin/login_need"); 
      }
    
    return mav;

	}
  
  /**
   * 목록
   * http://localhost:9093/notice/list.all.do
   * @return
   */
  @RequestMapping(value="/notice/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("/notice/list_all"); // /WEB-INF/views/notice/list_all.jsp
  
    ArrayList<NoticeVO> list = this.noticeProc.list_all();
    mav.addObject("list", list); 
    
    mav.setViewName("/notice/list_all"); // /webapp/WEB-INF/views/notice/list_all.jsp

    return mav;
  }
  
  /**
   * 조회
   *  http://localhost:9093/notice/read.do
   * @return
   */
  @RequestMapping(value="/notice/read.do", method=RequestMethod.GET )
  public ModelAndView read(int noticeno, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();
    
    NoticeVO read = noticeProc.read(noticeno);
    
Cookie[] cookies = request.getCookies();
    
    //쿠키 중복 체크를 위해 사용되는 변수
    Cookie viewCookie = null;
    
  // 쿠키가 있을 경우 
  if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
          // Cookie의 name이 cookie + noticeno와 일치하는 쿠키를 viewCookie에 넣어줌 
          if (cookies[i].getName().equals("cookie"+noticeno)){ 
              //System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
              viewCookie = cookies[i];
              viewCookie.setMaxAge(30); // 30 seconds
          }
      }
  }

  if (read != null) {
 // System.out.println("System - 해당 상세 페이지로 넘어감");
  
    mav.addObject("read", read);
  
  // 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
  if (viewCookie == null) {    
      //System.out.println("cookie 없음");
      
      // 쿠키 생성(이름, 값)
      Cookie newCookie = new Cookie("cookie"+noticeno, "|" + noticeno + "|");
      newCookie.setMaxAge(30); // 30 seconds
      
      // 쿠키 추가
      response.addCookie(newCookie);
      
      // 쿠키를 추가 시키고 조회수 증가시킴
      int result = noticeProc.views(noticeno);
      
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
      String value = viewCookie.getValue();
      //viewCookie.setMaxAge(60 * 60 * 24); // 1 day
      viewCookie.setMaxAge(30); // 30 seconds
      //System.out.println("cookie 값 : " + value);
  
    }
  }

    if (adminProc.isAdmin(session) || memberProc.isMember(session)) { // 관리자, 회원으로 로그인한 경우        
    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    
    String title = noticeVO.getNtitle();
    String content = noticeVO.getNcontent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    noticeVO.setNtitle(title);
    noticeVO.setNcontent(content);  
    
    long size1 = noticeVO.getSize1();
    noticeVO.setSize1_label(Tool.unit(size1)); 
    
    mav.addObject("noticeVO", noticeVO); // request.setAttribute("noticeVO", noticeVO);

    // 관리자 번호: adminno -> MasterVO -> mname
    String mname = this.adminProc.read(noticeVO.getAdminno()).getMname();
    mav.addObject("mname", mname);

    mav.setViewName("/notice/read"); // /WEB-INF/views/notice/read.jsp
    } else{ // 정상적인 로그인이 아닌 경우
    	mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
    }
    
    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9093/notice/youtube.do?noticeno=1
   * @return
   */
  @RequestMapping(value="/notice/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int noticeno, HttpSession session) {
    ModelAndView mav = new ModelAndView();
  
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한 경우       

    NoticeVO noticeVO = this.noticeProc.read(noticeno); // youtube 정보 읽어 오기
    mav.addObject("noticeVO", noticeVO); // request.setAttribute("noticeVO", noticeVO);

    mav.setViewName("/notice/youtube"); // /WEB-INF/views/notice/youtube.jsp
    } else{ // 정상적인 로그인이 아닌 경우
    mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 처리
   * http://localhost:9093/notice/youtube.do
   * @param noticeVO
   * @return
   */
  @RequestMapping(value="/notice/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    if (noticeVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      // youtube 영상의 크기를 width 기준 640 px로 변경 
      String youtube = Tool.youtubeResize(noticeVO.getYoutube());
      noticeVO.setYoutube(youtube);
    }
    
    this.noticeProc.youtube(noticeVO);
    
    mav.setViewName("redirect:/notice/read.do?noticeno=" + noticeVO.getNoticeno()); 
    // /webapp/WEB-INF/views/notice/read.jsp
    
    return mav;
  }
  
  /**
   * 패스워드 일치 검사
   * http://localhost:9093/notice/password_check.do?noticeno=1&passwd=1234
   * @return
   */
  @RequestMapping(value="/notice/password_check.do", method=RequestMethod.GET )
  public ModelAndView password_check(NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();

    int cnt = this.noticeProc.password_check(noticeVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 0) {
      mav.setViewName("/notice/passwd_check"); // /WEB-INF/views/notice/passwd_check.jsp
    }
        
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9093/notice/update.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int noticeno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    mav.addObject("noticeVO", noticeVO);   
    
    mav.setViewName("/notice/update"); // /WEB-INF/views/notice/update.jsp
    }else { // 정상적인 로그인이 아닌 경우
    	mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9093/notice/update.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update.do", method = RequestMethod.POST)
  public ModelAndView update(HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    //System.out.println("title: " + noticeVO.getNtitle());   
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      this.noticeProc.update(noticeVO);  
      
      mav.addObject("noticeno", noticeVO.getNoticeno());
      mav.setViewName("redirect:/notice/read.do");
      
    } else { // 정상적인 로그인이 아닌 경우
      if (this.noticeProc.password_check(noticeVO) == 1) {
        this.noticeProc.update(noticeVO);  
         
        // mav 객체 이용
        mav.addObject("noticeno", noticeVO.getNoticeno());
        mav.setViewName("redirect:/notice/read.do");
        
      } else {
        mav.addObject("url", "/notice/passwd_check"); // /WEB-INF/views/notice/passwd_check.jsp
        mav.setViewName("redirect:/notice/list_all.do");  // POST -> GET -> JSP 출력
      }    
    }
    
    return mav; // forward
  }
  
  /**
   * 파일 수정 폼
   * http://localhost:9093/notice/update_file.do?noticeno=1
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int noticeno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) { // 로그인
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      mav.addObject("noticeVO", noticeVO);
    
      mav.setViewName("/notice/update_file"); // /WEB-INF/views/notice/update_file.jsp
      }else{ // 정상적인 로그인이 아닌 경우
     mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
     }

    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9093/notice/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/notice/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      NoticeVO noticeVO_old = noticeProc.read(noticeVO.getNoticeno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = noticeVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = noticeVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Notice.getUploadDir(); // C:/kd/deploy/team2_v2sbm3c/notice/storage/
      
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
      MultipartFile mf = noticeVO.getFile1MF();
          
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
          
      noticeVO.setFile1(file1);
      noticeVO.setFile1saved(file1saved);
      noticeVO.setThumb1(thumb1);
      noticeVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.noticeProc.update_file(noticeVO); // Oracle 처리

      mav.addObject("noticeno", noticeVO.getNoticeno());
      mav.setViewName("redirect:/notice/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/notice/msg.do"); // GET
    }
    
    return mav; // forward
  }   
  
  /**
   * 삭제 폼
   * @param noticeno
   * @return
   */
  @RequestMapping(value="/notice/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(HttpSession session, int noticeno) { 
    ModelAndView mav = new  ModelAndView();
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      // 삭제할 정보를 조회하여 확인
      NoticeVO noticeVO = this.noticeProc.read(noticeno);
      mav.addObject("noticeVO", noticeVO);
    
      mav.setViewName("/notice/delete");  // /webapp/WEB-INF/views/notice/delete.jsp
    
      }else {
    	mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
    
      return mav; 
  }
  
  /**
   * 삭제 처리 http://localhost:9093/notice/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/notice/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(HttpSession session, NoticeVO noticeVO) {
    ModelAndView mav = new ModelAndView();
    
    //NoticeVO noticeVO_read = this.noticeProc.read(noticeVO.getNoticeno()); 

    this.noticeProc.delete(noticeVO.getNoticeno()); // DBMS 삭제
    
    if (this.adminProc.isAdmin(session)) { // 관리자 로그인
      mav.setViewName("redirect:/notice/list_all.do");
    
      }else {
    	mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
    
      return mav;
  }   

}
