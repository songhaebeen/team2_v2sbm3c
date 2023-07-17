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
  
  /**
   * 특정 카테고리의 등록된 글 목록
   * @return
   */
  public ArrayList <CostipVO> list_by_costipno();
  
  /**
   * 조회
   * @param costipno
   * @return
   */
  public CostipVO read(int costipno);
  
  /**
   * Youtube
   * @param costipVO
   * @return
   */
  public int youtube(CostipVO costipVO);

}
