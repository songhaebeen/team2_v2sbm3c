package dev.mvc.cosmetype;

import java.util.ArrayList;

public interface CosmetypeProcInter {

  /**
   * 등록
   * @param cosmetypeVO
   * @return
   */
  public int create(CosmetypeVO cosmetypeVO); 
  

  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CosmetypeVO>list_all();
  
  /**
   * 조회
   * @param cosmetypeno
   * @return
   */
  public CosmetypeVO read(int cosmetypeno);
  
  /**
   * 수정
   * @param CosmetypeVO
   * @return 수정된 레코드 갯수를 리턴
   */
  public int update(CosmetypeVO cosmetypeVO);
  
  /**
   * 삭제
   * @param cosmetypeno
   * @return 삭제된 레코드 갯수를 리턴
   */
  public int delete(int cosmetypeno);
}
