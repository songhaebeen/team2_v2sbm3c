package dev.mvc.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.fboard.FboardProcInter;
import dev.mvc.fboard.FboardVO;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberVO;
import dev.mvc.tool.Tool;

@Controller
public class ReplyCont {
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc") // 이름 지정
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // 이름 지정
  private MemberProc memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
  
  @Autowired
  @Qualifier("dev.mvc.fboard.FboardProc") 
  private FboardProcInter fboardProc;

  public ReplyCont(){
    System.out.println("-> ReplyCont created.");
  }
  
  @ResponseBody
  @RequestMapping(value = "/reply/create.do",
                            method = RequestMethod.POST,
                            produces = "text/plain;charset=UTF-8")
  public String create(ReplyVO replyVO, int fboardno) {
    int cnt = replyProc.create(replyVO);
    fboardProc.increaseReplycnt(fboardno);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
 
    return obj.toString(); // {"cnt":1}

  }
  
  /**
   * 패스워드를 검사한 후 삭제 
   * http://localhost:9093/reply/delete.do?replyno=1&passwd=1234
   * {"delete_cnt":0,"passwd_cnt":0}
   * {"delete_cnt":1,"passwd_cnt":1}
   * @param replyno
   * @param passwd
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/delete.do", 
                              method = RequestMethod.POST,
                              produces = "text/plain;charset=UTF-8")
  public String delete(int replyno, String passwd, int fboardno) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("replyno", replyno);
    map.put("passwd", passwd);
    map.put("fboardno", fboardno);
    
    int passwd_cnt = replyProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    int delete_cnt = 0;                                    // 삭제된 댓글
    
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
      delete_cnt = replyProc.delete(replyno); // 댓글 삭제
      fboardProc.decreaseReplycnt(fboardno);
      
    }    

    JSONObject obj = new JSONObject();
    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    obj.put("delete_cnt", delete_cnt); // 삭제된 댓글
    
    
    return obj.toString();
  }
  
  @RequestMapping(value = "/reply/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int replyno, int fboardno) {
	  ModelAndView mav = new ModelAndView();	 
	  System.out.println("-> fboardno :" + session.getAttribute("fboardno"));
	  fboardProc.decreaseReplycnt(fboardno);
	  
      if (this.adminProc.isAdmin(session) == true) {
          mav.setViewName("/reply/list_join");        
          this.replyProc.delete(replyno);
          this.fboardProc.decreaseReplycnt(fboardno);
          
        } else {
          mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp

        }
	  
	  return mav;
	  
  }
  
  
//  /**
//   * 댓글 전체 목록(관리자)
//   * http://localhost:9093/reply/list.do
//   * @param session
//   * @return
//   */
//  @RequestMapping(value="/reply/list.do", method=RequestMethod.GET)
//  public ModelAndView list(HttpSession session) {
//    ModelAndView mav = new ModelAndView();
//    
//    if (adminProc.isAdmin(session)) {
//      List<ReplyVO> list = replyProc.list();
//      
//      mav.addObject("list", list);
//      mav.setViewName("/reply/list"); // /webapp/reply/list.jsp
//
//    } else {
//      mav.addObject("return_url", "/reply/list.do"); // 로그인 후 이동할 주소 ★
//      
//      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
//    }
//    
//    return mav;
//  }

  /**
   <xmp>
   http://localhost:9093/reply/list_by_fboardno.do?fboardno=1
   글이 없는 경우: {"list":[]}
   글이 있는 경우
   {"list":[
            {"memberno":1,"rdate":"2019-12-18 16:46:43","passwd":"123","replyno":3,"content":"댓글 3","contentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:39","passwd":"123","replyno":2,"content":"댓글 2","contentsno":1}
            ,
            {"memberno":1,"rdate":"2019-12-18 16:46:35","passwd":"123","replyno":1,"content":"댓글 1","contentsno":1}
            ] 
   }
   </xmp>  
//   * @param fboardno
//   * @return
//   */
//  @ResponseBody
//  @RequestMapping(value = "/reply/list_by_fboardno.do",
//                            method = RequestMethod.GET,
//                            produces = "text/plain;charset=UTF-8")
//  public String list_by_fboardno(int fboardno) {
//    List<ReplyVO> list = replyProc.list_by_fboardno(fboardno);
//    
//    JSONObject obj = new JSONObject();
//    obj.put("list", list);
// 
//    return obj.toString(); 
//
//  }
  
  /**
   * 관리자만 목록 확인 가능
   * http://localhost:9093/reply/list_join.do
   * @param session
   * @return
   */
  @RequestMapping(value="/reply/list_join.do", method=RequestMethod.GET)
  public ModelAndView list_member_join(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if ( adminProc.isAdmin(session)) {
      List<ReplyMemberVO> list = replyProc.list_member_join();
      
      mav.addObject("list", list);
      mav.setViewName("/reply/list_join"); // /webapp/reply/list_join.jsp

    } else {
      mav.addObject("return_url", "/reply/list_join.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/admin/login.do"); // /WEB-INF/views/admin/login_ck_form.jsp
    }
    
    return mav;
  }
  
