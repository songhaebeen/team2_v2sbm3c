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
   * 좋아요 
   * http://localhost:9093/good/create.do?fboardno=2&memberno=3
   * 
   * @return
   */
  @RequestMapping(value = "/good/create.do",
      method = RequestMethod.GET,
      produces = "text/plain;charset=UTF-8")
  public String findGood(int fboardno, HttpSession session, int goodno, GoodVO goodVO) {
    
    int cnt = goodProc.create(goodVO);
    //증가
    int recom = fboardProc.increaseRecom(fboardno);
    
    int find = goodProc.findGood(goodVO);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
    obj.put("recom",recom);
    obj.put("find",find);
    
    return obj.toString(); // {"cnt":1}
  
  }
  
  /**
   * 좋아요 처리
   * http://localhost:9093/good/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/good/create.do",
      method = RequestMethod.POST,
      produces = "text/plain;charset=UTF-8")
  public String create(int goodno, int fboardno,GoodVO goodVO) {
   
    int cnt = goodProc.create(goodVO);
    //증가
    int recom = fboardProc.increaseRecom(fboardno);
  
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
    obj.put("recom",recom);
  
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
   * 좋아요 처리
   * http://localhost:9093/good/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/good/delete.do",
      method = RequestMethod.POST,
      produces = "text/plain;charset=UTF-8")
  public String down(int goodno, int fboardno) {
  int cnt = goodProc.delete(goodno);
  //감소
  fboardProc.decreaseRecom(fboardno);
  
  JSONObject obj = new JSONObject();
  obj.put("cnt",cnt);
  
  return obj.toString(); // {"cnt":1}
  
  }
  
  @RequestMapping(value = "/good/down.do", method = RequestMethod.GET)
  public ModelAndView down(HttpSession session, int goodno) {
    ModelAndView mav = new ModelAndView();
      if (this.adminProc.isAdmin(session) == true) {
          mav.setViewName("/good/list_all");
          this.goodProc.delete(goodno);
          
        } else if (this.memberProc.isMember(session) == true) {
          mav.setViewName("/good/list_memberno");
          this.goodProc.delete(goodno);
        }else{
          mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp

        }
    
    return mav;
    
  }
  
  /**
   * 좋아요 체크
   * http://localhost:9093/good/findGood.do?fboardno=2&memberno=3
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/good/findGood.do", 
  						  method = RequestMethod.GET,
  						  produces = "text/plain;charset=UTF-8")
  public String findGood(HttpSession session, GoodVO goodVO, int goodno, int fboardno) {
       
    int cnt = 0; //좋아요 체크
    int up_cnt = 0; //좋아요 증가
    int down_cnt = 0; //좋아요 감소
    boolean bol = this.memberProc.isMember(session);
    int findcnt = this.goodProc.findGood(goodVO);
    
    System.out.println("memberno: " + session.getAttribute("memberno"));
    System.out.println("goodVO: " + goodVO);
    
    if ( bol &&  findcnt == 0) {  
      //회원 좋아요 1 증가
      cnt = goodProc.create(goodVO);
      //전체 좋아요 수 증가
      up_cnt = fboardProc.increaseRecom(fboardno);
         
     } else if(bol &&  findcnt == 1){
       //회원 좋아요 1 감소
       cnt = goodProc.delete(goodno);
       //좋아요 수 감소
       down_cnt = fboardProc.decreaseRecom(fboardno);
    }    
   
    JSONObject obj = new JSONObject();
    obj.put("up_cnt",up_cnt);
    obj.put("down_cnt",down_cnt);
    obj.put("cnt",cnt);
    
    return obj.toString();
  }
}
