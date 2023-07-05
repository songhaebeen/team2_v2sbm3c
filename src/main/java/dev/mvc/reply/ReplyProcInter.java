package dev.mvc.reply;

import java.util.List;
import java.util.Map;

public interface ReplyProcInter {
  /**
   * 등록
   * @param 
   * @return
   */
  public int create(ReplyVO replyVO);
  
  /**
   * 목록
   * @param replyVO
   * @return
   */
  public List<ReplyVO> list();
  
  /**
   * 자유게시판 목록
   * @param replyVO
   * @return
   */
  public List<ReplyVO> list_by_fboardno(int fboardno);
  
  /**
   * 회원 ID 댓글 목록
   * @param replyVO
   * @return
   */
  public List<ReplyMemberVO> list_member_join();
  
  /**
   * 댓글
   * @param replyMemberVO
   * @return
   */
  public List<ReplyMemberVO> list_by_fboardno_join(int fboardno);
  
  /**
   * 패스워드 확인
   * @param replyMemberVO
   * @return
   */
  public int checkPasswd(Map<String, Object> map);
  
  public int password_check(ReplyMemberVO replyMemberVO);
  
  /**
   * 특정 글 관련 전체 댓글 목록
   * @param fboardno
   * @return
   */
  public List<ReplyMemberVO> list_by_fboardno_join_add(int fboardno);
  
  /**
   * 최신 댓글 10건만 출력
   * @param fboardno
   * @return
   */
  public List<ReplyMemberVO> list_ten(int fboardno);
  
  /**
   * 회원별 댓글 목록
   * @param replyMemberVO
   * @return
   */
  public List<ReplyMemberVO> list_memberno(int memberno);

  /**
   * 조회
   * @param replyMemberVO
   * @return
   */
  public ReplyMemberVO read(int replyno);
  
  /**
   * 수정
   * @param replyMemberVO
   * @return 처리된 레코드 갯수
   */
  public int update(ReplyMemberVO replyMemberVO);

  /**
   * 삭제
   * @param replyVO
   * @return
   */
  public int delete(int replyno);
  
  /**
   * 댓글수 확인
   * @param 
   * @return
   */
  public int count(int fboardno);

  
}