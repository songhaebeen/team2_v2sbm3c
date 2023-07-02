package dev.mvc.contentsco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ContentscoProcInter {
  
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
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param catecono          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명 
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int catenoco, int now_page, String word, String list_file);
  
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