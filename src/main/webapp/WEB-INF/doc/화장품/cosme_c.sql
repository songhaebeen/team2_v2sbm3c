/**********************************/
/* Table Name: 화장품 종류 */
/**********************************/
DROP TABLE cosme_cate;
select * from cosme_cate;
--자식 테이블이 있어도 무시하고 테이블 삭제
select * from cosme;
 INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno)
    VALUES(cosme_seq.nextval, '2', '2', sysdate, '2', '2');
     INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno)
    VALUES(cosme_seq.nextval, '1', '1', sysdate, '1', '1');
         INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno)
    VALUES(cosme_seq.nextval, '3', '3', sysdate, '3', '3');
             INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno)
    VALUES(cosme_seq.nextval, '32', '32', sysdate, '3', '3');
             INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno)
    VALUES(cosme_seq.nextval, '33', '33', sysdate, '3', '3');
    commit;
    
DROP TABLE cosme_cate CASCADE CONSTRAINTS;
select * from cosme_cate;
CREATE TABLE cosme_cate(
    cosme_cateno                      NUMBER(10)     NOT NULL    PRIMARY KEY,
    cosme_catename                    VARCHAR2(20)     NOT NULL
);

COMMENT ON TABLE cosme_cate is '화장품 종류';
COMMENT ON COLUMN cosme_cate.cosme_cateno is '화장품 종류 번호';
COMMENT ON COLUMN cosme_cate.cosme_catename is '화장품 종류 이름';

DROP SEQUENCE cosme_cate_seq;

CREATE SEQUENCE cosme_cate_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO cosme_cate(cosme_cateno, cosme_catename)
VALUES(cosme_cate_seq.nextval, '크림');

INSERT INTO cosme_cate(cosme_cateno, cosme_catename)
VALUES(cosme_cate_seq.nextval, '토너');

INSERT INTO cosme_cate(cosme_cateno, cosme_catename)
VALUES(cosme_cate_seq.nextval, '앰플');

INSERT INTO cosme_cate(cosme_cateno, cosme_catename)
VALUES(cosme_cate_seq.nextval, '로션');

INSERT INTO cosme_cate(cosme_cateno, cosme_catename)
VALUES(cosme_cate_seq.nextval, '세럼');

commit;

SELECT cosme_cateno, cosme_catename FROM cosme_cate ORDER BY cosme_cateno ASC;

COSME_CATENO COSME_CATENAME      
------------ --------------------
           1 크림                
           2 토너                
           3 앰플                
           4 로션                
           5 세럼                

UPDATE cosme_cate
SET cosme_catename='밤'
WHERE cosme_cateno = 5;

DELETE FROM cosme_cate
WHERE cosme_cateno=5;

/**********************************/
/* Table Name: 화장품 */
/**********************************/
DROP TABLE cosme;

--자식 테이블이 있어도 무시하고 테이블 삭제
DROP TABLE cosme CASCADE CONSTRAINTS;

select * from cosme;
CREATE TABLE COSME(
    COSMENO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    BRAND                             VARCHAR2(15)     NOT NULL,
    COSMENAME                         VARCHAR2(50)     NOT NULL,
    RDATE                             DATE     NOT NULL,
    masterno                          NUMBER(10)     NULL ,
    cosme_cateno                      NUMBER(10)     NULL ,
    cosme_file                        VARCHAR2(500)    NULL ,
    cosme_file_saved                  VARCHAR2(500)    NULL ,
    cosme_file_preview                VARCHAR2(500)    NULL ,
    cosme_file_size                   INTEGER    NULL ,
    cosme_youtube                     VARCHAR2(50)     NULL ,
  FOREIGN KEY (cosme_cateno) REFERENCES cosme_cate (cosme_cateno)
);


COMMENT ON TABLE COSME is '화장품';
COMMENT ON COLUMN COSME.COSMENO is '화장품 번호';
COMMENT ON COLUMN COSME.BRAND is '브랜드';
COMMENT ON COLUMN COSME.COSMENAME is '화장품 이름';
COMMENT ON COLUMN COSME.RDATE is '등록일';
COMMENT ON COLUMN COSME.masterno is '관리자 번호';
COMMENT ON COLUMN COSME.cosme_cateno is '화장품 종류 번호';
COMMENT ON COLUMN COSME.cosme_file is '화장품 사진 파일';
COMMENT ON COLUMN COSME.cosme_file_saved is '화장품 사진 저장';
COMMENT ON COLUMN COSME.cosme_file_preview is '화장품 사진 미리보기';
COMMENT ON COLUMN COSME.cosme_file_size is '화장품 사진 크기';
COMMENT ON COLUMN COSME.cosme_youtube is '화장품 유튜브 영상';

DROP SEQUENCE cosme_seq;
commit;
CREATE SEQUENCE cosme_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO cosme(cosmeno, brand, cosmename, rdate, masterno, cosme_cateno, cosme_file, cosme_file_saved, cosme_file_preview, cosme_file_size, cosme_youtube)
VALUES(cosme_seq.nextval, '한율', '한율 어린쑥 수분진정 크림', sysdate, 1, 1, '1', '2', '3', 4, '5');


commit;

SELECT cosmeno, brand, cosmename, rdate, masterno, cosme_cateno, cosme_file, cosme_file_saved, cosme_file_preview, cosme_file_size, cosme_youtube FROM cosme ORDER BY cosmeno ASC;
 COSMENO BRAND           COSMENAME                                                 AGE RDATE                 MASTERNO COSME_CATENO COSME_FILE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           COSME_FILE_SAVED
---------- --------------- -------------------------------------------------- ---------- ------------------- ---------- ------------ -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
COSME_FILE_PREVIEW                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   COSME_FILE_SIZE COSME_YOUTUBE                                     
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------- --------------------------------------------------
         2 한율            한율 어린쑥 수분진정 크림                                  30 2023-05-31 05:35:49          1            1 1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    2                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
3                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  4 5                                                 

/* 전체 수정 */
UPDATE cosme
SET brand='솔데스크', cosmename='화장품', rdate=sysdate, masterno=1, cosme_cateno=2, cosme_file='2', cosme_file_saved='3', cosme_file_preview='4', cosme_file_size=20, cosme_youtube='youtube'
WHERE cosmeno=2

/* 파일 수정 */
UPDATE cosme
SET cosme_file='5', cosme_file_saved='2', cosme_file_preview='4', cosme_file_size=20
WHERE cosmeno=2

DELETE FROM cosme
WHERE cosmeno=2

commit;
