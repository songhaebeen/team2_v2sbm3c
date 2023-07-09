package dev.mvc.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.tool.MailTool;

@Controller
public class MailCont {
    public MailCont() {
        System.out.println("-> MailCont created.");
      }
    
    // http://localhost:9091/mail/form.do
    /**
     * 메일 입력 폼
     * @return
     */
    @RequestMapping(value = {"/mail/form.do"}, method=RequestMethod.GET)
    public ModelAndView form() {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/form");  // /WEB-INF/views/mail/form.jsp

      return mav;
    }
    
    // http://localhost:9091/mail/send.do
    /**
     * 메일 전송
     * @return
     */
    @RequestMapping(value = {"/mail/send.do"}, method=RequestMethod.POST)
    public ModelAndView send(String receiver, String from, String title, String content) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/sended");  // /WEB-INF/views/mail/sended.jsp

      MailTool mailTool = new MailTool();
      mailTool.send(receiver, from, title, content); // 메일 전송
      
      return mav;
    }
    
    // http://localhost:9091/mail/form_file.do
    /**
     * 파일 첨부 메일 입력폼
     * @return
     */
    @RequestMapping(value = {"/mail/form_file.do"}, method=RequestMethod.GET)
    public ModelAndView form_file() {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/form_file");  // /WEB-INF/views/mail/form_file.jsp

      return mav;
    }
    
//    // http://localhost:9091/mail/send_file.do
//    /**
//     * 메일 전송
//     * @return
//     */
//    @RequestMapping(value = {"/mail/send_file.do"}, method=RequestMethod.POST)
//    public ModelAndView send_file(String receiver, String from, String title, String content,
//                                             MultipartFile file1MF) {
//      ModelAndView mav = new ModelAndView();
//      mav.setViewName("/mail/sended");  // /WEB-INF/views/mail/sended.jsp
//
//      MailTool mailTool = new MailTool();
//      mailTool.send_file(receiver, from, title, content, file1MF, "C:/kd/deploy/mail/images/"); // 메일 전송
//      
//      return mav;
//    }
    
    // http://localhost:9091/mail/send_file.do
    /**
     * 메일 전송
     * @return
     */
    @RequestMapping(value = {"/mail/send_file.do"}, method=RequestMethod.POST)
    public ModelAndView send_file(String receiver, String from, String title, String content,
                                             MultipartFile[] file1MF) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/mail/sended");  // /WEB-INF/views/mail/sended.jsp

      MailTool mailTool = new MailTool();
      mailTool.send_file(receiver, from, title, content, file1MF, "C:/kd/deploy/mvc/mail/images/"); // 메일 전송
      
      return mav;
    }
    
}