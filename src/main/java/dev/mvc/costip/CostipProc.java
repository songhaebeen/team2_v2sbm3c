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
  
  @Override
  public ArrayList <CostipVO> list_by_costipno() {
    ArrayList<CostipVO> list = this.costipDAO.list_by_costipno();
    
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
  
//조회 
@Override
public CostipVO read(int costipno) {
 CostipVO costipVO = this.costipDAO.read(costipno);
 return costipVO;
}

//유튜브
@Override
public int youtube(CostipVO costipVO) {
int cnt = this.costipDAO.youtube(costipVO);
return cnt;
}

@Override
public int password_check(CostipVO costipVO) {
  int cnt = this.costipDAO.password_check(costipVO);
  return cnt;
}

@Override
public int update_text(CostipVO costipVO) {
  int cnt = this.costipDAO.update_text(costipVO);
  return cnt;
}

}
