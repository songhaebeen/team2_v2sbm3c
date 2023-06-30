package dev.mvc.cosme_youtube_tip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.cosme.CosmeVO;

public interface Cosme_youtube_tipProcInter {
  /**
   * 등록
   * spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @param cosme_youtube_tipVO
   * @return
   */
  public int create(Cosme_youtube_tipVO cosme_youtube_tipVO);
  
  /**
   *  모든 화장품의 등록된 유튜브 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<Cosme_youtube_tipVO> list_youtube_all();
  
  /**
   *  특정 화장품에 등록된 유튜브 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<Cosme_youtube_tipVO> list_youtube_cosmeno();
  
  /**
   * 조회
   * @param youtubeno
   * @return
   */
  public Cosme_youtube_tipVO read(int youtubeno);
  
  /**
   *  특정 화장품의 검색된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<Cosme_youtube_tipVO> list_by_cosmeno_search(Cosme_youtube_tipVO cosme_youtube_tipVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param Cosme_youtube_tipVO
   * @return
   */
  public int search_count(Cosme_youtube_tipVO cosme_youtube_tipVO);
  
  /**
   *  특정 카테고리의 검색 + 페이징된 글목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<Cosme_youtube_tipVO> list_by_cosmeno_search_paging(Cosme_youtube_tipVO cosme_youtube_tipVO);
  
  /**
   * 글 정보 수정
   * @param cosme_youtube_tipVO
   * @return 변경된 레코드 갯수
   */
  public int update_tip(Cosme_youtube_tipVO cosme_youtube_tipVO);
  
  /**
   * 내용 삭제
   * @param youtubeno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int youtubeno);
  
  /**
   * 특정 화장품별 레코드 갯수
   * @param cosmeno
   * @return
   */
  public int count_by_cosmeno(int cosmeno);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cosmeno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_cosmeno(int cosmeno);
  
}
