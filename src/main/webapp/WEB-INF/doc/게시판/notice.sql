/**********************************/
/* Table Name: 공지사항 */
/**********************************/
DROP TABLE notice;

CREATE TABLE notice(
        noticeno            NUMBER(10)     NOT NULL         PRIMARY KEY,
        adminno            NUMBER(10)     NOT NULL , -- FK
        ntitle             VARCHAR2(50)    NOT NULL,
        ncontent           CLOB    NOT NULL,
        passwd              VARCHAR2(15)	 NOT NULL,
        word                VARCHAR2(100)         NULL , --검색
        views               NUMBER(7)         DEFAULT 0   NOT NULL,
        file1                VARCHAR(100)          NULL,  -- 원본 파일명 image
        file1saved            VARCHAR(100)          NULL,  -- 저장된 파일명, image
        thumb1                VARCHAR(100)          NULL,   -- preview image
        size1                 NUMBER(10)      DEFAULT 0 NULL,  -- 파일 사이즈
        youtube               VARCHAR2(1000)            NULL,
        rdate               DATE           NOT NULL,
        FOREIGN KEY (adminno) REFERENCES admin (adminno)
);

COMMENT ON TABLE notice is '공지사항';
COMMENT ON COLUMN notice.noticeno is '공지사항 번호';
COMMENT ON COLUMN notice.adminno is '관리자 번호';
COMMENT ON COLUMN notice.ntitle is '공지 제목';
COMMENT ON COLUMN notice.ncontent is '공지 내용';
COMMENT ON COLUMN notice.word is '검색어';
COMMENT ON COLUMN notice.views is '조회수';
COMMENT ON COLUMN notice.passwd is '패스워드';
COMMENT ON COLUMN notice.rdate is '등록일';
COMMENT ON COLUMN notice.file1 is '메인 이미지';
COMMENT ON COLUMN notice.file1saved is '실제 저장된 메인 이미지';
COMMENT ON COLUMN notice.thumb1 is '메인 이미지 Preview';
COMMENT ON COLUMN notice.size1 is '메인 이미지 크기';
COMMENT ON COLUMN notice.youtube is 'youtube';

DROP SEQUENCE notice_seq;

CREATE SEQUENCE notice_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO notice(noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1)
VALUES(notice_seq.nextval, 1, '필독', '규정 안내','1234', sysdate, 'cosme.jpg', 'cosme_1.jpg', 'cosme_t.jpg', 1000);

commit;     

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO notice(noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1)
VALUES(notice_seq.nextval, 1, '필독', '규정 안내','1234', sysdate, 'cosme.jpg', 'cosme_1.jpg', 'cosme_t.jpg', 1000);
            
INSERT INTO notice(noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1)
VALUES(notice_seq.nextval, 1, '공지', '규정 안내','1234', sysdate, 'cosme.jpg', 'cosme_1.jpg', 'cosme_t.jpg', 1000);
            
INSERT INTO notice(noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1)
VALUES(notice_seq.nextval, 1, '공지사항', '규정 안내','1234', sysdate, 'cosme.jpg', 'cosme_1.jpg', 'cosme_t.jpg', 1000);

-- 유형 1 전체 목록
SELECT noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1, views, youtube
FROM notice
ORDER BY noticeno ASC;
         
--masterno 1번인 관리자가 등록한 레코드이며, noticeno 1번인 여행에 속한 레코드임
  NOTICENO   adminno NTITLE                                             NCONTENT                                           PASSWD          RDATE              
---------- ---------- -------------------------------------------------- -------------------------------------------------- --------------- -------------------
         1          1 필독                                               규정 안내                                          1234            2023-06-07 11:48:54
         2          1 필독                                               규정 안내                                          1234            2023-06-07 11:48:57
         3          1 공지사항                                           회원 등급                                          1234            2023-06-07 11:49:02

-- title, ncontent, word column search
SELECT noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1, views, youtube
FROM notice
WHERE ntitle LIKE '%공지%' OR ncontent LIKE '%공지%' 
ORDER BY noticeno DESC;

--페이징
SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube, r
FROM (
           SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube, rownum as r
           FROM (
                     SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube
                     FROM notice
                     WHERE ntitle LIKE '%공지%' OR ncontent LIKE '%공지%' OR word LIKE '%공지%'
                     ORDER BY noticeno DESC
           )          
)
WHERE r >= 1 AND r <= 3;

SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube, r
FROM (
           SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube, rownum as r
           FROM (
                     SELECT noticeno, adminno, ntitle, ncontent, rdate, file1, file1saved, thumb1, size1, views, youtube
                     FROM notice
                     WHERE ntitle LIKE '%추천%' OR ncontent LIKE '%추천%' OR word LIKE '%추천%'
                     ORDER BY noticeno DESC
           )          
)
WHERE r >= 1 AND r <= 10;

--adminno가 admin 테이블에 등록이 안되어 있는 번호이면 레코드 삭제 후 다시 INSERT
DELETE FROM notice;
commit;

-- 모든 레코드 삭제
DELETE FROM notice;
commit;

-- 삭제
DELETE FROM notice
WHERE noticeno = 1;
commit;

DELETE FROM notice
WHERE noticeno=1 AND adminno = 1;

commit;

-- ----------------------------------------------------------------------------
-- 조회
-- ----------------------------------------------------------------------------
SELECT noticeno, adminno, ntitle, ncontent, passwd, rdate, file1, file1saved, thumb1, size1, views, youtube
FROM notice
WHERE noticeno = 32;

-- 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE notice
SET ntitle='필독!!', ncontent='~~'
WHERE noticeno = 1;

-- SUCCESS
UPDATE notice
SET ntitle='필독!!', ncontent='우 ''리'' 규칙'
WHERE noticeno = 1;

-- SUCCESS
UPDATE notice
SET ntitle='필독', ncontent='우 "리" 규칙'
WHERE noticeno = 1;

-- 삭제
DELETE FROM notice
WHERE noticeno = 2;

commit;

--조회수 증가
UPDATE notice 
SET views = views + 1 
WHERE noticeno = 1;

-- 특정 관리자에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM notice 
WHERE adminno=1 AND passwd='1234';

-- 특정 관리자에 속한 레코드 모두 삭제
DELETE FROM notice
WHERE adminno=1;

-- 다수의 관리자에 속한 레코드 모두 삭제: IN
SELECT noticeno, adminno, ntitle
FROM notice
WHERE adminno IN(1,2,3);
  NOTICENO   adminno NTITLE                                            
---------- ---------- --------------------------------------------------
         1          1 필독!!                                            
         2          1 공지사항                                          
         3          1 공지사항                                          
         4          1 필독                                                                                         
                                                                                                                                                                                                                    
SELECT noticeno, adminno, ntitle
FROM notice
WHERE adminno IN('1','2','3');
  NOTICENO   adminno NTITLE                                            
---------- ---------- --------------------------------------------------
         1          1 필독!!                                            
         2          1 공지사항                                          
         3          1 공지사항                                          
         4          1 필독                                              
                                           