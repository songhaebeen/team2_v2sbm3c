package dev.mvc.costip;

import java.util.ArrayList;

public interface CostipProcInter {
  
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
  
  /**
   * 패스워드 검사
   * @param costipVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치 
   */
  public int password_check(CostipVO costipVO);
  
  /**
   * 글 정보 수정
   * @param costipVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(CostipVO costipVO);


}
