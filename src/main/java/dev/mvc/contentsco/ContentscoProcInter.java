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
}