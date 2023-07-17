package dev.mvc.costip;

import java.util.ArrayList;

public interface CostipDAOInter {
  
  /**
   * 등록
   * @param costipVO
   * @return
   */
  public int create(CostipVO costipVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList <CostipVO> list_all();

}
