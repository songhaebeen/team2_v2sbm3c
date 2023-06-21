package dev.mvc.admin;

public interface AdminDAOInter {
  /**
   * 로그인
   * @param AdminVO
   * @return
   */
  public int login(AdminVO adminVO);
  
  /**
   * id를 통한 회원 정보
   * @param String
   * @return
   */
  public AdminVO read_by_id(String id);
  
  
  /**
   * adminno를 통한 회원 정보
   * @param adminno 회원 번호
   * @return
   */
  public AdminVO read(int adminno);
  
}