  /**
   * 컨텐츠별 댓글 목록 
   * {"list":[
          {"memberno":1,
        "rdate":"2019-12-18 16:46:35",
      "passwd":"123",
      "replyno":1,
      "id":"user1",
      "content":"댓글 1",
      "fboardno":1}
    ,
        {"memberno":1,
       "rdate":"2019-12-18 16:46:35",
       "passwd":"123",
       "replyno":1,
       "id":"user1",
       "content":"댓글 1",
       "fboardno":1}
    ]
}
   * http://localhost:9093/reply/list_by_fboardno_join.do?fboardno=2
   * @param fboardno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_fboardno_join.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_fboardno_join(int fboardno) {
    // String msg="JSON 출력";
    // return msg;
    
    List<ReplyMemberVO> list = replyProc.list_by_fboardno_join(fboardno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  

//  /**
//   * 패스워드 입력폼 
//   * http://localhost:9093/reply/update.do?replyno=1&passwd=1234
//   * {"delete_cnt":0,"passwd_cnt":0}
//   * {"delete_cnt":1,"passwd_cnt":1}
//   * @param replyno
//   * @param passwd
//   * @return
//   */
//  @ResponseBody
//  @RequestMapping(value = "/reply/update.do", 
//                              method = RequestMethod.POST,
//                              produces = "text/plain;charset=UTF-8")
//  public String update_passwd(ReplyMemberVO replyMemberVO, String passwd) {
//    Map<String, Object> map = new HashMap<String, Object>();
//    map.put("replyMemberVO", replyMemberVO);
//    map.put("passwd", passwd);
//    
//    int passwd_cnt = replyProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
//    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
//      replyProc.update(replyMemberVO); // 댓글 수정
//    }
//    
//    JSONObject obj = new JSONObject();
//    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
//    
//    return obj.toString();
//  }
  
//  /**
//   * 패스워드를 검사한 후 수정 
//   * http://localhost:9093/reply/update.do?replyno=1&passwd=1234
//   * {"delete_cnt":0,"passwd_cnt":0}
//   * {"delete_cnt":1,"passwd_cnt":1}
//   * @param replyno
//   * @param passwd
//   * @return
//   */
//  @ResponseBody
//  @RequestMapping(value = "/reply/update.do", 
//                              method = RequestMethod.POST,
//                              produces = "text/plain;charset=UTF-8")
//  public String update(ReplyMemberVO replyMemberVO, String passwd) {
//    Map<String, Object> map = new HashMap<String, Object>();
//    map.put("replyMemberVO", replyMemberVO);
//    map.put("passwd", passwd);
//    
//    int passwd_cnt = replyProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
//    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
//      replyProc.update(replyMemberVO); // 댓글 수정
//    }
//    
//    JSONObject obj = new JSONObject();
//    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
//    
//    return obj.toString();
//  }
  
//  /**
//   * 삭제 폼
//   * @param replyno
//   * @return
//   */
//  @RequestMapping(value="/reply/delete.do", method=RequestMethod.GET )
//  public ModelAndView delete(int replyno) { 
//    ModelAndView mav = new  ModelAndView();
//    
//    // 삭제할 정보를 조회하여 확인
//    ReplyVO replyVO = this.replyProc.list(replyno);
//    mav.addObject("replyVO", replyVO);
//    
//    mav.setViewName("/reply/delete");  // /webapp/WEB-INF/views/reply/delete.jsp
//    
//    return mav; 
//  }
  
  /**
   * {"list":[
          {"memberno":1,
        "rdate":"2019-12-18 16:46:35",
      "passwd":"123",
      "replyno":1,
      "id":"user1",
      "content":"댓글 1",
      "fboardno":1}
    ,
        {"memberno":1,
       "rdate":"2019-12-18 16:46:35",
       "passwd":"123",
       "replyno":1,
       "id":"user1",
       "content":"댓글 1",
       "fboardno":1}
    ]
  }

   * http://localhost:9093/reply/list_by_fboardno_join_add.do?fboardno=2
   * @param fboardno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_fboardno_join_add.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_fboardno_join_add(int fboardno) {
    ModelAndView mav = new ModelAndView();
    // String msg="JSON 출력";
    // return msg;
    
    List<ReplyMemberVO> list = replyProc.list_by_fboardno_join_add(fboardno);
    
	//fboardProc.increaseReplycnt(fboardno);
	//fboardProc.decreaseReplycnt(fboardno);
    
    FboardVO fboardVO = this.fboardProc.read(fboardno);
    
    //회원 번호: memberno -> MemberVO -> mname
   String mname = this.memberProc.read(fboardVO.getMemberno()).getMname();
   mav.addObject("mname", mname);
    
   
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 
  }
  
  /**
   * 10건 출력
  * http://localhost:9093/reply/list_ten.do?fboardno=2
   * @param fboardno
   * @return
   */
  @RequestMapping(value="/reply/list_ten.do", method=RequestMethod.GET)
  public ModelAndView list_ten(int fboardno) {
    ModelAndView mav = new ModelAndView();
    List<ReplyMemberVO> list = replyProc.list_ten(fboardno);
    
    mav.addObject("list", list);
    
    mav.setViewName("/reply/list_ten"); // /webapp/reply/list_ten.jsp
    
    return mav;
  }
  
