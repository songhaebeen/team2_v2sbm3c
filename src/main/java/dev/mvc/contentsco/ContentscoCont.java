package dev.mvc.contentsco;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cateco.CatecoProcInter;
import dev.mvc.cateco.CatecoVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ContentscoCont {
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.cateco.CatecoProc")
  private CatecoProcInter catecoProc;
  
  @Autowired
  @Qualifier("dev.mvc.contentsco.contentscoProc")
  private ContentscoProcInter contentscoProcInter;
  
  public ContentscoCont() {
    System.out.println("-> ContentscoCont created.");
  }
  
  // 등록 폼
  // http://localhost:9093/contentsco/create.do?catecono=1
  @RequestMapping(value="/contentsco/create.do", method=RequestMethod.GET)
  public ModelAndView create(int catecono) {
    //public ModelAndView create(HttpServletRequest request,  int cateno) {
    ModelAndView mav = new ModelAndView();
    
    CatecoVO catecoVO = this.catecoProc.read(catecono);
    mav.addObject("catecoVO", catecoVO);
    
    mav.setViewName("/contentsco/create");
    
    return mav;
  }
  
  
  
  
  
  
  
  
  
  
  
}