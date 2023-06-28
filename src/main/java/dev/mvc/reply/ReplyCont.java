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
  public String create(ReplyVO replyVO) {
    int cnt = replyProc.create(replyVO);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
 
    return obj.toString(); // {"cnt":1}

  }
  
  /**
   * 댓글 전체 목록(관리자)
   * @param session
   * @return
   */
  @RequestMapping(value="/reply/list.do", method=RequestMethod.GET)
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (adminProc.isAdmin(session)) {
      List<ReplyVO> list = replyProc.list();
      
      mav.addObject("list", list);
      mav.setViewName("/reply/list"); // /webapp/reply/list.jsp

    } else {
      mav.addObject("return_url", "/reply/list.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp
    }
    
    return mav;
  }

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
   * @param fboardno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_fboardno.do",
                            method = RequestMethod.GET,
                            produces = "text/plain;charset=UTF-8")
  public String list_by_fboardno(int fboardno) {
    List<ReplyVO> list = replyProc.list_by_fboardno(fboardno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 

  }
  
  /**
   * 관리자만 목록 확인 가능
   * @param session
   * @return
   */
  @RequestMapping(value="/reply/list_join.do", method=RequestMethod.GET)
  public ModelAndView list_join(HttpSession session) {
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
   * http://localhost:9093/reply/list_by_fboardno_join.do?fboardno=1
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
  public String delete(int replyno, String passwd) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("replyno", replyno);
    map.put("passwd", passwd);
    
    int passwd_cnt = replyProc.checkPasswd(map); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    int delete_cnt = 0;                                    // 삭제된 댓글
    if (passwd_cnt == 1) { // 패스워드가 일치할 경우
      delete_cnt = replyProc.delete(replyno); // 댓글 삭제
    }
    
    JSONObject obj = new JSONObject();
    obj.put("passwd_cnt", passwd_cnt); // 패스워드 일치 여부, 1: 일치, 0: 불일치
    obj.put("delete_cnt", delete_cnt); // 삭제된 댓글
    
    return obj.toString();
  }
  
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

   * http://localhost:9093/reply/list_by_fboardno_join_add.do?fboardno=1
   * @param fboardno
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/reply/list_by_fboardno_join_add.do",
                              method = RequestMethod.GET,
                              produces = "text/plain;charset=UTF-8")
  public String list_by_fboardno_join_add(int fboardno) {
    // String msg="JSON 출력";
    // return msg;
    
    List<ReplyMemberVO> list = replyProc.list_by_fboardno_join_add(fboardno);
    
	fboardProc.increaseReplycnt(fboardno);
	fboardProc.decreaseReplycnt(fboardno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  
}