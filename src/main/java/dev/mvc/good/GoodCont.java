package dev.mvc.good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.fboard.FboardProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.reply.ReplyMemberVO;

@Controller
public class GoodCont {
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
  @Qualifier("dev.mvc.good.GoodProc") 
  private GoodProcInter goodProc;
  
  
  public GoodCont() {
	  System.out.println("-> GoodCont created.");
  }
  
  /**
   * 좋아요 처리
   * http://localhost:9093/good/up.do
   * 
   * @return
   */
  @RequestMapping(value = "/good/up.do",
      method = RequestMethod.POST,
      produces = "text/plain;charset=UTF-8")
  public String up(int goodno, int fboardno) {
  int cnt = goodProc.up(goodno);
  //증가
  fboardProc.increaseRecom(fboardno);
  
  JSONObject obj = new JSONObject();
  obj.put("cnt",cnt);
  
  return obj.toString(); // {"cnt":1}
  
  }
  
  /**
   * 관리자만 목록 확인 가능
   * http://localhost:9093/good/list_all.do
   * @param session
   * @return
   */
  @RequestMapping(value="/good/list_all.do", method=RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if ( adminProc.isAdmin(session)) {
      List<GoodVO> list = goodProc.list_all();
      
      mav.addObject("list", list);
      mav.setViewName("/good/list_all"); // /webapp/good/list_all.jsp

    } else {
      mav.addObject("return_url", "/good/list_all.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/admin/login.do"); // /WEB-INF/views/admin/login_ck_form.jsp
    }
    
    return mav;
  }
  
  
  /**
   * 회원별 좋아요 목록
  * http://localhost:9093/good/list_memberno.do
   * @param memberno
   * @return
   */
  @RequestMapping(value="/good/list_memberno.do", method=RequestMethod.GET)
  public ModelAndView list_memberno(HttpSession session) {
    ModelAndView mav = new ModelAndView();

    int memberno = 0;
    
    if (this.memberProc.isMember(session)) { // 회원으로 로그인
      if (session.getAttribute("memberno") != null) {
        memberno = (int)session.getAttribute("memberno");
      }
    

      List<GoodVO> list = goodProc.list_memberno(memberno);
      mav.addObject("list", list);
   
    
    mav.setViewName("/good/list_memberno"); // /webapp/good/list_memberno.jsp
    } else {
      // 로그인을 하지 않은 경우
      mav.setViewName("/member/login_need"); // /webapp/WEB-INF/views/member/login_need.jsp
    }
    
    return mav;
  }
  /**
   * 좋아요 취소 처리
   * http://localhost:9093/good/down.do
   * 
   * @return
   */
  @RequestMapping(value = "/good/down.do", method = RequestMethod.POST)
  public ModelAndView down(HttpSession session, GoodVO goodVO, int goodno, int fboardno) {
    ModelAndView mav = new ModelAndView();
        
    if (this.memberProc.isMember(session) && this.goodProc.findGood(goodVO) == 0) {
       this.goodProc.up(goodno);  
         
       // mav 객체 이용
       mav.addObject("goodno", goodVO.getGoodno());
       mav.setViewName("redirect:/good/list_memberno.do");
    } else {
    	mav.addObject("goodVO", goodVO);
        mav.addObject("url", "/reply/passwd_check"); // /WEB-INF/views/reply/passwd_check.jsp
    }    
   
    return mav; // forward
  }
  
  /**
   * 좋아요 체크
   * http://localhost:9093/good/findGood.do
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/good/findGood.do", 
  						  method = RequestMethod.POST,
  						  produces = "text/plain;charset=UTF-8")
  public String findGood(HttpSession session, GoodVO goodVO, int goodno, int fboardno) {
	Map<String, Object> map = new HashMap<String, Object>();
    map.put("goodno", goodno);
        
    int up_cnt = 0; //좋아요 증가
    int down_cnt = 0; //좋아요 감소
    if (this.memberProc.isMember(session) && this.goodProc.findGood(goodVO) == 0) {
    	down_cnt = goodProc.down(goodno);  
         
     } else if(this.memberProc.isMember(session) && this.goodProc.findGood(goodVO) == 1){
    	 up_cnt = goodProc.up(goodno);  
    }    
   
    JSONObject obj = new JSONObject();
    
    return obj.toString();
  }

}
