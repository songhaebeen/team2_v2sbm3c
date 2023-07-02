package dev.mvc.cosme_youtube_tip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.cosme.CosmeVO;
import dev.mvc.tool.Tool;

@Component("dev.mvc.cosme_youtube_tip.Cosme_youtube_tipProc")
public class Cosme_youtube_tipProc implements Cosme_youtube_tipProcInter {
  @Autowired
  private Cosme_youtube_tipDAOInter cosme_youtube_tipDAO;
  
  @Override
  public int create(Cosme_youtube_tipVO cosme_youtube_tipVO) {
    int cnt = this.cosme_youtube_tipDAO.create(cosme_youtube_tipVO);
    
    return cnt;
  }

  @Override
  public ArrayList<Cosme_youtube_tipVO> list_youtube_all() {
    ArrayList <Cosme_youtube_tipVO> list = this.cosme_youtube_tipDAO.list_youtube_all();
    // for 문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (Cosme_youtube_tipVO cosme_youtube_tipVO : list) {
      String youtubetitle = cosme_youtube_tipVO.getYoutubetitle();
      String youtubecontent = cosme_youtube_tipVO.getYoutubecontent();
      
      youtubetitle = Tool.convertChar(youtubetitle);
      youtubecontent = Tool.convertChar(youtubecontent);
      
      cosme_youtube_tipVO.setYoutubetitle(youtubetitle);
      cosme_youtube_tipVO.setYoutubecontent(youtubecontent);
    }
    
    return list;
  }

  @Override
  public ArrayList<Cosme_youtube_tipVO> list_youtube_cosmeno() {
    ArrayList <Cosme_youtube_tipVO> list = this.cosme_youtube_tipDAO.list_youtube_cosmeno();
    // for 문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (Cosme_youtube_tipVO cosme_youtube_tipVO : list) {
      String youtubetitle = cosme_youtube_tipVO.getYoutubetitle();
      String youtubecontent = cosme_youtube_tipVO.getYoutubecontent();
      
      youtubetitle = Tool.convertChar(youtubetitle);
      youtubecontent = Tool.convertChar(youtubecontent);
      
      cosme_youtube_tipVO.setYoutubetitle(youtubetitle);
      cosme_youtube_tipVO.setYoutubecontent(youtubecontent);
    }
    
    return list;
  }

  @Override
  public Cosme_youtube_tipVO read(int youtubeno) {
    Cosme_youtube_tipVO cosme_youtube_tipVO = this.cosme_youtube_tipDAO.read(youtubeno);
    return cosme_youtube_tipVO;
  }


  @Override
  public ArrayList<Cosme_youtube_tipVO> list_by_cosmeno_search(Cosme_youtube_tipVO cosme_youtube_tipVO) {
    ArrayList<Cosme_youtube_tipVO> list = this.cosme_youtube_tipDAO.list_by_cosmeno_search(cosme_youtube_tipVO);
    return list;
  }

  @Override
  public int search_count(Cosme_youtube_tipVO cosme_youtube_tipVO) {
    int cnt = this.cosme_youtube_tipDAO.search_count(cosme_youtube_tipVO);
    return cnt;
  }

  @Override
  public ArrayList<Cosme_youtube_tipVO> list_by_cosmeno_search_paging(Cosme_youtube_tipVO cosme_youtube_tipVO) {
    /*
    예) 페이지당 10개의 레코드 출력
    1 page: WHERE r >= 1 AND r <= 10
    2 page: WHERE r >= 11 AND r <= 20
    3 page: WHERE r >= 21 AND r <= 30
      
    페이지에서 출력할 시작 레코드 번호 계산 기준값, nowPage는 1부터 시작
    1 페이지 기준값: now_page = 1, (1 - 1) * 10 --> 0 
    2 페이지 기준값: now_page = 2, (2 - 1) * 10 --> 10
    3 페이지 기준값: now_page = 3, (3 - 1) * 10 --> 20
    */
    int begin_of_page = (cosme_youtube_tipVO.getNow_page() - 1) * Cosme_youtube_tip.RECORD_PER_PAGE;
    
    // 시작 rownum 결정
    // 1 페이지 = 0 + 1: 1
    // 2 페이지 = 10 + 1: 11
    // 3 페이지 = 20 + 1: 21
    int start_num = begin_of_page + 1;
    
    //  종료 rownum
    // 1 페이지 = 0 + 10: 10
    // 2 페이지 = 10 + 10: 20
    // 3 페이지 = 20 + 10: 30
    int end_num = begin_of_page + Cosme_youtube_tip.RECORD_PER_PAGE;   
    /*
    1 페이지: WHERE r >= 1 AND r <= 10
    2 페이지: WHERE r >= 11 AND r <= 20
    3 페이지: WHERE r >= 21 AND r <= 30
    */
    cosme_youtube_tipVO.setStart_num(start_num);
    cosme_youtube_tipVO.setEnd_num(end_num);
    
    ArrayList<Cosme_youtube_tipVO> list = this.cosme_youtube_tipDAO.list_by_cosmeno_search_paging(cosme_youtube_tipVO);
    
    for (Cosme_youtube_tipVO va : list ) { // 특수 문자 처리.
      String youtubetitle = va.getYoutubetitle();
      String youtubecontent = va.getYoutubecontent();
      
      youtubetitle = Tool.convertChar(youtubetitle);
      youtubecontent = Tool.convertChar(youtubecontent);
      
      va.setYoutubetitle(youtubetitle);
      va.setYoutubecontent(youtubecontent);
    }
    
    return list;
  }

  @Override
  public int update_tip(Cosme_youtube_tipVO cosme_youtube_tipVO) {
    int cnt = this.cosme_youtube_tipDAO.update_tip(cosme_youtube_tipVO);
    return cnt;
  }

  @Override
  public int delete(int youtubeno) {
    int cnt = this.cosme_youtube_tipDAO.delete(youtubeno);
    return cnt;
  }

  @Override
  public int count_by_cosmeno(int cosmeno) {
    int cnt = this.cosme_youtube_tipDAO.count_by_cosmeno(cosmeno);
    return cnt;
  }

  @Override
  public int delete_by_cosmeno(int cosmeno) {
    int cnt = this.cosme_youtube_tipDAO.delete_by_cosmeno(cosmeno);
    return cnt;
  }

}
