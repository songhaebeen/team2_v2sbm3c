package dev.mvc.contentsco;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cateco.CatecoProcInter;
import dev.mvc.cateco.CatecoVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ContentscoCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.cateco.CatecoProc")
  private CatecoProcInter catecoProc;
  
  @Autowired
  @Qualifier("dev.mvc.contentsco.ContentscoProc")
  private ContentscoProcInter contentscoProc;
  
  public ContentscoCont() {
    System.out.println("-> ContentscoCont created.");
  }
  
  // 등록 폼
  // http://localhost:9093/contentsco/create.do?catecono=2
  @RequestMapping(value="/contentsco/create.do", method=RequestMethod.GET)
  public ModelAndView create(int catecono) {
    //public ModelAndView create(HttpServletRequest request,  int cateno) {
    ModelAndView mav = new ModelAndView();
    
    CatecoVO catecoVO = this.catecoProc.read(catecono);
    mav.addObject("catecoVO", catecoVO);
    
    mav.setViewName("/contentsco/create");
    
    return mav;
  }
  
  // 등록 처리  
  @RequestMapping(value="/contentsco/create.do", method=RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, ContentscoVO contentscoVO) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Contentsco.getUploadDir();
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = contentscoVO.getFile1MF();
      
      file1 = Tool.getFname(mf.getOriginalFilename()); // 원본 순수 파일명 산출
      System.out.println("-> file1: " + file1);
      
      long size1 = mf.getSize();  // 파일 크기
      
      if (size1 > 0) { // 파일 크기 체크
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
        }
        
      }    
      
      contentscoVO.setFile1(file1);   // 순수 원본 파일명
      contentscoVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
      contentscoVO.setThumb1(thumb1);      // 원본이미지 축소판
      contentscoVO.setSize1(size1);  // 파일 크기
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------
      
      int adminno = (int)session.getAttribute("adminno"); // adminno FK
      contentscoVO.setAdminno(adminno);
      int cnt = this.contentscoProc.create(contentscoVO); 
      
      
      if (cnt == 1) {
          mav.addObject("code", "create_success");

      } else {
          mav.addObject("code", "create_fail");
      }
      mav.addObject("cnt", cnt); 
      
      mav.addObject("catecono", contentscoVO.getCatecono()); 
      
      mav.addObject("url", "/contentsco/msg"); 
      mav.setViewName("redirect:/contentsco/msg.do"); 

    } else {
      mav.addObject("url", "/admin/login_need"); 
      mav.setViewName("redirect:/contentsco/msg.do"); 
    }
    
    return mav; 
  }
  
  // 컨텐츠 글 목록
  // http://localhost:9093/contentsco/list_all.do
  @RequestMapping(value="/contentsco/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    
    ArrayList<ContentscoVO> list = this.contentscoProc.list_all();
    mav.addObject("list", list);
    
    mav.setViewName("/contentsco/list_all"); 
    
    return mav;
  }
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * @return
   */
  @RequestMapping(value="/contentsco/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  // 특정 카테고리 글 목록
  // http://localhost:9093/contentsco/list_by_catecono.do?catecono=2
  @RequestMapping(value="/contentsco/list_by_catecono.do", method=RequestMethod.GET)
  public ModelAndView list_by_catecono(int catecono) {
    ModelAndView mav = new ModelAndView();
    
    CatecoVO catecoVO = this.catecoProc.read(catecono);
    mav.addObject("catecoVO", catecoVO);
        
    ArrayList<ContentscoVO> list = this.contentscoProc.list_by_catecono(catecono);
    mav.addObject("list", list);
    
    mav.setViewName("/contentsco/list_by_catecono"); 
    
    return mav;
  }
  //  조회
  // http://localhost:9093/contentsco/read.do
  @RequestMapping(value="/contentsco/read.do", method=RequestMethod.GET )
  public ModelAndView read(int contentscono) {
    ModelAndView mav = new ModelAndView();

    ContentscoVO contentscoVO = this.contentscoProc.read(contentscono);
    
    String title = contentscoVO.getTitle();
    String content = contentscoVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    contentscoVO.setTitle(title);
    contentscoVO.setContent(content);  
    
    long size1 = contentscoVO.getSize1();
    contentscoVO.setSize1_label(Tool.unit(size1));    
    
    mav.addObject("contentscoVO", contentscoVO); // request.setAttribute("contentsVO", contentsVO);

    CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono()); // 그룹 정보 읽기
    mav.addObject("catecoVO", catecoVO); 

    String mname = this.adminProc.read(contentscoVO.getAdminno()).getMname();
    mav.addObject("mname", mname);
    
    mav.setViewName("/contentsco/read"); // /WEB-INF/views/contents/read.jsp
        
    return mav;
  }
  

   // Youtube 등록/수정/삭제 폼
   // http://localhost:9093/contentsco/youtube.do?contentscono=14
  @RequestMapping(value="/contentsco/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int contentscono) {
    ModelAndView mav = new ModelAndView();

    ContentscoVO contentscoVO = this.contentscoProc.read(contentscono);
    mav.addObject("contentscoVO", contentscoVO);
    
    CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono()); 
    mav.addObject("catecoVO", catecoVO); 

    mav.setViewName("/contentsco/youtube"); // /WEB-INF/views/contents/youtube.jsp

    return mav;
    
  }
  

   // Youtube 등록/수정/삭제 처리
   // http://localhost:9093/contentsco/youtube.do
  @RequestMapping(value="/contentsco/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(ContentscoVO contentscoVO) {
    ModelAndView mav = new ModelAndView();
    
    if (contentscoVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      // youtube 영상의 크기를 width 기준 640 px로 변경 
      String youtube = Tool.youtubeResize(contentscoVO.getYoutube());
      contentscoVO.setYoutube(youtube);
    }
    
    this.contentscoProc.youtube(contentscoVO);  
    
    mav.setViewName("redirect:/contentsco/read.do?contentscono=" + contentscoVO.getContentscono()); 
    // /webapp/WEB-INF/views/contents/read.jsp

    return mav;
  }
  }
  
  
  
  
  
  
  
  
  
