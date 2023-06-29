package dev.mvc.ingred;

import java.util.ArrayList;

public interface IngredProcInter {
	
  /**
   * 등록
   * @param ingredVO
   * @return
   */
  public int create(IngredVO ingredVO);
  
  /**
   * 목록
   * @return
   */
  public ArrayList<IngredVO> list_all();
  
  /**
   * 조회
   * @param ingredno
   * @return
   */
  public IngredVO read(int ingredno);
  
  /**
   * 수정
   * @param ingredVO
   * @return
   */
  public int update(IngredVO ingredVO);
  
  /**
   * 삭제
   * @param ingredVO
   * @return
   */
  public int delete(int ingredno);

  /**
   * 출력 순서 상향
   * @param ingredno
   * @return
   */
  public int update_seqno_decrease(int ingredno);
  
  /**
   * 출력 순서 하향
   * @param ingredno
   * @return
   */
  public int update_seqno_increase(int ingredno);
}
