package dev.mvc.cateco;

//CREATE TABLE cate(
//    cateno                            NUMBER(10)     NOT NULL    PRIMARY KEY,
//    name                              VARCHAR2(30)     NOT NULL,
//    cnt                               NUMBER(7)    DEFAULT 0     NOT NULL,
//    rdate                             DATE     NOT NULL
//);
public class CatecoVO {
  private int catecono;
  private String name;
  private int cnt;
  private String rdate;
  private int seqno;
  private String visible;
  

  public int getCatecono() {
    return catecono;
  }
  public void setCatecono(int catecono) {
    this.catecono = catecono;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
    
  
}


