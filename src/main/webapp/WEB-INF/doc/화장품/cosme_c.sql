/**********************************/
/* Table Name: 화장품 */
/**********************************/
DROP TABLE cosme;

--자식 테이블이 있어도 무시하고 테이블 삭제
DROP TABLE cosme CASCADE CONSTRAINTS;

select * from cosme;
CREATE TABLE COSME(
    COSMENO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    BRAND                             VARCHAR2(25)     NOT NULL,
    COSMENAME                         VARCHAR2(500)     NOT NULL,
    RDATE                             DATE     NOT NULL,
    adminno                          NUMBER(10)     NULL ,
    cosme_cateno                      NUMBER(10)     NULL ,
    cnt                      NUMBER(7)		 DEFAULT 0	NOT NULL,
    file1                        VARCHAR2(500)    NULL ,
    file1saved                  VARCHAR2(500)    NULL ,
    thumb1                VARCHAR2(500)    NULL ,
    size1                   INTEGER    NULL ,
  FOREIGN KEY (cosme_cateno) REFERENCES cosme_cate (cosme_cateno)
);


COMMENT ON TABLE COSME is '화장품';
COMMENT ON COLUMN COSME.COSMENO is '화장품 번호';
COMMENT ON COLUMN COSME.BRAND is '브랜드';
COMMENT ON COLUMN COSME.COSMENAME is '화장품 이름';
COMMENT ON COLUMN COSME.RDATE is '등록일';
COMMENT ON COLUMN COSME.adminno is '관리자 번호';
COMMENT ON COLUMN COSME.cosme_cateno is '화장품 종류 번호';
COMMENT ON COLUMN cateco.cnt is '관련 자료수';
COMMENT ON COLUMN COSME.file1 is '화장품 사진 파일';
COMMENT ON COLUMN COSME.file1saved is '화장품 사진 저장';
COMMENT ON COLUMN COSME.thumb1 is '화장품 사진 미리보기';
COMMENT ON COLUMN COSME.size1 is '화장품 사진 크기';

DROP SEQUENCE cosme_seq;
commit;
CREATE SEQUENCE cosme_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO cosme(cosmeno, brand, cosmename, rdate, adminno, cosme_cateno, file1, file1saved, thumb1, size1)
VALUES(cosme_seq.nextval, '한율', '한율 어린쑥 수분진정 크림', sysdate, 1, 1, '1', '2', '3', 4);


commit;

SELECT cosmeno, brand, cosmename, rdate, adminno, cosme_cateno, cosme_file, cosme_file_saved, cosme_file_preview, cosme_file_size FROM cosme ORDER BY cosmeno ASC;
 COSMENO BRAND           COSMENAME                                                 AGE RDATE                 ADMINNO COSME_CATENO COSME_FILE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           COSME_FILE_SAVED
---------- --------------- -------------------------------------------------- ---------- ------------------- ---------- ------------ -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
COSME_FILE_PREVIEW                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   COSME_FILE_SIZE COSME_YOUTUBE                                     
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------- --------------------------------------------------
         2 한율            한율 어린쑥 수분진정 크림                                  30 2023-05-31 05:35:49          1            1 1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    2                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
3                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  4 5                                                 

/* 전체 수정 */
UPDATE cosme
SET brand='솔데스크', cosmename='화장품', rdate=sysdate, adminno=1, cosme_cateno=2, cosme_file='2', cosme_file_saved='3', cosme_file_preview='4', cosme_file_size=20
WHERE cosmeno=2

/* 파일 수정 */
UPDATE cosme
SET cosme_file='5', cosme_file_saved='2', cosme_file_preview='4', cosme_file_size=20
WHERE cosmeno=2

DELETE FROM cosme
WHERE cosmeno=2

commit;
