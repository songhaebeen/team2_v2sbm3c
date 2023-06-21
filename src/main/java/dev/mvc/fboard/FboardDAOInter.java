package dev.mvc.fboard;

import java.util.ArrayList;

public interface FboardDAOInter {
  /**
   * 등록
   * spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @param fboardVO
   * @return
   */
	public int create(FboardVO fboardVO);
	
  /**
   *  자유게시판에 등록된 모든 글 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
	public ArrayList<FboardVO> list_all();
	
  /**
   *  자유게시판 조회
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
	public FboardVO read(int fboardno);
	
  /**
   * Youtube
   * @param fboardVO
   * @return
   */
  public int youtube(FboardVO fboardVO);
  
  /**
   *  특정 카테고리의 검색된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<FboardVO> list_by_search(FboardVO fboardVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param fboardVO
   * @return
   */
  public int search_count(FboardVO fboardVO);
  
  /**
   *  검색 + 페이징된 글 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<FboardVO> list_by_search_paging(FboardVO fboardVO);

}
