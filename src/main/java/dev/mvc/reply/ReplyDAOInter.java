package dev.mvc.reply;

import java.util.List;
import java.util.Map;

public interface ReplyDAOInter {
  /**
   * 등록
   * @param 
   * @return
   */
  public int create(ReplyVO replyVO);
  
  /**
   * 등록
   * @param replyVO
   * @return
   */
  public List<ReplyVO> list();
  
  /**
   * 목록
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
   * @param replyVO
   * @return
   */
  public List<ReplyMemberVO> list_by_fboardno_join(int fboardno);
  
  /**
   * 패스워드 확인
   * @param replyVO
   * @return
   */
  public int checkPasswd(Map<String, Object> map);

  /**
   * 삭제
   * @param replyVO
   * @return
   */
  public int delete(int replyno);
  
  /**
   * 특정 글 관련 전체 댓글 목록
   * @param fboardno
   * @return
   */
  public List<ReplyMemberVO> list_by_fboardno_join_add(int fboardno);

}
