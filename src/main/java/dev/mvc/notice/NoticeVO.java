package dev.mvc.notice;

public class NoticeVO {
	private int noticeno;
	private int adminno;
	private String ntitle="";
	private String ncontent="";
	private String passwd = "";
	private String rdate="";
	
	public int getNoticeno() {
		return noticeno;
	}
	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}
	public int getAdminno() {
		return adminno;
	}
	public void setAdminno(int adminno) {
		this.adminno = adminno;
	}
	public String getNtitle() {
		return ntitle;
	}
	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}
	public String getNcontent() {
		return ncontent;
	}
	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	

}
