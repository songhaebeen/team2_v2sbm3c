package dev.mvc.reply;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Tool;

@Component("dev.mvc.reply.ReplyProc")
public class ReplyProc implements ReplyProcInter {
  @Autowired
  private ReplyDAOInter replyDAO; 
  
  @Override
  public int create(ReplyVO replyVO) {
    int cnt = replyDAO.create(replyVO);
    return cnt;
  }

  @Override
  public List<ReplyVO> list() {
    List<ReplyVO> list = replyDAO.list();
    return list;
  }

  @Override
  public List<ReplyVO> list_by_fboardno(int fboardno) {
    List<ReplyVO> list = replyDAO.list_by_fboardno(fboardno);
    String content = "";
    
    // 특수 문자 변경
    for (ReplyVO replyVO:list) {
      content = replyVO.getContent();
      content = Tool.convertChar(content);
      replyVO.setContent(content);
    }
    return list;
  }

  @Override
  public List<ReplyMemberVO> list_by_fboardno_join(int fboardno) {
    List<ReplyMemberVO> list = replyDAO.list_by_fboardno_join(fboardno);
    String content = "";
    
    // 특수 문자 변경
    for (ReplyMemberVO replyMemberVO:list) {
      content = replyMemberVO.getContent();
      content = Tool.convertChar(content);
      replyMemberVO.setContent(content);
    }
    return list;
  }

  @Override
  public int checkPasswd(Map<String, Object> map) {
    int cnt = replyDAO.checkPasswd(map);
    return cnt;
  }

  @Override
  public int delete(int replyno) {
    int cnt = replyDAO.delete(replyno);
    return cnt;
  }

@Override
public List<ReplyMemberVO> list_member_join() {
	
	List<ReplyMemberVO> list = replyDAO.list_member_join();
	// 특수 문자 변경
    for (ReplyMemberVO replyMemberVO:list) {
      String content = replyMemberVO.getContent();
      content = Tool.convertChar(content);
      replyMemberVO.setContent(content);
    }
    
    return list;
	}

//목록
@Override
public List<ReplyMemberVO> list_by_fboardno_join_add(int fboardno) {
  List<ReplyMemberVO> list = replyDAO.list_by_fboardno_join_add(fboardno);
  String content = "";
  
  // 특수 문자 변경
  for (ReplyMemberVO replyMemberVO:list) {
    content = replyMemberVO.getContent();
    content = Tool.convertChar(content);
    replyMemberVO.setContent(content);
  }
  return list;
}

//10건만 출력
@Override
public List<ReplyMemberVO> list_ten(int fboardno) {
  List<ReplyMemberVO> list = replyDAO.list_ten(fboardno);
  String content = "";
  
  // 특수 문자 변경
  for (ReplyMemberVO replyMemberVO:list) {
    content = replyMemberVO.getContent();
    content = Tool.convertChar(content);
    replyMemberVO.setContent(content);
  }
  return list;
}

@Override
public int update(ReplyMemberVO replyMemberVO) {
  int cnt = this.replyDAO.update(replyMemberVO);
  return cnt;
}


   
}