package dev.mvc.cosme_cate;

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

import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;


@Controller
public class Cosme_cateCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;
	  
  @Autowired
  @Qualifier("dev.mvc.cosme_cate.Cosme_cateProc")
  private Cosme_cateProcInter cosme_cateProc;
  
  public Cosme_cateCont() {
    System.out.println("-> Cosme_cateCont created.");
  }

  

  /**
   * 등록폼
   * http://localhost:9093/cosme_cate/create.do
   * @return
   */
 @RequestMapping(value="/cosme_cate/create.do", method=RequestMethod.GET)
public ModelAndView create(HttpSession session) {
 
 ModelAndView mav = new ModelAndView();
 
 if (this.memberProc.isMember(session) == true) {
	 mav.setViewName("/cosme_cate/create");
 } else {
     mav.setViewName("/member/login_need"); // /WEB-INF/views/master/login_need.jsp
   }

 
 return mav;
}
 
/**
 * 등록 처리
 * 
 * @param cosme_cateVO
 * @return
 */
 @RequestMapping(value="/cosme_cate/create.do", method=RequestMethod.POST)
 public ModelAndView create(Cosme_cateVO cosme_cateVO, HttpSession session) { 
   
   ModelAndView mav = new ModelAndView();
   
   if (this.memberProc.isMember(session) == true) {
	   int cnt = this.cosme_cateProc.create(cosme_cateVO);
	     if (cnt == 1) {
	       // request.setAttribute("code", "create_success"); // 고전적인 jsp 방법 
	       // mav.addObject("code", "create_success");
	       mav.setViewName("redirect:/cosme_cate/list_all.do");     // 목록으로 자동 이동
	       
	     } else {
	       // request.setAttribute("code", "create_fail");
	       mav.addObject("code", "create_fail");
	       mav.setViewName("/cosme_cate/msg"); // /WEB-INF/views/cate/msg.jsp // 등록 실패 메시지 출력

	     }
	     
	     // request.setAttribute("cnt", cnt);
	     mav.addObject("cnt", cnt);
   } else {
	     mav.setViewName("/master/login_need"); // /WEB-INF/views/master/login_need.jsp
	   }

   
   return mav;
 }
 
 /**
  * 목록
  * http://localhost:9093/cosme_cate/list.all
  * @return
  */
 // http://localhost:9093/cosme_cate/list_all.do
 @RequestMapping(value="/cosme_cate/list_all.do", method=RequestMethod.GET)
 public ModelAndView list_all() {
   ModelAndView mav = new ModelAndView();
   
	      mav.setViewName("/cosme_cate/list_all"); // /WEB-INF/views/cosme_cate/list_all.jsp
	      
	      ArrayList<Cosme_cateVO> list = this.cosme_cateProc.list_all();
	      mav.addObject("list", list);   

   return mav;
 }
 
 /**
  * 종류별 리스트
  * http://localhost:9093/cosme_cate/list_by_cate.do?cosme_cateno=1
  * @return
  */
 @RequestMapping(value="/cosme_cate/list_by_cate.do", method=RequestMethod.GET)
 public ModelAndView list_by_cateno(@RequestParam(value = "cosme_cateno", required = false) Integer cosme_cateno) {
     ModelAndView mav = new ModelAndView();

     if (cosme_cateno != null) {
         Cosme_cateVO cosme_cateVO = this.cosme_cateProc.read(cosme_cateno);
         mav.addObject("cosme_cateVO", cosme_cateVO);

         ArrayList<Cosme_cateVO> list = this.cosme_cateProc.list_by_cate(cosme_cateno);
         mav.addObject("list", list);
     }

     mav.setViewName("/cosme_cate/list_by_cate"); // /webapp/WEB-INF/views/contents/list_by_cateno.jsp

     return mav;

 }
 // 수정폼
 // http://localhost:9093/cosme_cate/read_update.do?cosme_cate=1
 // http://localhost:9093/cosme_cate/read_update.do?cosme_cate=2
 // http://localhost:9093/cosme_cate/read_update.do?cosme_cate=3
 @RequestMapping(value="/cosme_cate/read_update.do", method=RequestMethod.GET)
 public ModelAndView read_update(HttpSession session, int cosme_cateno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.memberProc.isMember(session) == true) {
     mav.setViewName("/cosme_cate/read_update"); // /WEB-INF/views/cate/read_update.jsp
     
     Cosme_cateVO cosme_cateVO = this.cosme_cateProc.read(cosme_cateno); // 수정용 데이터
     mav.addObject("cosme_cateVO", cosme_cateVO);
     
     ArrayList<Cosme_cateVO> list = this.cosme_cateProc.list_all(); // 목록 출력용 데이터
     mav.addObject("list", list);
     
   } else {
     mav.setViewName("/master/login_need"); // /WEB-INF/views/admin/login_need.jsp
     
   }
   
   return mav;
 }
 
 // 수정 처리
 @RequestMapping(value="/cosme_cate/update.do", method=RequestMethod.POST)
 public ModelAndView update(HttpSession session, Cosme_cateVO cosme_cateVO) { 
   
   ModelAndView mav = new ModelAndView();

   if (this.memberProc.isMember(session) == true) {
     int cnt = this.cosme_cateProc.update(cosme_cateVO);
     
     if (cnt == 1) {
       // request.setAttribute("code", "update_success");
       // mav.addObject("code", "update_success");
       mav.setViewName("redirect:/cosme_cate/list_all.do");  
       
     } else {
       // request.setAttribute("code", "update_fail");
       mav.addObject("code", "update_fail");
       mav.setViewName("/cosme_cate/msg"); 
     }
     
     mav.addObject("cnt", cnt);
     
   } else {
     mav.setViewName("/master/login_need"); 
   }
   
   return mav;
 }

 // 삭제폼, 수정폼을 복사하여 개발 
 // http://localhost:9091/cosme_cate/read_delete.do?cosme_cateno=1
 @RequestMapping(value="/cosme_cate/read_delete.do", method=RequestMethod.GET)
 public ModelAndView read_delete(HttpSession session, int cosme_cateno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.memberProc.isMember(session) == true) {
     Cosme_cateVO cosme_cateVO = this.cosme_cateProc.read(cosme_cateno); // 수정용 데이터
     mav.addObject("cosme_cateVO", cosme_cateVO);
     
     ArrayList<Cosme_cateVO> list = this.cosme_cateProc.list_all(); // 목록 출력용 데이터
     mav.addObject("list", list);
     
     mav.setViewName("/cosme_cate/read_delete");
     
   } else {
     mav.setViewName("/master/login_need"); 
   }
   
   return mav;
 }
 
 // 삭제 처리, 수정 처리를 복사하여 개발
 @RequestMapping(value="/cosme_cate/delete.do", method=RequestMethod.POST)
 public ModelAndView delete(HttpSession session, int cosme_cateno) { 

   ModelAndView mav = new ModelAndView();
   
   if (this.memberProc.isMember(session) == true) {
     ArrayList<Cosme_cateVO> list = this.cosme_cateProc.list_by_cate(cosme_cateno); 
           
     int cnt = this.cosme_cateProc.delete(cosme_cateno); // 카테고리 삭제
     
     if (cnt == 1) {
       mav.setViewName("redirect:/cosme_cate/list_all.do");       // 자동 주소 이동, Spring 재호출
       
     } else {
       mav.addObject("code", "delete_fail");
       mav.setViewName("/cosme_cate/msg"); 
     }
     
     mav.addObject("cnt", cnt);
     
   } else {
     mav.setViewName("/master/login_need"); 
   }
   
   return mav;
 }
 
 // http://localhost:9093/cosme_cate/read.do?cosme_cateno=1
 @RequestMapping(value="/cosme_cate/read.do", method=RequestMethod.GET)
 public ModelAndView read(HttpSession session, int cosme_cateno) {
   ModelAndView mav = new ModelAndView();
   
   if (this.memberProc.isMember(session) == true) {
     mav.setViewName("/cosme_cate/read"); 
     
     Cosme_cateVO cosme_cateVO = this.cosme_cateProc.read(cosme_cateno);
     mav.addObject("cosme_cateVO", cosme_cateVO);
   } else {
     mav.setViewName("/master/login_need"); 
   }
   
   return mav;
 }
 }
