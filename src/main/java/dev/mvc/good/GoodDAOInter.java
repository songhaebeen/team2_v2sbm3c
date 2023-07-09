package dev.mvc.good;

public interface GoodDAOInter {	
  /**
   * 좋아요 체크  
   * @param goodVO
   * @return 1: 증가, 0: 감소
   */
  public int findGood(GoodVO goodVO);
  
  /**
   * 좋아요 증가  
   * @param goodVO
   * @return 1: 증가, 0: 감소
   */
  public int up(int goodno);
  
  /**
   * 좋아요 감소  
   * @param goodVO
   * @return 1: 증가, 0: 감소
   */
  public int down(int goodno);

}
