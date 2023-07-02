package dev.mvc.cosmetype;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.member.MemberProcInter;
import dev.mvc.admin.AdminProcInter;
import dev.mvc.cosme_cate.Cosme_cateVO;


@Controller
public class CosmetypeCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc = null;
    
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc = null;
  
  @Autowired
  @Qualifier("dev.mvc.cosmetype.CosmetypeProc")
  private CosmetypeProcInter cosmetypeProc;

  public CosmetypeCont() {
    System.out.println("-> CosmetypeCont created.");
    
  }

  /**
   * 등록폼
   * http://localhost:9093/cosmetype/create.do
   * @return
   */
 @RequestMapping(value="/cosmetype/create.do", method=RequestMethod.GET)
public ModelAndView create(HttpSession session) {
 
 ModelAndView mav = new ModelAndView();
 
 if (this.adminProc.isAdmin(session) == true) {
   mav.setViewName("/cosmetype/create");
 } else {
     mav.setViewName("/admin/login_need");
   }

 
 return mav;
}
 
/**
 * 등록 처리
 * 
 * @param cosmetypeVO
 * @return
 */
 @RequestMapping(value="/cosmetype/create.do", method=RequestMethod.POST)
 public ModelAndView create(CosmetypeVO cosmetypeVO, HttpSession session) { 
   
   ModelAndView mav = new ModelAndView();
   
   if (this.adminProc.isAdmin(session) == true) {
     int cnt = this.cosmetypeProc.create(cosmetypeVO);
       if (cnt == 1) {
         mav.setViewName("redirect:/"); 
       } else {
         mav.addObject("code", "create_fail");
         mav.setViewName("/cosmetype/msg");

       }
       mav.addObject("cnt", cnt);
   } else {
       mav.setViewName("/admin/login_need");
     }

   return mav;
 }
 
 /**
  * 목록
  * http://localhost:9093/cosmetype/list_all.do
  * @return
  */
@RequestMapping(value="/cosmetype/list_all.do", method=RequestMethod.GET)
 public ModelAndView list_all() {
   ModelAndView mav = new ModelAndView();
   
        mav.setViewName("/cosmetype/list_all"); 
        
        ArrayList<CosmetypeVO> list = this.cosmetypeProc.list_all();
        mav.addObject("list", list);   
        
   return mav;
}
 
// 수정폼
// http://localhost:9093/cosmetype/read_update.do?cosmetypeno=1
// http://localhost:9093/cosmetype/read_update.do?cosmetypeno=2
// http://localhost:9093/cosmetype/read_update.do?cosmetypeno=3
@RequestMapping(value="/cosmetype/read_update.do", method=RequestMethod.GET)
public ModelAndView read_update(HttpSession session, int cosmetypeno) {
  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session) == true) {
    mav.setViewName("/cosmetype/read_update");
    
    CosmetypeVO cosmetypeVO = this.cosmetypeProc.read(cosmetypeno); // 수정용 데이터
    mav.addObject("cosmetypeVO", cosmetypeVO);
    
    ArrayList<CosmetypeVO> list = this.cosmetypeProc.list_all(); // 목록 출력용 데이터
    mav.addObject("list", list);
    
  } else {
    mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
    
  }
  
  return mav;
}

// 수정 처리
@RequestMapping(value="/cosmetype/update.do", method=RequestMethod.POST)
public ModelAndView update(HttpSession session, CosmetypeVO cosmetypeVO) { 
  
  ModelAndView mav = new ModelAndView();

  if (this.adminProc.isAdmin(session) == true) {
    int cnt = this.cosmetypeProc.update(cosmetypeVO);
    
    if (cnt == 1) {
      // request.setAttribute("code", "update_success");
      // mav.addObject("code", "update_success");
      mav.setViewName("redirect:/cosmetype/list_all.do");  
      
    } else {
      // request.setAttribute("code", "update_fail");
      mav.addObject("code", "update_fail");
      mav.setViewName("/cosmetype/msg"); 
    }
    
    mav.addObject("cnt", cnt);
    
  } else {
    mav.setViewName("/admin/login_need"); 
  }
  
  return mav;
}

