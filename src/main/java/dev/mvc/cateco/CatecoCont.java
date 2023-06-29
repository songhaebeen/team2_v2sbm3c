package dev.mvc.cateco;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        //mav.setViewName("redirect:/cateco/list_all.do");     // 목록으로 자동 이동
        
      } else {
         //request.setAttribute("code", "create_fail");
//        mav.addObject("code", "create_fail");
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
}
  

  







