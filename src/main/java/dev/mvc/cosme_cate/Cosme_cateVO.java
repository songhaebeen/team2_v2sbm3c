package dev.mvc.cosme_cate;

//CREATE TABLE cosme_cate(
//cosme_cateno NUMBER(10) NOT NULL PRIMARY KEY,
//cosme_catename VARCHAR2(20) NOT NULL
//);

public class Cosme_cateVO {
  private int cosme_cateno;
  private String cosme_catename;
  private int cnt;
  private String rdate;
  private int seqno;
  
  
  public int getCosme_cateno() {
    return cosme_cateno;
  }
  public void setCosme_cateno(int cosme_cateno) {
    this.cosme_cateno = cosme_cateno;
  }
  public String getCosme_catename() {
    return cosme_catename;
  }
  public void setCosme_catename(String cosme_catename) {
    this.cosme_catename = cosme_catename;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  
  
  

  
}
