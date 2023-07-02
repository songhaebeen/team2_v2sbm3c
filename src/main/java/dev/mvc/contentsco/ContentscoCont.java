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
        this.catecoProc.update_cnt_add(contentscoVO.getCatecono()); // cate 테이블 글 수 증가
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
//  @RequestMapping(value="/contentsco/list_by_catecono.do", method=RequestMethod.GET)
//  public ModelAndView list_by_catecono(int catecono) {
//    ModelAndView mav = new ModelAndView();
//    
//    CatecoVO catecoVO = this.catecoProc.read(catecono);
//    mav.addObject("catecoVO", catecoVO);
//        
//    ArrayList<ContentscoVO> list = this.contentscoProc.list_by_catecono(catecono);
//    mav.addObject("list", list);
//    
//    mav.setViewName("/contentsco/list_by_catecono"); 
//    
//    return mav;
//  }
  
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
   // http://localhost:9093/contentsco/youtube.do?contentscono=3
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
  
   // 특정 카테고리의 검색된 글목록
   // http://localhost:9093/contentsco/list_by_catecono.do?catecono=8&word=화장품

//  @RequestMapping(value="/contentsco/list_by_catecono.do", method=RequestMethod.GET)
//  public ModelAndView list_by_catecono_search(ContentscoVO contentscoVO) {
//    ModelAndView mav = new ModelAndView();
//    
//    CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono());
//    mav.addObject("catecoVO", catecoVO);
//        
//    ArrayList<ContentscoVO> list = this.contentscoProc.list_by_catecono_search(contentscoVO);
//    mav.addObject("list", list);
//    
//    mav.setViewName("/contentsco/list_by_catecono_search"); // /webapp/WEB-INF/views/contents/list_by_cateno_search.jsp
//    
//    return mav;
//  }
  

 // 목록 + 검색 + 페이징 지원
 // http://localhost:9093/contentsco/list_by_catecono.do?catecono=2&word=화장품&now_page=1

@RequestMapping(value = "/contentsco/list_by_catecono.do", method = RequestMethod.GET)
public ModelAndView list_by_catecono_search_paging(ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();

  // 검색된 전체 글 수
  int search_count = this.contentscoProc.search_count(contentscoVO);
  mav.addObject("search_count", search_count);
  
  // 검색 목록
  ArrayList<ContentscoVO> list = contentscoProc.list_by_catecono_search_paging(contentscoVO);
  mav.addObject("list", list);

  CatecoVO catecoVO = catecoProc.read(contentscoVO.getCatecono());
  mav.addObject("catecoVO", catecoVO);

  /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param cateno 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
  String paging = contentscoProc.pagingBox(contentscoVO.getCatecono(), contentscoVO.getNow_page(), contentscoVO.getWord(), "list_by_catecono.do");
  mav.addObject("paging", paging);

  // mav.addObject("now_page", now_page);
  
  mav.setViewName("/contentsco/list_by_catecono_search_paging");  // /contents/list_by_cateno_search_paging.jsp

  return mav;
}


 // 목록 + 검색 + 페이징 + Grid(갤러리) 지원
 // http://localhost:9093/contentsco/list_by_catecono_grid.do?catecono=2&word=화장품&now_page=1

@RequestMapping(value = "/contentsco/list_by_catecono_grid.do", method = RequestMethod.GET)
public ModelAndView list_by_catecono_search_paging_grid(ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();

  // 검색된 전체 글 수
  int search_count = this.contentscoProc.search_count(contentscoVO);
  mav.addObject("search_count", search_count);
  
  // 검색 목록
  ArrayList<ContentscoVO> list = contentscoProc.list_by_catecono_search_paging(contentscoVO);
  mav.addObject("list", list);

  CatecoVO catecoVO = catecoProc.read(contentscoVO.getCatecono());
  mav.addObject("catecoVO", catecoVO);

  /*
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
   * 18 19 20 [다음]
   * @param catecono 카테고리번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @return 페이징용으로 생성된 HTML/CSS tag 문자열
   */
  String paging = contentscoProc.pagingBox(contentscoVO.getCatecono(), contentscoVO.getNow_page(), contentscoVO.getWord(), "list_by_catecono_grid.do");
  mav.addObject("paging", paging);

  // mav.addObject("now_page", now_page);
  
  mav.setViewName("/contentsco/list_by_catecono_search_paging_grid");  // /contents/list_by_cateno_search_paging_grid.jsp

  return mav;
}

