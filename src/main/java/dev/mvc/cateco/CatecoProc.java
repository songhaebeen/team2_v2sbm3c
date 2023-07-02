package dev.mvc.cateco;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cateco.CatecoProc") // Controller가 사용하는 이름
public class CatecoProc implements CatecoProcInter {
  @Autowired
  private CatecoDAOInter catecoDAO; // Spring이 CateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  // 등록
  @Override
  public int create(CatecoVO catecoVO) {
    int cnt = this.catecoDAO.create(catecoVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }

  // 목록
  @Override
  public ArrayList<CatecoVO> list_all() {
    ArrayList<CatecoVO> list = this.catecoDAO.list_all();
    return list;
  }

  // 조회
  @Override
  public CatecoVO read(int catecono) {
    CatecoVO catecoVO = this.catecoDAO.read(catecono);
    return catecoVO;
  }

  // 수정
  @Override
  public int update(CatecoVO catecoVO) {
    int cnt = this.catecoDAO.update(catecoVO);
    return cnt;
  }

  // 삭제
  @Override
  public int delete(int catecono) {
    int cnt = this.catecoDAO.delete(catecono);
    return cnt;
  }

  // 출력 순서 상향
  @Override
  public int update_seqno_decrease(int catecono) {
    int cnt = this.catecoDAO.update_seqno_decrease(catecono);
    return cnt;
  }

  // 출력 순서 하향
  @Override
  public int update_seqno_increase(int catecono) {
    int cnt = this.catecoDAO.update_seqno_increase(catecono);
    return cnt;
  }

  @Override
  public int update_cnt_add(int catecono) {
    int cnt = this.catecoDAO.update_cnt_add(catecono);
    return cnt;
  }

  @Override
  public int update_cnt_sub(int catecono) {
    int cnt = this.catecoDAO.update_cnt_sub(catecono);
    return cnt;
  }


  
}