  /**
   * 내가 단 댓글 목록
  * http://localhost:9093/reply/list_memberno.do
   * @param memberno
   * @return
   */
  @RequestMapping(value="/reply/list_memberno.do", method=RequestMethod.GET)
  public ModelAndView list_memberno(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    System.out.println("-> memberno :" + session.getAttribute("memberno"));
    System.out.println("-> id :" + session.getAttribute("id"));

    int memberno = 0;
    
    if (this.memberProc.isMember(session)) { // 회원으로 로그인
      if (session.getAttribute("memberno") != null) {
        memberno = (int)session.getAttribute("memberno");
      }
    

      List<ReplyMemberVO> list = replyProc.list_memberno(memberno);
      mav.addObject("list", list);
   
    
    mav.setViewName("/reply/list_memberno"); // /webapp/reply/list_memberno.jsp
    } else {
      // 로그인을 하지 않은 경우
      mav.setViewName("/member/login_need"); // /webapp/WEB-INF/views/member/login_need.jsp
    }
    
    return mav;
  }
  
//  /**
//   * 댓글 조회
//   * 회원 본인만 가능
//   * @param memberno
//   * @return
//   */
//  @RequestMapping(value="/reply/read.do", method=RequestMethod.GET)
//  public ModelAndView read(HttpSession session, HttpServletRequest request){
//    ModelAndView mav = new ModelAndView();
//    
//    int memberno = 0;
//    if (this.memberProc.isMember(session)) { 
//      // 로그인한 경우
//        memberno = (int)session.getAttribute("memberno"); // 본인의 회원 정보 조회
//        
//      ReplyMemberVO replyMemberVO = this.replyProc.read(memberno);
//      mav.addObject("memberVO", replyMemberVO);
//      mav.setViewName("/reply/read"); // /member/read.jsp
//      
//    } else {
//      // 로그인을 하지 않은 경우
//      mav.setViewName("/member/login_need"); // /webapp/WEB-INF/views/member/login_need.jsp
//    }
//    
//    return mav; // forward
//  }

  
  /**
   * 수정 폼
   * http://localhost:9093/reply/update.do
   * 
   * @return
   */
  @RequestMapping(value = "/reply/update.do", method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int replyno) {
    ModelAndView mav = new ModelAndView();
    System.out.println("-> memberno :" + session.getAttribute("memberno"));
    System.out.println("-> id :" + session.getAttribute("id"));
    System.out.println("-> public ModelAndView update(HttpSession session, int replyno) 호출");
    
    
    if (this.memberProc.isMember(session)) { // 로그인
      ReplyMemberVO replyMemberVO = this.replyProc.read(replyno);
      mav.addObject("replyMemberVO", replyMemberVO);
    
      mav.setViewName("/reply/update"); // /WEB-INF/views/reply/update.jsp

      }else{ // 정상적인 로그인이 아닌 경우
     mav.setViewName("/member/login_need"); // /WEB-INF/views/member/login_need.jsp
     }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9093/reply/update.do
   * 
   * @return
   */
  @RequestMapping(value = "/reply/update.do", method = RequestMethod.POST)
  public ModelAndView update(HttpSession session, ReplyMemberVO replyMemberVO) {
    ModelAndView mav = new ModelAndView();
    System.out.println("-> memberno :" + session.getAttribute("memberno"));
    System.out.println("-> id :" + session.getAttribute("id"));
    System.out.println("-> public ModelAndView update(HttpSession session, ReplyMemberVO replyMemberVO) 호출");
        
    if (this.memberProc.isMember(session) && this.replyProc.password_check(replyMemberVO) == 1 ) {
       this.replyProc.update(replyMemberVO);  

         
       // mav 객체 이용
       mav.addObject("replyno", replyMemberVO.getReplyno());
       mav.setViewName("redirect:/reply/list_memberno.do");
    } else {
      mav.addObject("replyMemberVO", replyMemberVO);

       mav.addObject("url", "/reply/passwd_check"); // /WEB-INF/views/reply/passwd_check.jsp
    }    
    
    
   
    return mav; // forward
  }
  
}