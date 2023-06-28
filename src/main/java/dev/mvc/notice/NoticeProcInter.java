package dev.mvc.notice;

import java.util.ArrayList;

public interface NoticeProcInter {
	
  /**
   * 등록
   * spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @param noticeVO
   * @return
   */
	public int create(NoticeVO noticeVO);
	
  /**
   *  모든 공지사항 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NoticeVO> list_all();
  
  /**
   *  공지사항 조회
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public NoticeVO read(int noticeno);
  
  /**
   * Youtube
   * @param noticeVO
   * @return
   */
  public int youtube(NoticeVO noticeVO);
  
  /**
   *  공지사항에 검색된 글 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NoticeVO> list_by_search(NoticeVO noticeVO);
  
  /**
   * 검색된 레코드 갯수 리턴
   * @param fboardVO
   * @return
   */
  public int search_count(NoticeVO noticeVO);
  
  /**
   *  검색 + 페이징된 글 목록
   *  spring framework이 JDBC 관련 코드를 모두 생성해줌
   * @return
   */
  public ArrayList<NoticeVO> list_by_search_paging(NoticeVO noticeVO);
  
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
  public int password_check(NoticeVO noticeVO);
  
  /**
   * 글 정보 수정
   * @param noticeVO
   * @return 변경된 레코드 갯수
   */
  public int update(NoticeVO noticeVO);
  
  /**
   * 파일 정보 수정
   * @param noticeVO
   * @return 변경된 레코드 갯수
   */
  public int update_file(NoticeVO noticeVO);
  
  /**
   * 삭제
   * @param noticeno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int noticeno);
  
  /**
   * 조회수
   * @param noticeno
   * @return 처리된 레코드 갯수
   */
  public int views(int noticeno);

}
