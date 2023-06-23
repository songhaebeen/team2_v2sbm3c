package dev.mvc.notice;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	/**
	 * 삭제
	 */
	@Override
	public int delete(int noticeno) {
		int cnt = this.noticeDAO.delete(noticeno);
		return cnt;
	}

}
