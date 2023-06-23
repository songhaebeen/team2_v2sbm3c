package dev.mvc.ingred;

import java.util.ArrayList;

public interface IngredDAOInter {
	
  /**
   * 등록
   * @param ingredvo
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

}
