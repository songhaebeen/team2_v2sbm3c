package dev.mvc.costip;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class CostipCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.costip.CostipProc")
  private CostipProcInter costipProc;
  
 public CostipCont() {
   System.out.println("-> CostipCotn created.");
 }
 
 /**
  * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
  * 
  * @return
  */
 @RequestMapping(value = "/costip/msg.do", method = RequestMethod.GET)
 public ModelAndView msg(String url) {
   ModelAndView mav = new ModelAndView();

   mav.setViewName(url); // forward

   return mav; // forward
 }

 // http://localhost:9093/costip/create.do?costipno=1
 @RequestMapping(value = "/costip/create.do", method = RequestMethod.GET)
 public ModelAndView create() {
   ModelAndView mav = new ModelAndView();

   mav.setViewName("/costip/create");

   return mav;
 }

 /**
  * 등록 처리 http://localhost:9093/costip/create.do
  * 
  * @return
  */
 @RequestMapping(value = "/costip/create.do", method = RequestMethod.POST)
 public ModelAndView create(HttpServletRequest request, HttpSession session, CostipVO costipVO) {
   ModelAndView mav = new ModelAndView();

   if (memberProc.isMember(session)) { // 회원으로 로그인한경우
     // ------------------------------------------------------------------------------
     // 파일 전송 코드 시작
     // ------------------------------------------------------------------------------
     String file1 = ""; // 원본 파일명 image
     String file1saved = ""; // 저장된 파일명, image
     String thumb1 = ""; // preview image

     String upDir = Costip.getUploadDir(); // 파일 업로드 폴더 읽기
     System.out.println("-> upDir: " + upDir);

     // 전송 파일이 없어도 file1MF 객체가 생성됨.
     // <input type='file' class="form-control" name='file1MF' id='file1MF'
     // value='' placeholder="파일 선택">
     MultipartFile mf = costipVO.getFile1MF();

     file1 = Tool.getFname(mf.getOriginalFilename()); // 원본 순수 파일명 산출
     System.out.println("-> file1: " + file1);

     long size1 = mf.getSize(); // 파일 크기

     if (size1 > 0) { // 파일 크기 체크
       // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
       file1saved = Upload.saveFileSpring(mf, upDir);

       if (Tool.isImage(file1saved)) { // 이미지인지 검사
         // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
         thumb1 = Tool.preview(upDir, file1saved, 200, 150);
       }

     }

     costipVO.setFile1(file1); // 순수 원본 파일명
     costipVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
     costipVO.setThumb1(thumb1); // 원본이미지 축소판
     costipVO.setSize1(size1); // 파일 크기
     // ------------------------------------------------------------------------------
     // 파일 전송 코드 종료
     // ------------------------------------------------------------------------------

     // Call By Reference: 메모리 공유, Hashcode 전달
     int memberno = (int) session.getAttribute("memberno"); // adminno FK
     costipVO.setMemberno(memberno);
     int cnt = this.costipProc.create(costipVO); // oracle sql 실행

     // ------------------------------------------------------------------------------
     // PK의 return
     // ------------------------------------------------------------------------------
     // System.out.println("--> contentsno: " + contentsVO.getContentsno());
     // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
     // parameter 적용
     // ------------------------------------------------------------------------------

     if (cnt == 1) {
       mav.addObject("code", "create_success");
       // cateProc.increaseCnt(contentsVO.getCateno()); // 글수 증가
     } else {
       mav.addObject("code", "create_fail");
     }
     mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)

     mav.addObject("url", "/costip/msg"); // msg.jsp, redirect parameter 적용
     mav.setViewName("redirect:/costip/msg.do");

   } else {
     mav.addObject("url", "/member/login_need"); // /WEB-INF/views/admin/login_need.jsp
     mav.setViewName("redirect:/costip/msg.do");
   }

   return mav; // forward
 }
 
 @RequestMapping(value="/costip/list_all.do", method=RequestMethod.GET)
 public ModelAndView list_all() {
   ModelAndView mav = new ModelAndView();
   
   ArrayList<CostipVO> list = this.costipProc.list_all();
   mav.addObject("list",list);
   
   mav.setViewName("/costip/list_all");
   
   return mav;
 }
 
 @RequestMapping(value="/costip/list_by_costipno.do",method=RequestMethod.GET)
 public ModelAndView list_by_costipno(int costipno) {
   ModelAndView mav = new ModelAndView();
   
   CostipVO costipVO = this.costipProc.read(costipno);
   mav.addObject("costipVo", costipVO);
   
   ArrayList<CostipVO> list = this.costipProc.list_by_costipno();
   mav.addObject("list", list);
   
   mav.setViewName("/costip/list_by_costipno");
   
   return mav;
 }
 
 @RequestMapping(value="/costip/read.do", method=RequestMethod.GET)
 public ModelAndView read(int costipno) {
   ModelAndView mav = new ModelAndView();
   
   CostipVO costipVO = this.costipProc.read(costipno);
   
   String title = costipVO.getTitle();
   String content = costipVO.getContent();
   
   title = Tool.convertChar(title);
   content = Tool.convertChar(content);
   
   costipVO.setTitle(title);
   costipVO.setContent(content);
   
   long size1 = costipVO.getSize1();
   costipVO.setSize1_label(Tool.unit(size1));
   
   mav.addObject("costipVO", costipVO);
   
   mav.setViewName("/costip/read");
   
   return mav;
   
 }
 
 @RequestMapping(value="/costip/youtube.do", method=RequestMethod.GET)
 public ModelAndView youtube(int costipno) {
   ModelAndView mav = new ModelAndView();
   
   CostipVO costipVO = this.costipProc.read(costipno);
   mav.addObject("costipVO", costipVO);
   
   mav.setViewName("/costip/youtube");
   
   return mav;
 }
 
 @RequestMapping(value="/costip/youtube.do", method = RequestMethod.POST)
 public ModelAndView youtube_update(CostipVO costipVO) {
   ModelAndView mav = new ModelAndView();
   
   this.costipProc.youtube(costipVO);
   
   mav.setViewName("redirect:/costip/read.do?costipno=" + costipVO.getCostipno());
   
   return mav;
 }
 

}
