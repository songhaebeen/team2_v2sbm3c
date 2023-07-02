package dev.mvc.cateco;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.contentsco.Contentsco;
import dev.mvc.contentsco.ContentscoProcInter;
import dev.mvc.contentsco.ContentscoVO;
import dev.mvc.tool.Tool;

@Controller
public class CatecoCont {
  @Autowired
  @Qualifier("dev.mvc.cateco.CatecoProc")  // @Component("dev.mvc.cate.CateProc")
  private CatecoProcInter catecoProc; // CateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.contentsco.ContentscoProc")
  private ContentscoProcInter contentscoProc;
  
  public CatecoCont() {
    System.out.println("-> CatecoCont created.");
  }
  
  // 등록폼
  // http://localhost:9093/cateco/create.do
  @RequestMapping(value="/cateco/create.do", method=RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
       //spring.mvc.view.prefix=/WEB-INF/views/
       //spring.mvc.view.suffix=.jsp
      mav.setViewName("/cateco/create"); // /WEB-INF/views/cate/create.jsp      
   } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }

    return mav;
  }

  // 등록 처리
  // http://localhost:9093/cateco/create.do
  @RequestMapping(value="/cateco/create.do", method=RequestMethod.POST)
  public ModelAndView create(HttpSession session, CatecoVO catecoVO) { // <form> 태그의 값이 자동으로 저장됨
     //request.getParameter("name"); 
    // System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("admin_id") != null) {
      int cnt = this.catecoProc.create(catecoVO);
    
      if (cnt == 1) {
         //request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
         //mav.addObject("code", "create_success");
        mav.setViewName("redirect:/cateco/list_all.do");     // 목록으로 자동 이동
        
      } else {
         //request.setAttribute("code", "create_fail");
        mav.addObject("code", "create_fail");
        mav.setViewName("/cateco/msg"); // /WEB-INF/views/cate/msg.jsp // 등록 실패 메시지 출력

      }
      
       //request.setAttribute("cnt", cnt);
     mav.addObject("cnt", cnt);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
      return mav;
  }
  
  // http://localhost:9093/cateco/list_all.do
  @RequestMapping(value="/cateco/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cateco/list_all"); // /WEB-INF/views/cateco/list_all.jsp
    
    ArrayList<CatecoVO> list = this.catecoProc.list_all();
    mav.addObject("list", list);
    } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
  // 조회
  @RequestMapping(value="/cateco/read.do", method=RequestMethod.GET)
  public ModelAndView read(HttpSession session, int catecono) {
    ModelAndView mav = new ModelAndView();
    
    if (this.adminProc.isAdmin(session) == true) {
      mav.setViewName("/cateco/read"); // /WEB-INF/views/cateco/read.jsp
    
    CatecoVO catecoVO = this.catecoProc.read(catecono);
    mav.addObject("catecoVO", catecoVO);
  } else {
      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    }
    
    return mav;
  }
  
    // 수정 폼
    // http://localhost:9093/cateco/read_update.do?catecono=1
    @RequestMapping(value="/cateco/read_update.do", method=RequestMethod.GET)
    public ModelAndView read_update(HttpSession session, int catecono) {
      ModelAndView mav = new ModelAndView();
      
      if (this.adminProc.isAdmin(session) == true) {
        mav.setViewName("/cateco/read_update");
      
      CatecoVO catecoVO = this.catecoProc.read(catecono); // 수정용 데이터
      mav.addObject("catecoVO", catecoVO);
      
      ArrayList<CatecoVO> list = this.catecoProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
      } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
        
      }
      
      return mav;
    }
    
    // 수정 처리
    @RequestMapping(value="/cateco/update.do", method=RequestMethod.POST)
    public ModelAndView update(HttpSession session, CatecoVO catecoVO) {
      ModelAndView mav = new ModelAndView();
      
      if (this.adminProc.isAdmin(session) == true) {
        int cnt = this.catecoProc.update(catecoVO);
      
      if (cnt == 1) {
        // request.setAttribute("code", "update_success"); 
        // mav.addObject("code", "update_success");
        mav.setViewName("redirect:/cateco/list_all.do"); 
        
      } else {
        // request.setAttribute("code", "update_fail");
        mav.addObject("code", "update_fail");
        mav.setViewName("/cateco/msg"); 
      }
      
      // request.setAttribute("cnt", cnt);
      mav.addObject("cnt", cnt);
      
      } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
      
      return mav;
    }

    // 삭제폼, 수정폼을 복사하여 개발 
    // http://localhost:9093/cateco/read_delete.do?catecono=1
    @RequestMapping(value="/cateco/read_delete.do", method=RequestMethod.GET)
    public ModelAndView read_delete(HttpSession session, int catecono) {
      ModelAndView mav = new ModelAndView();
      
      if (this.adminProc.isAdmin(session) == true) {      
      CatecoVO catecoVO = this.catecoProc.read(catecono); // 수정용 데이터
      mav.addObject("catecoVO", catecoVO);
      
      ArrayList<CatecoVO> list = this.catecoProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
      // 특정 카테고리에 속한 레코드 갯수를 리턴
      int count_by_catecono = this.contentscoProc.count_by_catecono(catecono);
      mav.addObject("count_by_catecono", count_by_catecono);
      
      mav.setViewName("/cateco/read_delete");
      
      } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
      
      return mav;
    }
    
    // 삭제 처리, 수정 처리를 복사하여 개발 
    @RequestMapping(value="/cateco/delete.do", method=RequestMethod.POST)
    public ModelAndView delete(HttpSession session, int catecono) {     
      ModelAndView mav = new ModelAndView();
          
      if (this.adminProc.isAdmin(session) == true) {
        ArrayList<ContentscoVO> list = this.contentscoProc.list_by_catecono(catecono); // 자식 레코드 목록 읽기
        
        for(ContentscoVO contentscoVO : list) { // 자식 레코드 관련 파일 삭제
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
        
      this.contentscoProc.delete_by_catecono(catecono); // 자식 레코드 삭제
      
      int cnt = this.catecoProc.delete(catecono);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cateco/list_all.do"); 
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/cateco/msg"); 
      }
      
      mav.addObject("cnt", cnt);
      
      } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
      
      return mav;
    }
    

     // 출력 순서 상향(10 등 -> 1 등)
    @RequestMapping(value = "/cateco/update_seqno_decrease.do", method = RequestMethod.GET)
    public ModelAndView update_seqno_decrease(int catecono) {
      ModelAndView mav = new ModelAndView();
      
      CatecoVO catecoVO = this.catecoProc.read(catecono);
      int seqno = catecoVO.getSeqno();
      
      if(seqno > 1) {
        int cnt =  this.catecoProc.update_seqno_decrease(catecono);
        mav.addObject("cnt", cnt);
        
        if(cnt == 1) {
          mav.setViewName("redirect:/cateco/list_all.do");
          
        }else {
          mav.addObject("code", "update_seqno_decrease_fail");
          mav.setViewName("/cateco/msg");
        }
        
      }else {
        mav.setViewName("redirect:/cateco/list_all.do");
      }
            
      return mav;
    }
    
    // 출력 순서 하향(1등 -> 10등)
    @RequestMapping(value = "/cateco/update_seqno_increase.do", method = RequestMethod.GET)
    public ModelAndView update_seqno_increase(int catecono) {
      ModelAndView mav = new ModelAndView();
      int cnt = this.catecoProc.update_seqno_increase(catecono); 
      mav.addObject("cnt", cnt);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cateco/list_all.do");

      } else {
        mav.addObject("code", "update_seqno_increase_fail");
        mav.setViewName("/cateco/msg"); 
      }
      
      return mav;
    }


     // 글수 증가
     // http://localhost:9093/cateco/update_cnt_add.do?catecono=1
    @RequestMapping(value = "/cateco/update_cnt_add.do", method = RequestMethod.GET)
    public ModelAndView update_cnt_add(int catecono) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("redirect:/cateco/list_all.do");
      
      this.catecoProc.update_cnt_add(catecono);
      
      return mav;
    }
    

     // 글수 감소
     // http://localhost:9093/cateco/update_cnt_sub.do?catecono=1
    @RequestMapping(value = "/cateco/update_cnt_sub.do", method = RequestMethod.GET)
    public ModelAndView update_cnt_sub(int catecono) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("redirect:/cateco/list_all.do");
      
      this.catecoProc.update_cnt_sub(catecono);
      
      return mav;
    }
}


  







