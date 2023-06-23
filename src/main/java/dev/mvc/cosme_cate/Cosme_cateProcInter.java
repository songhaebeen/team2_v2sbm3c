package dev.mvc.cosme_cate;

import java.util.ArrayList;

public interface Cosme_cateProcInter {
  
  /**
   * 등록
   * @param cosme_cateVO
   * @return 
   */
  public int create(Cosme_cateVO cosme_cateVO); 
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<Cosme_cateVO>list_all();

  /**
   *종류별 리스트
   * @return
   */
  public ArrayList<Cosme_cateVO>list_by_cate(int cosme_cateno);
  
  /**
   * 조회
   * @param cosme_cateno
   * @return
   */
  public Cosme_cateVO read(int cosme_cateno);
  
  /**
   * 수정
   * @param Cosme_cateVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(Cosme_cateVO cosme_cateVO);
  
  /**
   * 삭제
   * @param cosme_cateno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int cosme_cateno);
  
  /**
   * 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가
   * @param cosme_cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_decrease(int cosme_cateno);
  
  /**
   * 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소
   * @param cosme_cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_increase(int cosme_cateno);
  
  /**
   * 글수 증가 
   * @param cosme_cateno
   * @return
   */
  public int update_cnt_add(int cosme_cateno);
  
  /**
   * 글수 감소
   * @param cosme_cateno
   * @return
   */
  public int update_cnt_sub(int cosme_cateno);
}
