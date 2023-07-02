package dev.mvc.contentsco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.contentsco.ContentscoProc")
public class ContentscoProc implements ContentscoProcInter {
  @Autowired
  private ContentscoDAOInter contentscoDAO;

  // 등록
  @Override
  public int create(ContentscoVO contentscoVO) {
    int cnt = this.contentscoDAO.create(contentscoVO);
    return cnt;
  }

  // 컨텐츠 글 목록
  @Override
  public ArrayList<ContentscoVO> list_all() {
    ArrayList<ContentscoVO> list = this.contentscoDAO.list_all();
    
    for (ContentscoVO contentscoVO : list) {
      String title = contentscoVO.getTitle();
      String content = contentscoVO.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      contentscoVO.setTitle(title);
      contentscoVO.setContent(content);  

    }
    
    return list;
  }

  // 특정 카테고리 글 목록
  @Override
  public ArrayList<ContentscoVO> list_by_catecono(int catecono) {
    ArrayList<ContentscoVO> list = this.contentscoDAO.list_by_catecono(catecono);
    
    for (ContentscoVO contentscoVO : list) {
      String title = contentscoVO.getTitle();
      String content = contentscoVO.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      contentscoVO.setTitle(title);
      contentscoVO.setContent(content);  

    }
    
    return list;
  }

  // 조회 
  @Override
  public ContentscoVO read(int contentscono) {
    ContentscoVO contentscoVO = this.contentscoDAO.read(contentscono);
    return contentscoVO;
  }

  // 유튜브
  @Override
  public int youtube(ContentscoVO contentscoVO) {
    int cnt = this.contentscoDAO.youtube(contentscoVO);
    return cnt;
  }

}