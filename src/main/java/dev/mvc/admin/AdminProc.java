package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO;
  
  public AdminProc() {
    
  }
  
  @Override
  public int checkID(String id) {
    int cnt = this.adminDAO.checkID(id);
    return cnt;
  }
  
  @Override
  public int create(AdminVO adminVO) {
    int cnt = this.adminDAO.create(adminVO);
    return cnt;
  }
  
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
  
  @Override
  public ArrayList<AdminVO> list() {
    ArrayList<AdminVO> list = this.adminDAO.list();
    return list;
  }
  
  @Override
  public int update(AdminVO adminVO) {
    int cnt = this.adminDAO.update(adminVO);
    return cnt;
  }
  
  @Override
  public int delete(int adminno) {
    int cnt = this.adminDAO.delete(adminno);
    return cnt;
  }
  
  @Override
  public int passwd_check(HashMap<Object, Object> map) {
    int cnt = this.adminDAO.passwd_check(map);
    return cnt;
  }
  
  @Override
  public int passwd_update(HashMap<Object, Object> map) {
    int cnt = this.adminDAO.passwd_update(map);
    return cnt;
  }
  
  
  
}



