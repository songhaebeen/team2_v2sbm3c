package dev.mvc.contentsco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.cateco.CatecoVO;

public interface ContentscoDAOInter {
  
  /**
   * 등록
   * @param contentscoVO
   * @return
   */
  public int create(ContentscoVO contentscoVO);

  /**
   * 컨텐츠 글 목록
   * @return
   */
  public ArrayList <ContentscoVO> list_all();
  
  /**
   * 특정 카테고리 글 목록
   * @param catecono
   * @return
   */
  public ArrayList <ContentscoVO> list_by_catecono(int catecono);
  
  /**
   * 조회
   * @param contentscono
   * @return
   */
  public ContentscoVO read(int contentscono);
  
  /**
   * 유튜브
   * @param contentscoVO
   * @return
   */
  public int youtube(ContentscoVO contentscoVO);
  
  /**
   * 검색된 글 목록
   * @param contentscoVO
   * @return
   */
  public ArrayList<ContentscoVO>list_by_catecono_search(ContentscoVO contentscoVO);
  
  /**
   * 검색된 레코드 갯수
   * @param contentscoVO
   * @return
   */
  public int search_count(ContentscoVO contentscoVO);
  
  /**
   * 카테고리 검색 + 페이징
   * @param contentscoVO
   * @return
   */
  public ArrayList <ContentscoVO> list_by_catecono_search_paging(ContentscoVO contentscoVO);
  
  /**
   * 패스워드 검사  
   * @param contentscoVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(ContentscoVO contentscoVO);
  
  /**
   * 글 수정
   * @param contentscoVO
   * @return
   */
  public int update_text(ContentscoVO contentscoVO);
  
  /**
   * 파일 수정
   * @param contentscoVO
   * @return
   */
  public int update_file(ContentscoVO contentscoVO);
  
  /**
   * 삭제
   * @param contentscono
   * @return
   */
  public int delete(int contentscono);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param catecono
   * @return
   */
  public int count_by_catecono(int catecono);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param catecono
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_catecono(int catecono);
}