// 삭제폼, 수정폼을 복사하여 개발 
// http://localhost:9091/cosmetype/read_delete.do?cosmetypeno=1
@RequestMapping(value="/cosmetype/read_delete.do", method=RequestMethod.GET)
public ModelAndView read_delete(HttpSession session, int cosmetypeno) {
  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session) == true) {
    CosmetypeVO cosmetypeVO = this.cosmetypeProc.read(cosmetypeno); // 수정용 데이터
    mav.addObject("cosmetypeVO", cosmetypeVO);
    
    ArrayList<CosmetypeVO> list = this.cosmetypeProc.list_all(); // 목록 출력용 데이터
    mav.addObject("list", list);
    
    mav.setViewName("/cosmetype/read_delete");
    
  } else {
    mav.setViewName("/admin/login_need"); 
  }
  
  return mav;
}

// 삭제 처리, 수정 처리를 복사하여 개발
@RequestMapping(value="/cosmetype/delete.do", method=RequestMethod.POST)
public ModelAndView delete(HttpSession session, int cosmetypeno) { 

  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session) == true) {
    ArrayList<CosmetypeVO> list = this.cosmetypeProc.list_all(); 
          
    int cnt = this.cosmetypeProc.delete(cosmetypeno); // 카테고리 삭제
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cosmetype/list_all.do");       // 자동 주소 이동, Spring 재호출
      
    } else {
      mav.addObject("code", "delete_fail");
      mav.setViewName("/cosmetype/msg"); 
    }
    
    mav.addObject("cnt", cnt); 
    
  } else {
    mav.setViewName("/admin/login_need"); 
  }
  
  return mav;
}

// http://localhost:9093/cosmetype/read.do?cosme_cateno=1
@RequestMapping(value="/cosmetype/read.do", method=RequestMethod.GET)
public ModelAndView read(HttpSession session, int cosmetypeno) {
  ModelAndView mav = new ModelAndView();
  
  if (this.adminProc.isAdmin(session) == true) {
    mav.setViewName("/cosmetype/read"); 
    
    CosmetypeVO cosmetypeVO = this.cosmetypeProc.read(cosmetypeno);
    mav.addObject("cosmetypeVO", cosmetypeVO);
  } else {
    mav.setViewName("/admin/login_need"); 
  }
  
  return mav;
}

/**
 * 출력 순서 올림
 * @param cosmetypeno
 * @return
 */
@RequestMapping(value = "/cosmetype/update_seqno_decrease.do", method = RequestMethod.GET)
public ModelAndView update_seqno_decrease(int cosmetypeno) {
  ModelAndView mav = new ModelAndView();
  
  // seqno 컬럼의 값이 1 초과(1<)일때만 감소를 할 수 있다.
  CosmetypeVO cosmetypeVO = this.cosmetypeProc.read(cosmetypeno);
  int seqno = cosmetypeVO.getSeqno();

  // 2) if 문을 이용한 분기
  if (seqno > 1) {
    int cnt = this.cosmetypeProc.update_seqno_decrease(cosmetypeno); 
    mav.addObject("cnt", cnt);
    
    if (cnt == 1) {
      mav.setViewName("redirect:/cosmetype/list_all.do");

    } else {
      mav.addObject("code", "update_seqno_decrease_fail");
      mav.setViewName("/cosmetype/msg"); 
    }
    
  } else {
    mav.setViewName("redirect:/cosmetype/list_all.do");
  }
  
  return mav;
}


/**
 * 출력 순서 내림(상향, 1 등 -> 10 등), seqno: 1 -> 10
 * @param cosmetypeno
 * @return
 */
@RequestMapping(value = "/cosmetype/update_seqno_increase.do", method = RequestMethod.GET)
public ModelAndView update_seqno_increase(int cosmetypeno) {
  ModelAndView mav = new ModelAndView();
  int cnt = this.cosmetypeProc.update_seqno_increase(cosmetypeno); 
  mav.addObject("cnt", cnt);
  
  if (cnt == 1) {
    mav.setViewName("redirect:/cosmetype/list_all.do");

  } else {
    mav.addObject("code", "update_seqno_increase_fail");
    mav.setViewName("/cosmetype/msg"); 
  }
  
  return mav;
}





 
 }
