package dev.mvc.good;

import java.util.ArrayList;
import java.util.List;

public interface GoodProcInter {
  /**
   * 좋아요 증가  
   * @param goodVO
   * @return 1: 증가, 0: 감소
   */
  public int create(GoodVO goodVO);
  
  /**
   * 목록
   * @param goodVO
   * @return
   */
  public List<GoodVO> list_all();
  
  /**
   * 회원 별 좋아요 목록
   * @param replyVO
   * @return
   */
  public List<GoodVO> list_memberno(int memberno);
  
  /**
   * 좋아요 감소  
   * @param goodno
   * @return 1: 증가, 0: 감소
   */
  public int delete(GoodVO goodVO);

  /**
   * 좋아요 체크  
   * @param goodVO
   * @return 1: 증가, 0: 감소
   */
  public int findGood(GoodVO goodVO);
	
}
