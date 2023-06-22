package dev.mvc.cosme_cate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;


  @Component("dev.mvc.cosme_cate.Cosme_cateProc") // Controller가 사용하는 이름
  public class Cosme_cateProc implements Cosme_cateProcInter {
    @Autowired
    private Cosme_cateDAOInter cosme_cateDAO; 
    
    // 등록
    @Override
    public int create(Cosme_cateVO cosme_cateVO) {
      int cnt = this.cosme_cateDAO.create(cosme_cateVO); 
      return cnt;
    }

    // 목록
    @Override
    public ArrayList<Cosme_cateVO> list_all() {
      ArrayList<Cosme_cateVO> list = this.cosme_cateDAO.list_all();
      
      return list;
    }

    // 종류별 리스트
    @Override
    public ArrayList<Cosme_cateVO> list_by_cate(int cosme_cateno) {
      ArrayList<Cosme_cateVO> list = this.cosme_cateDAO.list_by_cate(cosme_cateno);
      
      for(Cosme_cateVO cosme_cateVO : list) {
        String cosme_catename = cosme_cateVO.getCosme_catename();
        
        cosme_catename = Tool.convertChar(cosme_catename);
        
        cosme_cateVO.setCosme_catename(cosme_catename);
      }
      
      return list;
    }
    // 조회
    @Override
    public Cosme_cateVO read(int cosme_cateno) {
      Cosme_cateVO cosme_cateVO = this.cosme_cateDAO.read(cosme_cateno);
      return cosme_cateVO;
    }

    // 수정
    @Override
    public int update(Cosme_cateVO cosme_cateVO) {
      int cnt = this.cosme_cateDAO.update(cosme_cateVO);
      return cnt;
    }

    // 삭제
    @Override
    public int delete(int cosme_cateno) {
      int cnt = this.cosme_cateDAO.delete(cosme_cateno);
      return cnt;
    }
  }

    
  


