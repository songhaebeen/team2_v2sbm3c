package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.cate.CateVO;

public interface ContentsDAOInter {
  /**
   * 등록
   * spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @param contentsVO
   * @return
   */
  public int create(ContentsVO contentsVO);

  /**
   *  모든 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<ContentsVO> list_all();

  /**
   *  특정 카테고리의 등록된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<ContentsVO> list_by_cateno(int cateno);
  
  /**
   * 조회
   * @param contentsno
   * @return
   */
  public ContentsVO read(int contentsno);
  
  /**
   * Map
   * @param contentsVO
   * @return
   */
  public int map(ContentsVO contentsVO);

  /**
   * Youtube
   * @param contentsVO
   * @return
   */
  public int youtube(ContentsVO contentsVO);

  /**
   *  특정 카테고리의 검색된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<ContentsVO> list_by_cateno_search(ContentsVO contentsVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param contentsVO
   * @return
   */
  public int search_count(ContentsVO contentsVO);

  /**
   *  특정 카테고리의 검색 + 페이징된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<ContentsVO> list_by_cateno_search_paging(ContentsVO contentsVO);
  
  /**
   * 글 정보 수정
   * @param contentsVO
   * @return 변경된 레코드 갯수
   */
  public int update_text(ContentsVO contentsVO);
 
  /**
   * 패스워드 검사  
   * @param contentsVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(ContentsVO contentsVO);
  
  /**
   * 파일 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(ContentsVO contentsVO);
  
  /**
   * 삭제
   * @param contentsno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int contentsno);
  
  /**
   * 카테고리별 레코드 갯수
   * @param cateno
   * @return
   */
  public int count_by_cateno(int cateno);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cateno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_cateno(int cateno);
  
}





