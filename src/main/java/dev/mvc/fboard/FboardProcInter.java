package dev.mvc.fboard;

import java.util.ArrayList;

public interface FboardProcInter {
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
   *  자유게시판에 검색된 글목록
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
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명 
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file);
  
  /**
   * 패스워드 검사  
   * @param contentsVO
   * @return 1: 패스워드 일치, 0: 패스워드 불일치
   */
  public int password_check(FboardVO fboardVO);
  
  /**
   * 수정
   * @param fboardVO
   * @return 처리된 레코드 갯수
   */
  public int update(FboardVO fboardVO);
  
  /**
   * 글 정보 수정
   * @param fboardVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(FboardVO fboardVO);
  
  /**
   * 파일 정보 수정
   * @param fboardVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(FboardVO fboardVO);
  
  /**
   * 삭제
   * @param fboardno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int fboardno);
  
  /**
   * 조회수
   * @param fboardno
   * @return 처리된 레코드 갯수
   */
  public int views(int fboardno);
  
  /**
   * 댓글 수 증가
   * @param 
   * @return
   */ 
  public int increaseReplycnt(int fboardno);
 
  /**
   * 댓글 수 감소
   * @param 
   * @return
   */   
  public int decreaseReplycnt(int fboardno);
	

}
