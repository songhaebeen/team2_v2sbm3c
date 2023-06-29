package dev.mvc.ingred;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.ingred.IngredProc")
public class IngredProc implements IngredProcInter {
	
	@Autowired
	public IngredDAOInter ingredDAO;

	//등록
  @Override
  public int create(IngredVO ingredVO) {
    int cnt = this.ingredDAO.create(ingredVO);
    return cnt;
  }

  //목록
  @Override
  public ArrayList<IngredVO> list_all() {
   ArrayList<IngredVO>list = this.ingredDAO.list_all();
    return list;
  }

  //조회
  @Override
  public IngredVO read(int ingredno) {
    IngredVO ingredVO = this.ingredDAO.read(ingredno);
    return ingredVO;
  }

  //수정
  @Override
  public int update(IngredVO ingredVO) {
    int cnt = this.ingredDAO.update(ingredVO);
    return cnt;
  }

  //삭제
  @Override
  public int delete(int ingredno) {
    int cnt = this.ingredDAO.delete(ingredno);
    return cnt;
  }

  @Override
  public int update_seqno_decrease(int ingredno) {
    int cnt = this.ingredDAO.update_seqno_decrease(ingredno);
    return cnt;
  }

  @Override
  public int update_seqno_increase(int ingredno) {
    int cnt = this.ingredDAO.update_seqno_increase(ingredno);
    return cnt;
  }

}
