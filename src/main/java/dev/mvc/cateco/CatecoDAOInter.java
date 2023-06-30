package dev.mvc.cateco;

import java.util.ArrayList;

// <mapper namespace="dev.mvc.cate.CateDAOInter">
public interface CatecoDAOInter {
  // <insert id="create" parameterType="dev.mvc.cate.CateVO">
  // 개발자는 create 메소드 구현을 SpringFramework에서 미룸, 위임.
  
  /**
   * 등록
   * @param cateVO
   * @return insert 태그가 추가한 레코드 갯수를 리턴
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









