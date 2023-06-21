package dev.mvc.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO;
  
  @Override
  public int login(AdminVO adminVO) {
    int cnt = this.adminDAO.login(adminVO);
    return cnt;
  }

  @Override
  public AdminVO read_by_id(String id) {
    AdminVO adminVO = this.adminDAO.read_by_id(id);
    return adminVO;
  }

  @Override
  public boolean isAdmin(HttpSession session) {
    boolean admin_sw = false;
    
    if (session != null) {
      String admin_id = (String)session.getAttribute("admin_id");
      
      if (admin_id != null) {
        admin_sw = true; // 정상적으로 로그인 한 경우
      }
    }
    
    return admin_sw;
  }

  @Override
  public AdminVO read(int adminno) {
    AdminVO adminVO = this.adminDAO.read(adminno);
    return adminVO;
  }
  
  
  
}



