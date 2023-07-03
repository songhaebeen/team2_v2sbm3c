package dev.mvc.reply;

public class MReplyVO {
  /** 댓글 번호 */
  private int replyno;
  /** 관련 글 번호 */
  private int fboardno;
  /** 관련 글 제목 */
  private String ftitle = "";
  /** 회원 번호 */
  private int memberno;
  /** 내용 */
  private String content;
  /** 등록일 */
  private String rdate;
  
  public int getReplyno() {
    return replyno;
  }
  public void setReplyno(int replyno) {
    this.replyno = replyno;
  }
  public int getFboardno() {
    return fboardno;
  }
  public void setFboardno(int fboardno) {
    this.fboardno = fboardno;
  }
  public String getFtitle() {
    return ftitle;
  }
  public void setFtitle(String ftitle) {
    this.ftitle = ftitle;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  
  

}
