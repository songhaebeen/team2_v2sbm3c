package dev.mvc.notice;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.fboard.Fboard;
import dev.mvc.fboard.FboardVO;
import dev.mvc.tool.Tool;

@Component("dev.mvc.notice.NoticeProc")
public class NoticeProc implements NoticeProcInter{
	@Autowired
	private NoticeDAOInter noticeDAO;
	
  /**
   * 등록
   */
	@Override
	public int create(NoticeVO noticeVO) {
		int cnt = this.noticeDAO.create(noticeVO);
		
		return cnt;
	}

  /**
   * 목록
   */
	@Override
	public ArrayList<NoticeVO> list_all() {
		ArrayList<NoticeVO> list = this.noticeDAO.list_all();
		
		for (NoticeVO noticeVO : list) {
		      String ntitle = noticeVO.getNtitle();
		      String ncontent = noticeVO.getNcontent();
		      
		      ntitle = Tool.convertChar(ntitle);  // 특수 문자 처리
		      ncontent = Tool.convertChar(ncontent); 
		      
		      noticeVO.setNtitle(ntitle);
		      noticeVO.setNcontent(ncontent);  

		    }
		
		return list;
	}

  /**
   * 조회
   */
	@Override
	public NoticeVO read(int noticeno) {
		NoticeVO noticeVO = this.noticeDAO.read(noticeno);
		return noticeVO;
	}
	
  /**
   * 유튜브
   */
  @Override
  public int youtube(NoticeVO noticeVO) {
    int cnt = this.noticeDAO.youtube(noticeVO);
    return cnt;
  }
  
  @Override
  public ArrayList<NoticeVO> list_by_search(NoticeVO noticeVO) {
	  ArrayList<NoticeVO> list = this.noticeDAO.list_by_search(noticeVO);
  	return list;
  }

  @Override
  public int search_count(NoticeVO noticeVO) {
  	int cnt = this.noticeDAO.search_count(noticeVO);
  	return cnt;
  }

