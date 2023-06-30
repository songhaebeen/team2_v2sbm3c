package dev.mvc.contentsco;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.contentsco.contentscoProc")
public class ContentscoProc implements ContentscoProcInter {
  @Autowired
  private ContentscoDAOInter contentscoDAO;

  // 등록
  @Override
  public int create(ContentscoVO contentscoVO) {
    int cnt = this.contentscoDAO.create(contentscoVO);
    return cnt;
  }
  
}