package dev.mvc.notice;

import java.util.ArrayList;

public interface NoticeDAOInter {
	
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
