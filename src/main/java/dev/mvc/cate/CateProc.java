package dev.mvc.cate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cate.CateProc") // Controller가 사용하는 이름
public class CateProc implements CateProcInter {
  @Autowired
  private CateDAOInter cateDAO; // Spring이 CateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(CateVO cateVO) {
    int cnt = this.cateDAO.create(cateVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  @Override
  public ArrayList<CateVO> list_all() {
    ArrayList<CateVO> list = this.cateDAO.list_all();
    
    return list;
  }

  @Override
  public CateVO read(int cateno) {
    CateVO cateVO = this.cateDAO.read(cateno);
    
    return cateVO;
  }

  @Override
  public int update(CateVO cateVO) {
    int cnt = this.cateDAO.update(cateVO);
    
    return cnt;
  }

  @Override
  public int delete(int cateno) {
    int cnt = this.cateDAO.delete(cateno);
    return cnt;
  }

  @Override
  public int update_seqno_decrease(int cateno) {
    int cnt = this.cateDAO.update_seqno_decrease(cateno);
    return cnt;
  }

  @Override
  public int update_seqno_increase(int cateno) {
    int cnt = this.cateDAO.update_seqno_increase(cateno);
    return cnt;
  }
  
  @Override
  public int update_visible_y(int cateno) {
    int cnt = this.cateDAO.update_visible_y(cateno);
    return cnt;
  }
  
  @Override
  public int update_visible_n(int cateno) {
    int cnt = this.cateDAO.update_visible_n(cateno);
    return cnt;
  }

  @Override
  public ArrayList<CateVO> list_all_y() {
    ArrayList<CateVO> list = this.cateDAO.list_all_y();
    return list;
  }
  
  @Override
  public int update_cnt_add(int cateno) {
    int cnt = this.cateDAO.update_cnt_add(cateno);
    return cnt;
  }
  
  @Override
  public int update_cnt_sub(int cateno) {
    int cnt = this.cateDAO.update_cnt_sub(cateno);
    return cnt;
  }
  
}





