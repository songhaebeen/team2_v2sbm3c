package dev.mvc.cate;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.contents.Contents;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.tool.Tool;

@Controller
public class CateCont {
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")  // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc; // CateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") 
  private ContentsProcInter contentsProc;
  
  public CateCont() {
    System.out.println("-> CateCont created.");
  }
  
  // 등록폼
  // http://localhost:9091/cate/create.do
  @RequestMapping(value="/cate/create.do", method=RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    // System.out.println("-> CateCont create()");
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      // spring.mvc.view.prefix=/WEB-INF/views/
      // spring.mvc.view.suffix=.jsp
      mav.setViewName("/cate/create"); // /WEB-INF/views/cate/create.jsp      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }

  // 등록 처리
  // http://localhost:9091/cate/create.do
  @RequestMapping(value="/cate/create.do", method=RequestMethod.POST)
  public ModelAndView create(HttpSession session, CateVO cateVO) { // <form> 태그의 값이 자동으로 저장됨
    // request.getParameter("name"); 자동으로 실행
    // System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("admin_id") != null) {
      int cnt = this.cateProc.create(cateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "create_success");
        mav.setViewName("redirect:/cate/list_all.do");     // 목록으로 자동 이동
        
      } else {
        // request.setAttribute("code", "create_fail");
        mav.addObject("code", "create_fail");
        mav.setViewName("/cate/msg"); // /WEB-INF/views/cate/msg.jsp // 등록 실패 메시지 출력

      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // http://localhost:9091/cate/list_all.do
  @RequestMapping(value="/cate/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cate/list_all"); // /WEB-INF/views/cate/list_all.jsp
      
      ArrayList<CateVO> list = this.cateProc.list_all();
      mav.addObject("list", list);      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // http://localhost:9091/cate/read.do?cateno=1
  @RequestMapping(value="/cate/read.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session, int cateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cate/read"); // /WEB-INF/views/cate/read.jsp
      
      CateVO cateVO = this.cateProc.read(cateno);
      mav.addObject("cateVO", cateVO);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 수정폼
  // http://localhost:9091/cate/read_update.do?cateno=1
  // http://localhost:9091/cate/read_update.do?cateno=2
  // http://localhost:9091/cate/read_update.do?cateno=3
  @RequestMapping(value="/cate/read_update.do", method=RequestMethod.GET)
  public ModelAndView read_update(HttpSession session, int cateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cate/read_update"); // /WEB-INF/views/cate/read_update.jsp
      
      CateVO cateVO = this.cateProc.read(cateno); // 수정용 데이터
      mav.addObject("cateVO", cateVO);
      
      ArrayList<CateVO> list = this.cateProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      
    }
    
    return mav;
  }
  
  // 수정 처리
  @RequestMapping(value="/cate/update.do", method=RequestMethod.POST)
  public ModelAndView update(HttpSession session, CateVO cateVO) { // <form> 태그의 값이 자동으로 저장됨
//    System.out.println("-> cateno: " + cateVO.getCateno());
//    System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();

    if (this.adminProc.isAdmin(session) == true) {
      int cnt = this.cateProc.update(cateVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "update_success"); // 고전적인 jsp 방법 
        // mav.addObject("code", "update_success");
        mav.setViewName("redirect:/cate/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        // request.setAttribute("code", "update_fail");
        mav.addObject("code", "update_fail");
        mav.setViewName("/cate/msg"); // /WEB-INF/views/cate/msg.jsp
      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
 
  // 삭제폼, 수정폼을 복사하여 개발 
  // http://localhost:9091/cate/read_delete.do?cateno=1
  @RequestMapping(value="/cate/read_delete.do", method=RequestMethod.GET)
  public ModelAndView read_delete(HttpSession session, int cateno) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      CateVO cateVO = this.cateProc.read(cateno); // 수정용 데이터
      mav.addObject("cateVO", cateVO);
      
      ArrayList<CateVO> list = this.cateProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
      // 특정 카테고리에 속한 레코드 갯수를 리턴
      int count_by_cateno = this.contentsProc.count_by_cateno(cateno);
      mav.addObject("count_by_cateno", count_by_cateno);
      
      mav.setViewName("/cate/read_delete"); // /WEB-INF/views/cate/read_delete.jsp
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 삭제 처리, 수정 처리를 복사하여 개발
  // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
  /**
   * 카테고리 삭제
   * @param session
   * @param cateno 삭제할 카테고리 번호
   * @return
   */
  @RequestMapping(value="/cate/delete.do", method=RequestMethod.POST)
  public ModelAndView delete(HttpSession session, int cateno) { // <form> 태그의 값이 자동으로 저장됨
//    System.out.println("-> cateno: " + cateVO.getCateno());
//    System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno); // 자식 레코드 목록 읽기
      
      for(ContentsVO contentsVO : list) { // 자식 레코드 관련 파일 삭제
        // -------------------------------------------------------------------
        // 파일 삭제 시작
        // -------------------------------------------------------------------
        String file1saved = contentsVO.getFile1saved();
        String thumb1 = contentsVO.getThumb1();
        
        String uploadDir = Contents.getUploadDir();
        Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
        Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
        // -------------------------------------------------------------------
        // 파일 삭제 종료
        // -------------------------------------------------------------------
      }
      
      this.contentsProc.delete_by_cateno(cateno); // 자식 레코드 삭제     
            
      int cnt = this.cateProc.delete(cateno); // 카테고리 삭제
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cate/list_all.do");       // 자동 주소 이동, Spring 재호출
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/cate/msg"); // /WEB-INF/views/cate/msg.jsp
      }
      
      mav.addObject("cnt", cnt);
      
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  /**
   * 출력 순서 올림(상향, 10 등 -> 1 등), seqno: 10 -> 1
   * http://localhost:9091/cate/update_seqno_decrease.do?cateno=1
   * http://localhost:9091/cate/update_seqno_decrease.do?cateno=2
   * @param cateno
   * @return
   */
  @RequestMapping(value = "/cate/update_seqno_decrease.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_decrease(int cateno) {
    ModelAndView mav = new ModelAndView();
    
    // seqno 컬럼의 값이 1 초과(1<)일때만 감소를 할 수 있다.
    // 1) 특정 cateno에 해당하는 seqno 컬럼의 값을 알고 싶다. -> 출력하세요.
    CateVO cateVO = this.cateProc.read(cateno);
    int seqno = cateVO.getSeqno();
    System.out.println("-> cateno: " + cateno + " seqno: " + seqno);

    // 2) if 문을 이용한 분기
    if (seqno > 1) {
      int cnt = this.cateProc.update_seqno_decrease(cateno); 
      mav.addObject("cnt", cnt);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cate/list_all.do");

      } else {
        mav.addObject("code", "update_seqno_decrease_fail");
        mav.setViewName("/cate/msg"); 
      }
      
    } else {
      mav.setViewName("redirect:/cate/list_all.do");
    }
    
    return mav;
  }
  
  /**
   * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
   * http://localhost:9091/cate/update_seqno_increase.do?cateno=1
   * http://localhost:9091/cate/update_seqno_increase.do?cateno=2
   * @param cateno
   * @return
   */
  @RequestMapping(value = "/cate/update_seqno_increase.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_increase(int cateno) {
    ModelAndView mav = new ModelAndView();
    int cnt = this.cateProc.update_seqno_increase(cateno); 
    mav.addObject("cnt", cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cate/list_all.do");

    } else {
      mav.addObject("code", "update_seqno_increase_fail");
      mav.setViewName("/cate/msg"); 
    }
    
    return mav;
  }

  /**
   * 공개
   * http://localhost:9091/cate/update_visible_y.do?cateno=1
   * @param cateno
   * @return
   */
  @RequestMapping(value = "/cate/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int cateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/cate/list_all.do");
    
    this.cateProc.update_visible_y(cateno);
    
    return mav;
  }
  
  /**
   * 비공개
   * http://localhost:9091/cate/update_visible_n.do?cateno=1
   * @param cateno
   * @return
   */
  @RequestMapping(value = "/cate/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int cateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/cate/list_all.do");
    
    this.cateProc.update_visible_n(cateno);
    
    return mav;
  }
  
  /**
   * 글수 증가
   * http://localhost:9091/cate/update_cnt_add.do?cateno=1
   * @param cateno
   * @return 변경된 레코드 수
   */
  @RequestMapping(value = "/cate/update_cnt_add.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_add(int cateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/cate/list_all.do");
    
    this.cateProc.update_cnt_add(cateno);
    
    return mav;
  }
  
  /**
   * 글수 감소
   * http://localhost:9091/cate/update_cnt_sub.do?cateno=1
   * @param cateno
   * @return
   */  
  @RequestMapping(value = "/cate/update_cnt_sub.do", method = RequestMethod.GET)
  public ModelAndView update_cnt_sub(int cateno) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/cate/list_all.do");
    
    this.cateProc.update_cnt_sub(cateno);
    
    return mav;
  }
  
}






