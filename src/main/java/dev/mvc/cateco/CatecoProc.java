package dev.mvc.cateco;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.cateco.CatecoProc") // Controller가 사용하는 이름
public class CatecoProc implements CatecoProcInter {
  @Autowired
  private CatecoDAOInter catecoDAO; // Spring이 CateDAOInter.java를 구현하여 객체를 자동 생성후 할당
  
  @Override
  public int create(CatecoVO catecoVO) {
    int cnt = this.catecoDAO.create(catecoVO); // Spring이 자동으로 구현한 메소드를 호출
    return cnt;
  }


  
}





