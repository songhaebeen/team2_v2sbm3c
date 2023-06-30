package dev.mvc.cosme_youtube_tip;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Cosme_youtube_tipVO {
  /** 유튜브번호 */
  private int youtubeno;
  /** 화장품 번호 */
  private int cosmeno;
  /** 검색어 */
  private String word = "";
  /** 제목 */
  private String youtubetitle ;
  /** 내용 */
  private String youtubecontent ;
  /** 조회수 */
  private int views;
  /** 등록일 */
  private String rdate;
  /** 유튜브 주소 */
  private String address = "";
  /** 출력순서 */
  private int seqno;
  /** 출력 모드 */
  private String visible = "";
  /** 시작 rownum */
  private int start_num;
  
  /** 종료 rownum */
  private int end_num;    
  
  /** 현재 페이지 */
  private int now_page = 1;


}
