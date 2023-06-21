/**********************************/
/* Table Name: 카테고리 */
/**********************************/
DROP TABLE cate;

CREATE TABLE cate(
		cateno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL,
		cnt                           		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		rdate                         		DATE		 NOT NULL
);

COMMENT ON TABLE cate is '카테고리';
COMMENT ON COLUMN cate.cateno is '카테고리번호';
COMMENT ON COLUMN cate.name is '카테고리 이름';
COMMENT ON COLUMN cate.cnt is '관련 자료수';
COMMENT ON COLUMN cate.rdate is '등록일';

DROP SEQUENCE cate_seq;

CREATE SEQUENCE cate_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO cate(cateno, name, cnt, rdate) VALUES(cate_seq.nextval, '여행', 0, sysdate);
INSERT INTO cate(cateno, name, cnt, rdate) VALUES(cate_seq.nextval, '영화', 0, sysdate);
INSERT INTO cate(cateno, name, cnt, rdate) VALUES(cate_seq.nextval, '바다', 0, sysdate);
INSERT INTO cate(cateno, name, cnt, rdate) VALUES(cate_seq.nextval, '산', 0, sysdate);
commit;

-- SELECT LIST
SELECT cateno, name, cnt, rdate FROM cate ORDER BY cateno ASC;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:10:00
         2 영화                                    0 2023-03-21 12:10:00
         3 바다                                    0 2023-03-21 12:10:00
         4 산                                      0 2023-03-21 12:10:00
         
-- SELECT READ
SELECT cateno, name, cnt, rdate FROM cate WHERE cateno=1;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:10:00
         
-- UPDATE
UPDATE cate SET name='캠핑' WHERE cateno=4;
commit;
SELECT * FROM cate;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:18:26
         2 영화                                    0 2023-03-21 12:18:26
         3 바다                                    0 2023-03-21 12:18:26
         4 캠핑                                    0 2023-03-21 12:18:26

-- DELETE
-- DELETE FROM cate;
-- COMMIT;

DELETE FROM cate WHERE cateno=6;
commit;

SELECT * FROM cate;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:18:26
         2 영화                                    0 2023-03-21 12:18:26
         3 바다                                    0 2023-03-21 12:18:26

-- COUNT(*)
SELECT COUNT(*) as cnt FROM cate;
       CNT
----------
         3


