/**********************************/
/* Table Name: 화장품 성분 */
/**********************************/
DROP TABLE ingred;

CREATE TABLE ingred(
		ingredno                NUMBER(10)            NOT NULL PRIMARY KEY,
		ingredname              VARCHAR2(30)          NOT NULL,
        ingredeffect            VARCHAR2(20)          NOT NULL,
        seqno                   NUMBER(10)            NOT NULL
);

COMMENT ON TABLE ingred is '화장품 성분';
COMMENT ON COLUMN ingred.ingredno is '화장품 성분 번호';
COMMENT ON COLUMN ingred.ingredname is '화장품 성분 이름';
COMMENT ON COLUMN ingred.ingredeffect is '성분 효과';
COMMENT ON COLUMN ingred.seqno is '출력 순서';

DROP SEQUENCE ingred_seq;

CREATE SEQUENCE ingred_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO ingred(ingredno, ingredname, ingredeffect, seqno) VALUES(ingred_seq.nextval, '나이아신아마이드', '미백', 0);
INSERT INTO ingred(ingredno, ingredname, ingredeffect, seqno) VALUES(ingred_seq.nextval, '시카', '진정', 0);
INSERT INTO ingred(ingredno, ingredname, ingredeffect, seqno) VALUES(ingred_seq.nextval, '마데카소사이드', '재생', 0);
INSERT INTO ingred(ingredno, ingredname, ingredeffect, seqno) VALUES(ingred_seq.nextval, '히알루론산', '수분', 0);
INSERT INTO ingred(ingredno, ingredname, ingredeffect, seqno) VALUES(ingred_seq.nextval, '비타민C', '미백', 0);
commit;

SELECT * FROM ingred;

SELECT ingredno, ingredname, ingredeffect, seqno
FROM ingred
ORDER BY seqno ASC;

UPDATE ingred
SET ingredname = '판테놀' , ingredeffect = '진정'
WHERE ingredno = 1;

DELETE FROM ingred
WHERE ingredno = 3;


SELECT ingredno, ingredname, ingredeffect, seqno
FROM ingred
WHERE ingredno = 1;

-- ------------------------------------------------------------------
-- 출력 순서 변경 관련 SQL
-- ------------------------------------------------------------------
-- 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소, id: update_seqno_decrease
UPDATE ingred SET seqno = seqno - 1 WHERE ingredno=1;

-- 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가, id: update_seqno_increase
UPDATE ingred SET seqno = seqno + 1 WHERE ingredno=1;

SELECT * FROM ingred;
 
commit;