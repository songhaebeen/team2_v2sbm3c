package dev.mvc.ingred;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.member.MemberProcInter;
import dev.mvc.admin.AdminProcInter;
import dev.mvc.cosmetype.CosmetypeVO;

@Controller
public class IngredCont {
	@Autowired
	@Qualifier("dev.mvc.ingred.IngredProc")
	private IngredProcInter ingredProc;
	
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
	
  public IngredCont() {
    System.out.println("-> IngredCont created.");
  }
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;
  

  /**
   * 등록폼
   * http://localhost:9093/ingred/create.do
   * @return
   */
 @RequestMapping(value="/ingred/create.do", method=RequestMethod.GET)
public ModelAndView create(HttpSession session) {
 
 ModelAndView mav = new ModelAndView();
 
 if (this.adminProc.isAdmin(session) == true) {
   mav.setViewName("/ingred/create");
 } else {
     mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
   }

 
 return mav;
}
 
/**
 * 등록 처리
 * 
 * @param ingredVO
 * @return
 */
 @RequestMapping(value="/ingred/create.do", method=RequestMethod.POST)
 public ModelAndView create(IngredVO ingredVO, HttpSession session) { 
   
   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     int cnt = this.ingredProc.create(ingredVO);
       if (cnt == 1) {
         // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
         // mav.addObject("code", "create_success");
         mav.setViewName("redirect:/ingred/list_all.do");     // 목록으로 자동 이동
         
       } else {
         // request.setAttribute("code", "create_fail");
         mav.addObject("code", "create_fail");
         mav.setViewName("/ingred/msg"); 

       }
       
       // request.setAttribute("cnt", cnt);
       mav.addObject("cnt", cnt);
   } else {
       mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
     }

   
   return mav;
 }
 
 /**
  * 목록
  * http://localhost:9093/ingred/list.all
  * @return
  */
 // http://localhost:9093/cosme_cate/list_all.do
 @RequestMapping(value="/ingred/list_all.do", method=RequestMethod.GET)
 public ModelAndView list_all() {
   ModelAndView mav = new ModelAndView();
   
        mav.setViewName("/ingred/list_all"); // /WEB-INF/views/cosme_cate/list_all.jsp
        
        ArrayList<IngredVO> list = this.ingredProc.list_all();
        mav.addObject("list", list);   

   return mav;
 }
 
 // 수정폼
 // http://localhost:9093/ingred/read_update.do?ingredno=1
 // http://localhost:9093/ingred/read_update.do?ingredno=2
 // http://localhost:9093/ingred/read_update.do?ingredno=3
 @RequestMapping(value="/ingred/read_update.do", method=RequestMethod.GET)
 public ModelAndView read_update(HttpSession session, int ingredno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     mav.setViewName("/ingred/read_update"); 
     
     IngredVO ingredVO = this.ingredProc.read(ingredno); // 수정용 데이터
     mav.addObject("ingredVO", ingredVO);
     
     ArrayList<IngredVO> list = this.ingredProc.list_all(); // 목록 출력용 데이터
     mav.addObject("list", list);
     
   } else {
     mav.setViewName("/admin/login_need"); 
     
   }
   
   return mav;
 }
 
 // 수정 처리
 @RequestMapping(value="/ingred/update.do", method=RequestMethod.POST)
 public ModelAndView update(HttpSession session, IngredVO ingredVO) { 
   
   ModelAndView mav = new ModelAndView();

   if (this.adminProc.isAdmin(session) == true) {
     int cnt = this.ingredProc.update(ingredVO);
     
     if (cnt == 1) {
       // request.setAttribute("code", "update_success");
       // mav.addObject("code", "update_success");
       mav.setViewName("redirect:/ingred/list_all.do");  
       
     } else {
       // request.setAttribute("code", "update_fail");
       mav.addObject("code", "update_fail");
       mav.setViewName("/ingred/msg"); 
     }
     
     mav.addObject("cnt", cnt);
     
   } else {
     mav.setViewName("/admin/login_need"); 
   }
   
   return mav;
 }

 // 삭제폼, 수정폼을 복사하여 개발 
 // http://localhost:9091/ingred/read_delete.do?ingredno=1
 @RequestMapping(value="/ingred/read_delete.do", method=RequestMethod.GET)
 public ModelAndView read_delete(HttpSession session, int ingredno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     IngredVO ingredVO = this.ingredProc.read(ingredno); // 수정용 데이터
     mav.addObject("ingredVO", ingredVO);
     
     ArrayList<IngredVO> list = this.ingredProc.list_all(); // 목록 출력용 데이터
     mav.addObject("list", list);
     
     mav.setViewName("/ingred/read_delete");
     
   } else {
     mav.setViewName("/admin/login_need"); 
   }
   
   return mav;
 }
 
 // 삭제 처리, 수정 처리를 복사하여 개발
 @RequestMapping(value="/ingred/delete.do", method=RequestMethod.POST)
 public ModelAndView delete(HttpSession session, int ingredno) { 

   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     ArrayList<IngredVO> list = this.ingredProc.list_all(); 
           
     int cnt = this.ingredProc.delete(ingredno); // 카테고리 삭제
     
     if (cnt == 1) {
       mav.setViewName("redirect:/ingred/list_all.do");       // 자동 주소 이동, Spring 재호출
       
     } else {
       mav.addObject("code", "delete_fail");
       mav.setViewName("/ingred/msg"); 
     }
     
     mav.addObject("cnt", cnt);
     
   } else {
     mav.setViewName("/admin/login_need"); 
   }
   
   return mav;
 }
 
 // http://localhost:9093/ingred/read.do?ingredno=1
 @RequestMapping(value="/ingred/read.do", method=RequestMethod.GET)
 public ModelAndView read(HttpSession session, int ingredno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     mav.setViewName("/ingred/read"); 
     
     IngredVO ingredVO = this.ingredProc.read(ingredno);
     mav.addObject("ingredVO", ingredVO);
   } else {
     mav.setViewName("/admin/login_need"); 
   }
   
   return mav;
 }
 
 @RequestMapping(value="/ingred/update_seqno_decrease.do", method = RequestMethod.GET)
 public ModelAndView update_seqno_decrease(int ingredno) {
 ModelAndView mav = new ModelAndView();
 
 // seqno 컬럼의 값이== 1 초과(1<)일때만 감소를 할 수 있다.
 IngredVO ingredVo = this.ingredProc.read(ingredno);
 int seqno = ingredVo.getSeqno();

 // 2) if 문을 이용한 분기
 if (seqno > 1) {
   int cnt = this.ingredProc.update_seqno_decrease(ingredno); 
   mav.addObject("cnt", cnt);
   
   if (cnt == 1) {
     mav.setViewName("redirect:/ingred/list_all.do");

   } else {
     mav.addObject("code", "update_seqno_decrease_fail");
     mav.setViewName("/ingred/msg"); 
   }
   
 } else {
   mav.setViewName("redirect:/ingred/list_all.do");
 }
 
 return mav;
}
 
 /**
  * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
  * @param ingredno
  * @return
  */
 @RequestMapping(value = "/ingred/update_seqno_increase.do", method = RequestMethod.GET)
 public ModelAndView update_seqno_increase(int ingredno) {
   ModelAndView mav = new ModelAndView();
   int cnt = this.ingredProc.update_seqno_increase(ingredno); 
   mav.addObject("cnt", cnt);
   
   if (cnt == 1) {
     mav.setViewName("redirect:/ingred/list_all.do");

   } else {
     mav.addObject("code", "update_seqno_increase_fail");
     mav.setViewName("/ingred/msg"); 
   }
   
   return mav;
 }
} 
