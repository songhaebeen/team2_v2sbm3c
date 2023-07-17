package dev.mvc.costip;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.costip.CostipProc")
public class CostipProc  implements CostipProcInter {
  @Autowired
  private CostipDAOInter costipDAO;
  
  @Override
  public int create(CostipVO costipVO) {
    int cnt = this.costipDAO.create(costipVO);
    return cnt;
  }
  
  @Override
  public ArrayList <CostipVO> list_all() {
    ArrayList<CostipVO> list = this.costipDAO.list_all();
    
    for (CostipVO costipVO : list) {
      String title = costipVO.getTitle();
      String content = costipVO.getContent();
      
      title = Tool.convertChar(title);
      content = Tool.convertChar(content);
      
      costipVO.setTitle(title);
      costipVO.setContent(content);
    }
    
    return list;
  }

}
