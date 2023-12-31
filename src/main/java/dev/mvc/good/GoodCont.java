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
import dev.mvc.fboard.FboardVO;
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
  public String down(GoodVO goodVO, int fboardno) {
  int cnt = goodProc.delete(goodVO);
  //감소
  fboardProc.decreaseRecom(fboardno);
  
  JSONObject obj = new JSONObject();
  obj.put("cnt",cnt);
  
  return obj.toString(); // {"cnt":1}
  
  }
  
  @RequestMapping(value = "/good/down.do", method = RequestMethod.GET)
  public ModelAndView down(HttpSession session, GoodVO goodVO) {
    ModelAndView mav = new ModelAndView();
      if (this.adminProc.isAdmin(session) == true) {
          mav.setViewName("/good/list_all");
          this.goodProc.delete(goodVO);
          
        } else if (this.memberProc.isMember(session) == true) {
          mav.setViewName("/good/list_memberno");
          this.goodProc.delete(goodVO);
        }else{
          mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp

        }
    
    return mav;
    
  }
  
  /**
   * 좋아요 체크 후 증가, 감소
   * http://localhost:9093/good/checkGood.do?fboardno=1&memberno=1
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/good/checkGood.do", 
  						  method = RequestMethod.POST,
  						  produces = "text/plain;charset=UTF-8")
  public String checkGood(HttpSession session, GoodVO goodVO) {
    int fboardno = goodVO.getFboardno();
       
    boolean bol = this.memberProc.isMember(session);
    int findcnt = this.goodProc.findGood(goodVO); //좋아요 있나 확인
    
    System.out.println("memberno: " + session.getAttribute("memberno"));
    
    if ( bol &&  findcnt == 0) {  
       //회원 좋아요 1 증가
       goodProc.create(goodVO);
       //전체 좋아요 수 증가
       fboardProc.increaseRecom(fboardno);
         
     } else if(bol &&  findcnt == 1){
       //회원 좋아요 1 감소
       goodProc.delete(goodVO);
       //좋아요 수 감소
       fboardProc.decreaseRecom(fboardno);
    }    
   
    FboardVO fboardVO = this.fboardProc.read(fboardno);
    int recom = fboardVO.getRecom();
    System.out.println("-> recom: " + recom);
    System.out.println("-> findcnt: " + findcnt);
    
    JSONObject obj = new JSONObject();
    obj.put("recom", recom);
    obj.put("findcnt", findcnt);
    
    return obj.toString();
  }
  
}
