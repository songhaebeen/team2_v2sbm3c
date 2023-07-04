package dev.mvc.cosme_youtube_tip;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cosme.CosmeProcInter;
import dev.mvc.cosme_cate.Cosme_cateProcInter;
import dev.mvc.cosme.CosmeVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class Cosme_youtube_tipCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") 
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.cosme.CosmeProc") 
  private CosmeProcInter cosmeProc;
  
  @Autowired
  @Qualifier("dev.mvc.cosme_cate.Cosme_cateProc") 
  private Cosme_cateProcInter cosme_cateProc;
  
  @Autowired
  @Qualifier("dev.mvc.cosme_youtube_tip.Cosme_youtube_tipProc") 
  private Cosme_youtube_tipProcInter cosme_youtube_tipProc;
  
  public Cosme_youtube_tipCont () {
    System.out.println("-> Cosme_youtube_tipCont created.");
  }
  
    // 등록 폼, cosme_yotube_tip 테이블은 FK로 cosmeno를 사용함.
    // http://localhost:9093/cosme_youtube_tip/create.do  X
    // http://localhost:9093/cosme_youtube_tip/create.do?cosmeno=1
    @RequestMapping(value="/cosme_youtube_tip/create.do", method = RequestMethod.GET)
    public ModelAndView create(int cosmeno) {
      ModelAndView mav = new ModelAndView();
      
      CosmeVO cosmeVO = this.cosmeProc.cosme_read(cosmeno); // create.jsp에 화장품 정보를 출력하기위한 목적
      mav.addObject("cosmeVO", cosmeVO);
      
      mav.setViewName("/cosme_youtube_tip/create"); // /webapp/WEB-INF/views/cosme_youtube_tip/create.jsp
      
      return mav;
    }
    
    /**
     * 등록 처리 http://localhost:9093/cosme_youtube_tip/create.do
     * 
     * @return
     */
    @RequestMapping(value = "/cosme_youtube_tip/create.do", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpSession session, Cosme_youtube_tipVO cosme_youtube_tipVO) {
      ModelAndView mav = new ModelAndView();
      
      if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
        if (cosme_youtube_tipVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
          // youtube 영상의 크기를 width 기준 640 px로 변경 
          String youtube = Tool.youtubeResize(cosme_youtube_tipVO.getYoutube());
          cosme_youtube_tipVO.setYoutube(youtube);
        }
        
        int cnt = this.cosme_youtube_tipProc.create(cosme_youtube_tipVO);
        
        if (cnt == 1) {
          mav.setViewName("redirect:/cosme_youtube_tip/read.do?cosme_youtube_tipno=" + cosme_youtube_tipVO.getYoutubeno()); 
        } else {
          mav.addObject("code", "youtube_fail");
          mav.setViewName("redirect:/cosme_youtube_tip/msg.do"); 
        }
        mav.addObject("cnt", cnt); 
  
        // /webapp/WEB-INF/views/cosme_youtube_tip/read.jsp
      } else {
      mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/admin/msg.do"); 
    }

      
      return mav;
    }
    
    /**
     * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
     * @return
     */
    @RequestMapping(value="/cosme_youtube_tip/msg.do", method=RequestMethod.GET)
    public ModelAndView msg(String url) {
      ModelAndView mav = new ModelAndView();
      
      mav.setViewName(url); // forward
      
      return mav; //forward
    }
    
    /**
     * 모든 화장품의 등록된 유튜브 목록, http://localhost:9093/comse_youtube_tip/list_youtube_all.do
     * @return
     */
    @RequestMapping(value="cosme_youtube_tip/list_youtube_all.do", method=RequestMethod.GET)
    public ModelAndView list_youtube_all() {
      ModelAndView mav = new ModelAndView();
      
      ArrayList<Cosme_youtube_tipVO> list = this.cosme_youtube_tipProc.list_youtube_all();
      mav.addObject("list", list);
      
      mav.setViewName("cosme_youtube_tip/list_youtube_all"); // /webapp/WEB-INF/views/cosme_youtube_tip/list_youtube_all.jsp
      
      return mav;
    }
    
    /**
     * 특정 화장품의 등록된 유튜브 목록, http://localhost:9093/comse_youtube_tip/list_youtube_cosmeno.do
     * @return
     */
    @RequestMapping(value="cosme_youtube_tip/list_youtube_cosmeno.do", method=RequestMethod.GET)
    public ModelAndView list_youtube_cosmeno(int cosmeno) {
      ModelAndView mav = new ModelAndView();
      
      ArrayList<Cosme_youtube_tipVO> list = this.cosme_youtube_tipProc.list_youtube_cosmeno(cosmeno);
      mav.addObject("list", list);
      
      mav.setViewName("cosme_youtube_tip/list_youtube_cosmeno"); // /webapp/WEB-INF/views/cosme_youtube_tip/list_youtube_cosmeno.jsp
      
      return mav;
    }
    
    /**
     * 조회
     * @return
     */
    @RequestMapping(value="/cosme_youtube_tip/read.do", method=RequestMethod.GET)
    public ModelAndView read(int youtubeno) {
      ModelAndView mav = new ModelAndView();
      
      Cosme_youtube_tipVO cosme_youtube_tipVO = this.cosme_youtube_tipProc.read(youtubeno);
      
      String youtubetitle = cosme_youtube_tipVO.getYoutubetitle();
      String youtubecontent = cosme_youtube_tipVO.getYoutubecontent();
      
      youtubetitle = Tool.convertChar(youtubetitle);
      youtubecontent = Tool.convertChar(youtubecontent);
      
      mav.addObject("cosme_youtube_tipVO", cosme_youtube_tipVO);
      
      CosmeVO cosmeVO = this.cosmeProc.cosme_read(cosme_youtube_tipVO.getCosmeno()); // 화장품 정보 읽기
      mav.addObject("cosmeVO", cosmeVO);
      
      mav.setViewName("/cosme_youtube_tip/read"); // /WEB-INF/views/cosme_youtube_tip/read.jsp
      
      return mav;
    }
    
    // 수정 폼, cosme_yotube_tip 테이블은 FK로 youtubeno를 사용함.
    // http://localhost:9093/cosme_youtube_tip/update.do  X
    // http://localhost:9093/cosme_youtube_tip/update.do?cosmeno=1
    @RequestMapping(value="/cosme_youtube_tip/update.do", method = RequestMethod.GET)
    public ModelAndView update(int youtubeno) {
      ModelAndView mav = new ModelAndView();

      Cosme_youtube_tipVO cosme_youtube_tipVO = this.cosme_youtube_tipProc.read(youtubeno);
      mav.addObject("cosme_youtube_tipVO", cosme_youtube_tipVO);
      
      CosmeVO cosmeVO = this.cosmeProc.cosme_read(cosme_youtube_tipVO.getCosmeno());
      mav.addObject("cosmeVO", cosmeVO);
      
      mav.setViewName("/cosme_youtube_tip/update"); // /webapp/WEB-INF/views/cosme_youtube_tip/update.jsp
      
      return mav;
    }
    
    /**
     * 수정 처리 http://localhost:9093/cosme_youtube_tip/update.do?youtubeno=2
     * 
     * @return
     */
    @RequestMapping(value = "/cosme_youtube_tip/update.do", method = RequestMethod.POST)
    public ModelAndView update(HttpSession session, Cosme_youtube_tipVO cosme_youtube_tipVO) {
      ModelAndView mav = new ModelAndView();

      if (adminProc.isAdmin(session)) { // 관리자로 로그인한경우
        mav.addObject("cosmeno", cosme_youtube_tipVO.getCosmeno());
        mav.addObject("youtube", cosme_youtube_tipVO.getYoutubeno());
        
        if (cosme_youtube_tipVO.getYoutube().trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
          // youtube 영상의 크기를 width 기준 640 px로 변경 
          String youtube = Tool.youtubeResize(cosme_youtube_tipVO.getYoutube());
          cosme_youtube_tipVO.setYoutube(youtube);
        }
        
        int cnt = this.cosme_youtube_tipProc.update(cosme_youtube_tipVO);
        
        if (cnt == 1) {
          mav.setViewName("redirect:/cosme_youtube_tip/read.do?cosme_youtube_tipno=" + cosme_youtube_tipVO.getYoutubeno()); 
        } else {
          mav.addObject("code", "update_fail");
          mav.setViewName("redirect:/cosme_youtube_tip/msg.do"); 
        }
        mav.addObject("cnt", cnt); 
  
        // /webapp/WEB-INF/views/cosme_youtube_tip/read.jsp
      } else {
        mav.addObject("url", "/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
        mav.setViewName("redirect:/admin/msg.do");
      }
      
      return mav;
    }

    
    /**
     * 삭제 폼
     * @param youtubeno
     * @return
     */
    @RequestMapping(value= "cosme_youtube_tip/delete.do", method=RequestMethod.GET )
    public ModelAndView delete(int youtubeno) {
      ModelAndView mav = new ModelAndView();
      
      // 삭제할 정보를 조회하여 확인
      Cosme_youtube_tipVO cosme_youtube_tipVO = this.cosme_youtube_tipProc.read(youtubeno);
      mav.addObject("cosme_youtube_tipVO", cosme_youtube_tipVO);
      
      CosmeVO cosmeVO = this.cosmeProc.cosme_read(cosme_youtube_tipVO.getCosmeno());
      mav.addObject("cosmeVO", cosmeVO);
      
      mav.setViewName("/cosme_youtube_tip/delete"); // /webapp/WEB-INF/views/comse_youtube_tip/delete.jsp
      return mav;
    }
    
    /**
     * 삭제 처리 http://localhost:9093/cosme_youtube_tip/delete.do
     * 
     * @return
     */
    @RequestMapping(value = "/cosme_youtube_tip/delete.do", method = RequestMethod.POST)
    public ModelAndView delete(HttpSession session, Cosme_youtube_tipVO cosme_youtube_tipVO) {
      ModelAndView mav = new ModelAndView();
      
      if (this.adminProc.isAdmin(session) == true) {
        Cosme_youtube_tipVO cosme_youtube_tipVO_read = cosme_youtube_tipProc.read(cosme_youtube_tipVO.getYoutubeno());
        this.cosme_youtube_tipProc.delete(cosme_youtube_tipVO.getYoutubeno()); // DBMS 삭제
        
        mav.addObject("cosmeno", cosme_youtube_tipVO.getCosmeno());
        mav.setViewName("redirect:/cosme_youtube_tip/list_youtube_cosmeno.do");
        
      } else {
        mav.setViewName("/admin/login_need"); // /WEB-INF/views/admin/login_need.jsp
      }
      
      return mav;
    }
    
    /**
     * 목록 + 검색 + 페이징 지원
     * http://localhost:9093/cosme_youtube_tip/list_by_cosmeno_search_paging.do?cosmeno=1&word=로션&now_page=1
     * 
     * @param cosmeno
     * @param word
     * @param now_page
     * @return
     */
//    @RequestMapping(value = "/cosme_youtube_tip/list_by_cosmeno_search_paging.do", method = RequestMethod.GET)
//    public ModelAndView list_by_cosmeno_search_paging(Cosme_youtube_tipVO cosme_youtube_tipVO) {
//      ModelAndView mav = new ModelAndView();
//
//      // 검색된 전체 글 수
//      int search_count = this.cosme_youtube_tipProc.search_count(cosme_youtube_tipVO);
//      mav.addObject("search_count", search_count);
//      
//      // 검색 목록: 검색된 레코드를 페이지 단위로 분할하여 가져옴
//      ArrayList<Cosme_youtube_tipVO> list = cosme_youtube_tipProc.list_by_cosmeno_search_paging(cosme_youtube_tipVO);
//      mav.addObject("list", list);
//      
//      CosmeVO cosmeVO = cosmeProc.cosme_read(CosmeVO.getCosmeno());
//      mav.addObject("cosmeVO", cosmeVO);
//
//      /*
//       * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
//       * 18 19 20 [다음]
//       * @param cosmeno 화장품번호
//       * @param now_page 현재 페이지
//       * @param word 검색어
//       * @return 페이징용으로 생성된 HTML/CSS tag 문자열
//       */
//      String paging = cosme_youtube_tipProc.pagingBox(cosme_youtube_tipVO.getCosmeno(), cosme_youtube_tipVO.getNow_page(), cosme_youtube_tipVO.getWord(), "list_youtube_cosmeno.do");
//      mav.addObject("paging", paging);
//
//      // mav.addObject("now_page", now_page);
//      
//      mav.setViewName("/cosme_youtube_tip/list_by_cosmeno_search_paging");  // /cosme_youtube_tip/list_by_cosmeno_search_paging.jsp
//
//      return mav;
//    }
//    
}
