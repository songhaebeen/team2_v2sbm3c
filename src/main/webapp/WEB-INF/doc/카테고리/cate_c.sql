/**********************************/
/* Table Name: 카테고리 */
/**********************************/
DROP TABLE cateco;

CREATE TABLE cateco(
    catecono                  NUMBER(10)		 NOT NULL	PRIMARY KEY,
    name                   VARCHAR2(30)		 NOT NULL,
    cnt                      NUMBER(7)		 DEFAULT 0	NOT NULL,
    rdate                    DATE		         NOT NULL,
    udate                   DATE	         	     NULL,
    seqno                   NUMBER(10)        DEFAULT 0  NOT NULL,    
    visible                   CHAR(1)    DEFAULT 'N'     NOT NULL
);

COMMENT ON TABLE cateco is '카테고리';
COMMENT ON COLUMN cateco.catecono is '카테고리번호';
COMMENT ON COLUMN cateco.name is '카테고리 이름';
COMMENT ON COLUMN cateco.cnt is '관련 자료수';
COMMENT ON COLUMN cateco.rdate is '등록일';
COMMENT ON COLUMN cateco.udate is '수정일';
COMMENT ON COLUMN cateco.seqno is '출력 순서';
COMMENT ON COLUMN cateco.visible is '출력 모드';

DROP SEQUENCE cateco_seq;

CREATE SEQUENCE cateco_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO cateco(catecono, name, cnt, rdate, seqno) VALUES(cateco_seq.nextval, '여행', 0, sysdate, 0);
INSERT INTO cateco(catecono, name, cnt, rdate, seqno) VALUES(cateco_seq.nextval, '영화', 0, sysdate, 0);
INSERT INTO cateco(catecono, name, cnt, rdate, seqno) VALUES(cateco_seq.nextval, '바다', 0, sysdate, 0);
INSERT INTO cateco(catecono, name, cnt, rdate, seqno) VALUES(cateco_seq.nextval, '산', 0, sysdate, 0);
commit;

-- SELECT LIST
SELECT catecono, name, cnt, rdate, seqno, visible FROM cateco ORDER BY catecono ASC;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:10:00
         2 영화                                    0 2023-03-21 12:10:00
         3 바다                                    0 2023-03-21 12:10:00
         4 산                                      0 2023-03-21 12:10:00
         
-- SELECT READ
SELECT catecono, name, cnt, rdate, seqno, visible FROM cateco WHERE catecono=1;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:10:00
         
-- UPDATE
UPDATE cateco SET name='캠핑', seqno=5 WHERE catecono=4;
commit;
SELECT * FROM cateco;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:18:26
         2 영화                                    0 2023-03-21 12:18:26
         3 바다                                    0 2023-03-21 12:18:26
         4 캠핑                                    0 2023-03-21 12:18:26

-- DELETE
-- DELETE FROM cate;
-- COMMIT;

DELETE FROM cateco WHERE catecono=6;
commit;

SELECT * FROM cateco;
    CATENO NAME                                  CNT RDATE              
---------- ------------------------------ ---------- -------------------
         1 여행                                    0 2023-03-21 12:18:26
         2 영화                                    0 2023-03-21 12:18:26
         3 바다                                    0 2023-03-21 12:18:26

-- COUNT(*)
SELECT COUNT(*) as cnt FROM cateco;
       CNT
----------
         3

-- ------------------------------------------------------------------
-- 출력 순서 변경 관련 SQL
-- ------------------------------------------------------------------
-- 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소, id: update_seqno_decrease
UPDATE cateco SET seqno = seqno - 1 WHERE catecono=1;

-- 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가, id: update_seqno_increase
UPDATE cateco SET seqno = seqno + 1 WHERE catecono=1;

commit;
-- seqno 컬럼 기준 오름차순 정렬 SELECT LIST, id: list_all
SELECT catecono, name, cnt, rdate, seqno, visible FROM cateco ORDER BY seqno ASC;

-- 한번에 다수의 컬럼값 수정은 사용자가 불편을 느낄수 있음으로 필요시 컬럼을 분할하여 값 변경
-- 예) 패스워드 변경, 별명 변경, 이름 변경등
-- 출력, id: update_visible_Y
UPDATE cateco SET visible='Y' WHERE catecono=1;

-- 숨김, id: update_visible_N
UPDATE cateco SET visible='N' WHERE catecono=2;
commit;

SELECT * FROM cateco;

-- 비회원/회원 SELECT LIST, id: list_all_y
SELECT catecono, name, cnt, rdate, seqno, visible 
FROM cateco 
WHERE visible='Y'
ORDER BY catecono ASC;

-- 자료수 증가, cnt 커럼 1씩 증가, id: update_cnt_add
UPDATE cateco SET cnt = cnt + 1 WHERE catecono=1;

-- 자료수 감소, cnt 커럼 1씩 감소, id: update_cnt_sub
UPDATE cateco SET cnt = cnt - 1 WHERE catecono=1;

commit;


