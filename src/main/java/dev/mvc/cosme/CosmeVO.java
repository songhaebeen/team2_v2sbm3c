package dev.mvc.cosme;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CosmeVO{
    /** 화장품 번호 */
    private int cosmeno;
    /** 브랜드 */
    private String brand = "";
    /** 화장품 이름 */
    private String cosmename = "";
    /** 등록일 */
    private String rdate;
    /** 관리자 번호 */
    private int adminno;
    /** 화장품 종류 번호 */
    private int cosme_cateno;
    /** 화장품 사진 파일 */
    private String cosme_file = "";
    /** 화장품 사진 저장 */
    private String cosme_file_saved = "";
    /** 화장품 사진 미리보기 */
    private String cosme_file_preview = "";
    /** 화장품 사진 크기 */
    private long cosme_file_size;
    /** 화장품 유튜브 영상 */
    private String cosme_youtube = "";
    
    public int getCosmeno() {
      return cosmeno;
    }

    public void setCosmeno(int cosmeno) {
      this.cosmeno = cosmeno;
    }

    public String getBrand() {
      return brand;
    }

    public void setBrand(String brand) {
      this.brand = brand;
    }

    public String getCosmename() {
      return cosmename;
    }

    public void setCosmename(String cosmename) {
      this.cosmename = cosmename;
    }

    public String getRdate() {
      return rdate;
    }

    public void setRdate(String rdate) {
      this.rdate = rdate;
    }

    public int getAdminno() {
      return adminno;
    }

    public void setAdminno(int adminno) {
      this.adminno = adminno;
    }

    public int getCosme_cateno() {
      return cosme_cateno;
    }

    public void setCosme_cateno(int cosme_cateno) {
      this.cosme_cateno = cosme_cateno;
    }

    public String getCosme_file() {
      return cosme_file;
    }

    public void setCosme_file(String cosme_file) {
      this.cosme_file = cosme_file;
    }

    public String getCosme_file_saved() {
      return cosme_file_saved;
    }

    public void setCosme_file_saved(String cosme_file_saved) {
      this.cosme_file_saved = cosme_file_saved;
    }

    public String getCosme_file_preview() {
      return cosme_file_preview;
    }

    public void setCosme_file_preview(String cosme_file_preview) {
      this.cosme_file_preview = cosme_file_preview;
    }

    public long getCosme_file_size() {
      return cosme_file_size;
    }

    public void setCosme_file_size(long cosme_file_size) {
      this.cosme_file_size = cosme_file_size;
    }

    public String getCosme_youtube() {
      return cosme_youtube;
    }

    public void setCosme_youtube(String cosme_youtube) {
      this.cosme_youtube = cosme_youtube;
    }

    public MultipartFile getFile1MF() {
      return file1MF;
    }

    public void setFile1MF(MultipartFile file1mf) {
      file1MF = file1mf;
    }

    public MultipartFile file1MF;
  

  
}
