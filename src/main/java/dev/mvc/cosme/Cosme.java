package dev.mvc.cosme;

import java.io.File;

public class Cosme{
  
    /** 페이지당 출력할 레코드 갯수 */
    public static int RECORD_PER_PAGE = 3;

    /** 블럭당 페이지 수, 하나의 블럭은 1개 이상의 페이지로 구성됨 */
    public static int PAGE_PER_BLOCK = 10;


    // Windows, VMWare, AWS cloud 절대 경로 설정
    public static synchronized String getUploadDir() {
        String path = "";
        if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
            path="C:\\kd\\deploy\\team2_v2sbm3c\\cosme\\storage\\";
            // System.out.println("Windows 10: " + path);
            
        } else { // Linux, AWS, 서비스용 배치 폴더 
            // System.out.println("Linux");
            path = "/home/ubuntu/deploy/team_v2sbm3c/cosme/storage/";
        }
        
        return path;
    }

}