  @Override
  public ArrayList<NoticeVO> list_by_search_paging(NoticeVO noticeVO) {
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
	    int begin_of_page = (noticeVO.getNow_page() - 1) * Notice.RECORD_PER_PAGE;
	   
	    // 시작 rownum 결정
	    // 1 페이지 = 0 + 1: 1
	    // 2 페이지 = 10 + 1: 11
	    // 3 페이지 = 20 + 1: 21 
	    int start_num = begin_of_page + 1;
	    
	    //  종료 rownum
	    // 1 페이지 = 0 + 10: 10
	    // 2 페이지 = 10 + 10: 20
	    // 3 페이지 = 20 + 10: 30
	    int end_num = begin_of_page + Notice.RECORD_PER_PAGE;   
	    /*
	    1 페이지: WHERE r >= 1 AND r <= 10
	    2 페이지: WHERE r >= 11 AND r <= 20
	    3 페이지: WHERE r >= 21 AND r <= 30
	    */
	    noticeVO.setStart_num(start_num);
	    noticeVO.setEnd_num(end_num);
	    
	    ArrayList<NoticeVO> list = this.noticeDAO.list_by_search_paging(noticeVO);
	    
	    for (NoticeVO vo : list) { // 특수 문자 처리
	      String title = vo.getNtitle();
	      String content = vo.getNcontent();
	      
	      title = Tool.convertChar(title);
	      content = Tool.convertChar(content);
	      
	      vo.setNtitle(title);
	      vo.setNcontent(content);
	    }
	    
	    return list;
	  }

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @return 페이징 생성 문자열
   */ 
	@Override
	public String pagingBox(int now_page, String word, String list_file) {
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setWord(word);
		
		int search_count = this.noticeDAO.search_count(noticeVO);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
		//System.out.println("-> search_count: " + search_count);
		int total_page = (int) (Math.ceil((double) search_count / Notice.RECORD_PER_PAGE)); // 전체 페이지 수
		int total_grp = (int) (Math.ceil((double) total_page / Notice.PAGE_PER_BLOCK)); // 전체 그룹 수
		int now_grp = (int) (Math.ceil((double) now_page / Notice.PAGE_PER_BLOCK)); // 현재 그룹 번호

		// 1 group: 1, 2, 3 ... 9, 10
		// 2 group: 11, 12 ... 19, 20
		// 3 group: 21, 22 ... 29, 30
		int start_page = ((now_grp - 1) * Notice.PAGE_PER_BLOCK) + 1; // 특정 그룹의 시작 페이지
		int end_page = (now_grp * Notice.PAGE_PER_BLOCK); // 특정 그룹의 마지막 페이지

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
//	      str.append("현재 페이지: " + nowPage + " / " + totalPage + "  "); 

		// 이전 10개 페이지로 이동
		// now_grp: 1 (1 ~ 10 page)
		// now_grp: 2 (11 ~ 20 page)
		// now_grp: 3 (21 ~ 30 page)
		// 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
		// 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
		int _now_page = (now_grp - 1) * Fboard.PAGE_PER_BLOCK;
		if (now_grp >= 2) { // 현재 그룹번호가 2 이상이면 페이지 수가 11페이지 이상임으로 이전 그룹으로 갈 수 있는 링크 생성
			str.append("<span class='span_box_1'><A href='" + list_file + "?&word=" + word + "&now_page=" + _now_page
					+ "'>이전</A></span>");
		}

		//System.out.println("-> start_page: " + start_page);
		//System.out.println("-> end_page: " + end_page);
		//System.out.println("-> now_page: " + now_page);
		//System.out.println("-> total_page: " + total_page);

		// 중앙의 페이지 목록
		for (int i = start_page; i <= end_page; i++) {
			//System.out.println("-> for문 순환 i: " + i);
			if (i > total_page) { // 마지막 페이지를 넘어갔다면 페이지 출력 종료
				break;
			}

			if (now_page == i) { // 목록에 출력하는 페이지가 현재 페이지와 같다면 CSS 강조(차별을 둠)
				//System.out.println("-> 현재 페이지 발견됨: " + i);
				str.append("<span class='span_box_2'>" + i + "</span>"); // 현재 페이지, 강조
			} else {
				// 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
				str.append("<span class='span_box_1'><A href='" + list_file + "?word=" + word + "&now_page=" + i + "'>" + i + "</A></span>");
			}
		}

		// 10개 다음 페이지로 이동
		// nowGrp: 1 (1 ~ 10 page), nowGrp: 2 (11 ~ 20 page), nowGrp: 3 (21 ~ 30 page)
		// 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
		// 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
		// 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
		_now_page = (now_grp * Fboard.PAGE_PER_BLOCK) + 1; // 최대 페이지수 + 1
		if (now_grp < total_grp) {
			str.append("<span class='span_box_1'><A href='" + list_file + "?&word=" + word + "&now_page=" + _now_page
					+ "'>다음</A></span>");
		}
		str.append("</DIV>");

		return str.toString();
  }

  /**
   *  패스워드 일치 검사
   * http://localhost:9093/notice/password_check.do?noticeno=1&passwd=1234
   * @return
   */
	@Override
	public int password_check(NoticeVO noticeVO) {
		int cnt = this.noticeDAO.password_check(noticeVO);
		return cnt;
		}

	/**
	 * 글 수정
	 */
	@Override
	public int update(NoticeVO noticeVO) {
		int cnt = this.noticeDAO.update(noticeVO);
		return cnt;
		}
	
  //파일 수정
  @Override
  public int update_file(NoticeVO noticeVO) {
    int cnt = this.noticeDAO.update_file(noticeVO);
    return cnt;
  }

	/**
	 * 삭제
	 */
	@Override
	public int delete(int noticeno) {
		int cnt = this.noticeDAO.delete(noticeno);
		return cnt;
	}

   //조회수
  @Override
  public int views(int noticeno) {
    int cnt = this.noticeDAO.views(noticeno);
    return cnt;
  }




}
