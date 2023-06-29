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
  
  /* 
  자동으로 구현되는 목록
  1. Connection 연결, 해제
  2. PreparedStatement 객체 자동 생성
  3. #{name}등 컬럼의 값 자동 저장
  4. SQL 실행 자동
  5. 관련 예외처리 자동으로 처리됨.
  */
  

  
}









