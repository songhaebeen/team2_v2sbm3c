package dev.mvc.cateco;

import java.util.ArrayList;

public interface CatecoProcInter {
  /**
   * 등록
   * @param cateVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(CatecoVO catecoVO); // 추상 메소드
 
  /**
   * 목록
   * @return
   */
  public ArrayList<CatecoVO> list_all();
  
  /**
   * 조회
   * @param catecono
   * @return
   */
  public CatecoVO read(int catecono);
  
  /**
   * 수정
   * @param catecoVO
   * @return
   */
  public int update(CatecoVO catecoVO);
  
  /**
   * 삭제
   * @param catecono
   * @return
   */
  public int delete(int catecono);
  
  /**
   * 출력 순서 상향
   * @param catecono
   * @return
   */
  public int update_seqno_decrease(int catecono);
  
  /**
   * 출력 순서 하향
   * @param catecono
   * @return
   */
  public int update_seqno_increase(int catecono);
  
  /**
   * 글수 증가 
   * @param catecono
   * @return
   */
  public int update_cnt_add(int catecono);
  
  /**
   * 글수 감소
   * @param catecono
   * @return
   */
  public int update_cnt_sub(int catecono);
}


