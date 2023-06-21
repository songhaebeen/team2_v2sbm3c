package dev.mvc.cate;

import java.util.ArrayList;

public interface CateProcInter {
  /**
   * 등록
   * @param cateVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(CateVO cateVO); // 추상 메소드
 
  /**
   *  전체 목록
   * @return
   */
  public ArrayList<CateVO> list_all();
  
  /**
   * 조회, 읽기
   * @param cateno
   * @return
   */
  public CateVO read(int cateno);
  
  /**
   * 수정
   * @param cateVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(CateVO cateVO);
  
  /**
   * 삭제
   * @param cateno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int cateno);
  
  /**
   * 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_decrease(int cateno);
  
  /**
   * 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소
   * @param cateno
   * @return 변경된 레코드 수
   */
  public int update_seqno_increase(int cateno);

  /**
   * 공개
   * @param cateno
   * @return
   */
  public int update_visible_y(int cateno);
  
  /**
   * 비공개
   * @param cateno
   * @return
   */
  public int update_visible_n(int cateno);
  
  /**
   * visible y만 출력
   * @return
   */
  public ArrayList<CateVO> list_all_y();

  /**
   * 글수 증가 
   * @param cateno
   * @return
   */
  public int update_cnt_add(int cateno);
  
  /**
   * 글수 감소
   * @param cateno
   * @return
   */
  public int update_cnt_sub(int cateno);
  
  
}