/**
 * 패스워드 검사
 * http://localhost:9093/contentsco/password_check.do?contentscono=2&passwd=123
 * @return
 */
@RequestMapping(value="/contentsco/password_check.do", method=RequestMethod.GET )
public ModelAndView password_check(ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();

  int cnt = this.contentscoProc.password_check(contentscoVO);
  System.out.println("-> cnt: " + cnt);
  
  if (cnt == 0) {
    mav.setViewName("/contentsco/passwd_check"); // /WEB-INF/views/contents/passwd_check.jsp
  }
      
  return mav;
}


 // 수정 폼
 // http://localhost:9093/contentsco/update_text.do?contentscono=1
@RequestMapping(value = "/contentsco/update_text.do", method = RequestMethod.GET)
public ModelAndView update_text(int contentscono) {
  ModelAndView mav = new ModelAndView();
  
  ContentscoVO contentscoVO = this.contentscoProc.read(contentscono);
  mav.addObject("contentscoVO", contentscoVO);
  
  CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono());
  mav.addObject("catecoVO", catecoVO);
  
  mav.setViewName("/contentsco/update_text"); // /WEB-INF/views/contents/update_text.jsp

  return mav; // forward
}

 // 수정 처리
 // http://localhost:9093/contentsco/update_text.do?contentscono=1

@RequestMapping(value = "/contentsco/update_text.do", method = RequestMethod.POST)
public ModelAndView update_text(HttpSession session, ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();
  
  // System.out.println("-> word: " + contentsVO.getWord());
  
  if (this.adminProc.isAdmin(session)) { // 관리자 로그인
    this.contentscoProc.update_text(contentscoVO);  
    
    mav.addObject("contentscono", contentscoVO.getContentscono());
    mav.addObject("catecono", contentscoVO.getCatecono());
    mav.setViewName("redirect:/contentsco/read.do");
  } else { // 정상적인 로그인이 아닌 경우
    if (this.contentscoProc.password_check(contentscoVO) == 1) {
      this.contentscoProc.update_text(contentscoVO);  
       
      // mav 객체 이용
      mav.addObject("contentscono", contentscoVO.getContentscono());
      mav.addObject("catecono", contentscoVO.getCatecono());
      mav.setViewName("redirect:/contentsco/read.do");
    } else {
      mav.addObject("url", "/contentsco/passwd_check"); // /WEB-INF/views/contents/passwd_check.jsp
      mav.setViewName("redirect:/contentsco/msg.do");  // POST -> GET -> JSP 출력
    }    
  }
  
  mav.addObject("now_page", contentscoVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
  
  // URL에 파라미터의 전송
  // mav.setViewName("redirect:/contents/read.do?contentscono=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
  
  return mav; // forward
}
   

 // 파일 수정 폼
 // http://localhost:9093/contentsco/update_file.do?contentscono=1

@RequestMapping(value = "/contentsco/update_file.do", method = RequestMethod.GET)
public ModelAndView update_file(int contentscono) {
  ModelAndView mav = new ModelAndView();
  
  ContentscoVO contentscoVO = this.contentscoProc.read(contentscono);
  mav.addObject("contentscoVO", contentscoVO);
  
  CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono());
  mav.addObject("catecoVO", catecoVO);
  
  mav.setViewName("/contentsco/update_file"); // /WEB-INF/views/contentsco/update_file.jsp

  return mav; // forward
}


 // 파일 수정 처리 
 // http://localhost:9093/contentsco/update_file.do

