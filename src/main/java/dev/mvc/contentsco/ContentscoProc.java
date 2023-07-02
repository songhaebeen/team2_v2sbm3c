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

  // 검색된 글 목록
  @Override
  public ArrayList<ContentscoVO> list_by_catecono_search(ContentscoVO contentscoVO) {
    ArrayList <ContentscoVO> list = this.contentscoDAO.list_by_catecono_search(contentscoVO);
    return list;
  }
 // 검색된 글 갯수
  @Override
  public int search_count(ContentscoVO contentscoVO) {
    int cnt = this.contentscoDAO.search_count(contentscoVO);
    return cnt;
  }

  @Override
  public ArrayList<ContentscoVO> list_by_catecono_search_paging(ContentscoVO contentscoVO) {
    /*
    예) 페이지당 10개의 레코드 출력
    1 page: WHERE r >= 1 AND r <= 10
    2 page: WHERE r >= 11 AND r <= 20
    3 page: WHERE r >= 21 AND r <= 30
      
    페이지에서 출력할 시작 레코드 번호 계산 기준값, nowPage는 1부터 시작
    1 페이지 시작 rownum: now_page = 1, (1 - 1) * 10 --> 0 
    2 페이지 시작 rownum: now_page = 2, (2 - 1) * 10 --> 10
    3 페이지 시작 rownum: now_page = 3, (3 - 1) * 10 --> 20
    */
    int begin_of_page = (contentscoVO.getNow_page() - 1) * Contentsco.RECORD_PER_PAGE;
   
    // 시작 rownum 결정
    // 1 페이지 = 0 + 1: 1
    // 2 페이지 = 10 + 1: 11
    // 3 페이지 = 20 + 1: 21 
    int start_num = begin_of_page + 1;
    
    //  종료 rownum
    // 1 페이지 = 0 + 10: 10
    // 2 페이지 = 10 + 10: 20
    // 3 페이지 = 20 + 10: 30
    int end_num = begin_of_page + Contentsco.RECORD_PER_PAGE;   
    /*
    1 페이지: WHERE r >= 1 AND r <= 10
    2 페이지: WHERE r >= 11 AND r <= 20
    3 페이지: WHERE r >= 21 AND r <= 30
    */
    contentscoVO.setStart_num(start_num);
    contentscoVO.setEnd_num(end_num);
    
    ArrayList<ContentscoVO> list = this.contentscoDAO.list_by_catecono_search_paging(contentscoVO);
    
    for (ContentscoVO vo : list) { // 특수 문자 처리
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);
      content = Tool.convertChar(content);
      
      vo.setTitle(title);
      vo.setContent(content);
    }
    
    return list;
  }

