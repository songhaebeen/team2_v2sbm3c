package dev.mvc.cateco;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.tool.Tool;

@Controller
public class CatecoCont {
  @Autowired
  @Qualifier("dev.mvc.cateco.CatecoProc")  // @Component("dev.mvc.cate.CateProc")
  private CatecoProcInter catecoProc; // CateProc 객체가 자동 생성되어 할당됨.
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  
  public CatecoCont() {
    System.out.println("-> CatecoCont created.");
  }
  
  // 등록폼
  // http://localhost:9093/cateco/create.do
  @RequestMapping(value="/cateco/create.do", method=RequestMethod.GET)
  public ModelAndView create() {
    
    ModelAndView mav = new ModelAndView();
    
//    if (this.adminProc.isAdmin(session) == true) {
      // spring.mvc.view.prefix=/WEB-INF/views/
      // spring.mvc.view.suffix=.jsp
      mav.setViewName("/cateco/create"); // /WEB-INF/views/cate/create.jsp      
//    } else {
//      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
//    }

    return mav;
  }

  // 등록 처리
  // http://localhost:9091/cateco/create.do
  @RequestMapping(value="/cateco/create.do", method=RequestMethod.POST)
  public ModelAndView create(CatecoVO catecoVO) { // <form> 태그의 값이 자동으로 저장됨
     //request.getParameter("name"); 
    // System.out.println("-> name: " + cateVO.getName());
    
    ModelAndView mav = new ModelAndView();
    
//    if (session.getAttribute("admin_id") != null) {
      int cnt = this.catecoProc.create(catecoVO);
    
      if (cnt == 1) {
         //request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
         mav.addObject("code", "create_success");
        mav.setViewName("redirect:/cateco/list_all.do");     // 목록으로 자동 이동
        
      } else {
         //request.setAttribute("code", "create_fail");
        mav.addObject("code", "create_fail");
        mav.setViewName("/cateco/msg"); // /WEB-INF/views/cate/msg.jsp // 등록 실패 메시지 출력

//      }
      
      // request.setAttribute("cnt", cnt);
//     mav.addObject("cnt", cnt);
//    } else {
//      mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
//    }
    
      }
      return mav;
  }
  
  // http://localhost:9093/cateco/list_all.do
  @RequestMapping(value="/cateco/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cateco/list_all"); // /WEB-INF/views/cateco/list_all.jsp
    
    ArrayList<CatecoVO> list = this.catecoProc.list_all();
    mav.addObject("list", list);
    
    return mav;
  }
  
  // 조회
  @RequestMapping(value="/cateco/read.do", method=RequestMethod.GET)
  public ModelAndView read(int catecono) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/cateco/read");
    
    CatecoVO catecoVO = this.catecoProc.read(catecono);
    mav.addObject("catecoVO", catecoVO);
    
    return mav;
  }
  
    // 수정 폼
    // http://localhost:9093/cateco/read_update.do?catecono=1
    @RequestMapping(value="/cateco/read_update.do", method=RequestMethod.GET)
    public ModelAndView read_update(int catecono) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/cateco/read_update");
      
      CatecoVO catecoVO = this.catecoProc.read(catecono); // 수정용 데이터
      mav.addObject("catecoVO", catecoVO);
      
      ArrayList<CatecoVO> list = this.catecoProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
      return mav;
    }
    
    // 수정 처리
    @RequestMapping(value="/cateco/update.do", method=RequestMethod.POST)
    public ModelAndView update(CatecoVO catecoVO) {
      ModelAndView mav = new ModelAndView();
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
      
      return mav;
    }

    // 삭제폼, 수정폼을 복사하여 개발 
    // http://localhost:9093/cateco/read_delete.do?catecono=1
    @RequestMapping(value="/cateco/read_delete.do", method=RequestMethod.GET)
    public ModelAndView read_delete(int catecono) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/cateco/read_delete");
      
      CatecoVO catecoVO = this.catecoProc.read(catecono); // 수정용 데이터
      mav.addObject("catecoVO", catecoVO);
      
      ArrayList<CatecoVO> list = this.catecoProc.list_all(); // 목록 출력용 데이터
      mav.addObject("list", list);
      
      return mav;
    }
    
    // 삭제 처리, 수정 처리를 복사하여 개발 
    @RequestMapping(value="/cateco/delete.do", method=RequestMethod.POST)
    public ModelAndView delete(int catecono) { 

      
      ModelAndView mav = new ModelAndView();
          
      int cnt = this.catecoProc.delete(catecono);
      
      if (cnt == 1) {
        mav.setViewName("redirect:/cateco/list_all.do"); 
        
      } else {
        mav.addObject("code", "delete_fail");
        mav.setViewName("/cateco/msg"); 
      }
      
      mav.addObject("cnt", cnt);
      
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


  