@RequestMapping(value = "/contentsco/update_file.do", method = RequestMethod.POST)
public ModelAndView update_file(HttpSession session, ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session)) {
    // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
    ContentscoVO contentscoVO_old = contentscoProc.read(contentscoVO.getContentscono());
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String file1saved = contentscoVO_old.getFile1saved();  // 실제 저장된 파일명
    String thumb1 = contentscoVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
    long size1 = 0;
       
    String upDir =  Contentsco.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/
    
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
    MultipartFile mf = contentscoVO.getFile1MF();
        
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
        
    contentscoVO.setFile1(file1);
    contentscoVO.setFile1saved(file1saved);
    contentscoVO.setThumb1(thumb1);
    contentscoVO.setSize1(size1);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
        
    this.contentscoProc.update_file(contentscoVO); // Oracle 처리

    mav.addObject("contentscono", contentscoVO.getContentscono());
    mav.addObject("catecono", contentscoVO.getCatecono());
    mav.setViewName("redirect:/contentsco/read.do"); // request -> param으로 접근 전환
              
  } else {
    mav.addObject("url", "/admin/login_need"); // login_need.jsp, redirect parameter 적용
    mav.setViewName("redirect:/contentsco/msg.do"); // GET
  }

  // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
  mav.addObject("now_page", contentscoVO.getNow_page());
  
  return mav; // forward
}   

 // 삭제 폼
@RequestMapping(value="/contentsco/delete.do", method=RequestMethod.GET )
public ModelAndView delete(int contentscono) { 
  ModelAndView mav = new  ModelAndView();
  
  // 삭제할 정보를 조회하여 확인
  ContentscoVO contentscoVO = this.contentscoProc.read(contentscono);
  mav.addObject("contentscoVO", contentscoVO);
  
  CatecoVO catecoVO = this.catecoProc.read(contentscoVO.getCatecono());
  mav.addObject("catecoVO", catecoVO);
  
  mav.setViewName("/contentsco/delete");  // /webapp/WEB-INF/views/contentsco/delete.jsp
  
  return mav; 
}


 // 삭제 처리 
// http://localhost:9093/contentsco/delete.do

@RequestMapping(value = "/contentsco/delete.do", method = RequestMethod.POST)
public ModelAndView delete(ContentscoVO contentscoVO) {
  ModelAndView mav = new ModelAndView();
  
  // -------------------------------------------------------------------
  // 파일 삭제 시작
  // -------------------------------------------------------------------
  // 삭제할 파일 정보를 읽어옴.
  ContentscoVO contentscoVO_read = contentscoProc.read(contentscoVO.getContentscono());
      
  String file1saved = contentscoVO.getFile1saved();
  String thumb1 = contentscoVO.getThumb1();
  
  String uploadDir = Contentsco.getUploadDir();
  Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
  Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
  // -------------------------------------------------------------------
  // 파일 삭제 종료
  // -------------------------------------------------------------------
      
  this.contentscoProc.delete(contentscoVO.getContentscono()); // DBMS 삭제
      
  // -------------------------------------------------------------------------------------
  // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
  // -------------------------------------------------------------------------------------    
  // 마지막 페이지의 마지막 10번째 레코드를 삭제후
  // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
  // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
  int now_page = contentscoVO.getNow_page();
  if (contentscoProc.search_count(contentscoVO) % Contentsco.RECORD_PER_PAGE == 0) {
    now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
    if (now_page < 1) {
      now_page = 1; // 시작 페이지
    }
  }
  // -------------------------------------------------------------------------------------

  mav.addObject("catecono", contentscoVO.getCatecono());
  mav.addObject("now_page", now_page);
  mav.setViewName("redirect:/contentsco/list_by_catecono.do"); 
  
  return mav;
}   
 
  // 특정 카테고리에 속한 모든 레코드 삭제
  // http://localhost:9093/contentsco/count_by_catecono.do?catecono=1
@RequestMapping(value = "/contentsco/count_by_catecono.do", method = RequestMethod.GET)
public String count_by_catecono(int catecono) {
  System.out.println("-> count: " + this.contentscoProc.count_by_catecono(catecono));
  return "";
}

// http://localhost:9093/contentsco/delete_by_catecono.do?catecono=2
// 파일 삭제 -> 레코드 삭제
@RequestMapping(value = "/contentsco/delete_by_catecono.do", method = RequestMethod.GET)
public String delete_by_catecono(int catecono) {
  ArrayList<ContentscoVO> list = this.contentscoProc.list_by_catecono(catecono);
  
  for(ContentscoVO contentscoVO : list) {
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String file1saved = contentscoVO.getFile1saved();
    String thumb1 = contentscoVO.getThumb1();
    
    String uploadDir = Contentsco.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
  }
  
  System.out.println("-> count: " + this.contentscoProc.delete_by_catecono(catecono));
  
  return "";

}

  }
  
  
  
  
  
  
  
  
  