//  <!-- 페이지 목록 출력 부분 시작 -->
//  <DIV class='bottom_menu'>
//  <style type='text/css'>  
//  #paging {text-align: center; margin-top: 5px; font-size: 1em;}  
//  #paging A:link {text-decoration:none; color:black; font-size: 1em;}  
//  #paging A:hover{text-decoration:none; background-color: #FFFFFF; color:black; font-size: 1em;}  
//  #paging A:visited {text-decoration:none;color:black; font-size: 1em;}  
//  .span_box_1{
//    text-align: center;
//  font-size: 1em;    
//  border: 1px;    
//  border-style: solid;    
//  border-color: #cccccc;    
//  padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/    
//  margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/  
//  }  
//  .span_box_2{    
//    text-align: center;    
//  background-color: #668db4;    
//  color: #FFFFFF;    
//  font-size: 1em;    
//  border: 1px;    
//  border-style: solid;    
//  border-color: #cccccc;    
//  padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/    
//  margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/  
//  }
//  </style>
//  
//  <DIV id='paging'>
//    <span class='span_box_1'><A href='list_by_cateno.do?word=&now_page=1&cateno=4'>1</A></span>
//  <span class='span_box_2'>2</span>
//  <span class='span_box_1'><A href='list_by_cateno.do?word=&now_page=3&cateno=4'>3</A></span>
//    </DIV>
//  </DIV> 
//  <!-- 페이지 목록 출력 부분 종료 -->
    
    /** 
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
     * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
     *
     * @param catecono 카테고리번호 
     * @param now_page  현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징 생성 문자열
     */ 
    @Override
    public String pagingBox(int catecono, int now_page, String word, String list_file){
      ContentscoVO contentscoVO = new ContentscoVO();
      contentscoVO.setCatecono(catecono);
      contentscoVO.setWord(word);
            
      int search_count = this.contentscoDAO.search_count(contentscoVO);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
      int total_page = (int)(Math.ceil((double)search_count / Contentsco.RECORD_PER_PAGE)); // 전체 페이지 수 
      int total_grp = (int)(Math.ceil((double)total_page / Contentsco.PAGE_PER_BLOCK)); // 전체 그룹  수
      int now_grp = (int)(Math.ceil((double)now_page / Contentsco.PAGE_PER_BLOCK));  // 현재 그룹 번호
      
      // 1 group: 1, 2, 3 ... 9, 10
      // 2 group: 11, 12 ... 19, 20
      // 3 group: 21, 22 ... 29, 30
      int start_page = ((now_grp - 1) * Contentsco.PAGE_PER_BLOCK) + 1; // 특정 그룹의 시작  페이지  
      int end_page = (now_grp * Contentsco.PAGE_PER_BLOCK);               // 특정 그룹의 마지막 페이지   
       
      StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름 
       
      str.append("<style type='text/css'>"); 
      str.append("  #paging {text-align: center; margin-top: 5px; font-size: 1em;}"); 
      str.append("  #paging A:link {text-decoration:none; color:black; font-size: 1em;}"); 
      str.append("  #paging A:hover{text-decoration:none; background-color: #FFFFFF; color:black; font-size: 1em;}"); 
      str.append("  #paging A:visited {text-decoration:none;color:black; font-size: 1em;}"); 
      str.append("  .span_box_1{"); 
      str.append("    text-align: center;");    
      str.append("    font-size: 1em;"); 
      str.append("    border: 1px;"); 
      str.append("    border-style: solid;"); 
      str.append("    border-color: #cccccc;"); 
      str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("  }"); 
      str.append("  .span_box_2{"); 
      str.append("    text-align: center;");    
      str.append("    background-color: #668db4;"); 
      str.append("    color: #FFFFFF;"); 
      str.append("    font-size: 1em;"); 
      str.append("    border: 1px;"); 
      str.append("    border-style: solid;"); 
      str.append("    border-color: #cccccc;"); 
      str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("  }"); 
      str.append("</style>"); 
      str.append("<DIV id='paging'>"); 
//      str.append("현재 페이지: " + nowPage + " / " + totalPage + "  "); 
   
      // 이전 10개 페이지로 이동
      // now_grp: 1 (1 ~ 10 page)
      // now_grp: 2 (11 ~ 20 page)
      // now_grp: 3 (21 ~ 30 page) 
      // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
      // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
      int _now_page = (now_grp - 1) * Contentsco.PAGE_PER_BLOCK;  
      if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이 이상임으로 이전 그룹으로 갈수 있는 링크 생성 
        str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"&catecono="+catecono+"'>이전</A></span>"); 
      } 
   
      // 중앙의 페이지 목록
      for(int i=start_page; i<=end_page; i++){ 
        if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
          break; 
        } 
    
        if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
          str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조 
        }else{
          // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
          str.append("<span class='span_box_1'><A href='"+list_file+"?word="+word+"&now_page="+i+"&catecono="+catecono+"'>"+i+"</A></span>");   
        } 
      } 
   
      // 10개 다음 페이지로 이동
      // nowGrp: 1 (1 ~ 10 page),  nowGrp: 2 (11 ~ 20 page),  nowGrp: 3 (21 ~ 30 page) 
      // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
      // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
      // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
      _now_page = (now_grp * Contentsco.PAGE_PER_BLOCK)+1; //  최대 페이지수 + 1 
      if (now_grp < total_grp){ 
        str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"&cateno="+catecono+"'>다음</A></span>"); 
      } 
      str.append("</DIV>"); 
       
      return str.toString(); 
    }
    
    // 패스워드 검사
    @Override
    public int password_check(ContentscoVO contentscoVO) {
      int cnt = this.contentscoDAO.password_check(contentscoVO);
      return cnt;
    }

    // 수정
    @Override
    public int update_text(ContentscoVO contentscoVO) {
      int cnt = this.contentscoDAO.update_text(contentscoVO);
      return cnt;
    }

    // 파일 수정
    @Override
    public int update_file(ContentscoVO contentscoVO) {
      int cnt = this.contentscoDAO.update_file(contentscoVO);
      return cnt;
    }

    // 삭제
    @Override
    public int delete(int contentscono) {
      int cnt = this.contentscoDAO.delete(contentscono);
      return cnt;
    }

    // 특정 카테고리에 속한 모든 레코드 삭제
    @Override
    public int count_by_catecono(int catecono) {
      int cnt = this.contentscoDAO.count_by_catecono(catecono);
      return cnt;
    }

    // 특정 카테고리에 속한 모든 레코드 삭제
    @Override
    public int delete_by_catecono(int catecono) {
      int cnt = this.contentscoDAO.delete_by_catecono(catecono);
      return cnt;
    }



}