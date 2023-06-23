/**********************************/
/* Table Name: 화장품 성분 */
/**********************************/
DROP TABLE ingred;

CREATE TABLE ingred(
		ingredno                NUMBER(10)            NOT NULL PRIMARY KEY,
		ingredname              VARCHAR2(30)          NOT NULL,
        ingredeffect            VARCHAR2(20)          NOT NULL
);

COMMENT ON TABLE ingred is '화장품 성분';
COMMENT ON COLUMN ingred.ingredno is '화장품 성분 번호';
COMMENT ON COLUMN ingred.ingredname is '화장품 성분 이름';
COMMENT ON COLUMN ingred.ingredeffect is '성분 효과';

DROP SEQUENCE ingred_seq;

CREATE SEQUENCE ingred_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO ingred(ingredno, ingredname, ingredeffect) VALUES(ingred_seq.nextval, '나이아신아마이드', '미백');
INSERT INTO ingred(ingredno, ingredname, ingredeffect) VALUES(ingred_seq.nextval, '시카', '진정');
INSERT INTO ingred(ingredno, ingredname, ingredeffect) VALUES(ingred_seq.nextval, '마데카소사이드', '재생');
INSERT INTO ingred(ingredno, ingredname, ingredeffect) VALUES(ingred_seq.nextval, '히알루론산', '수분');
INSERT INTO ingred(ingredno, ingredname, ingredeffect) VALUES(ingred_seq.nextval, '비타민C', '미백');
commit;

SELECT * FROM ingred;

UPDATE ingred
SET ingredname = '판테놀' , ingredeffect = '진정'
WHERE ingredno = 1;

DELETE FROM ingred
WHERE ingredno = 3;


SELECT ingredno, ingredname, ingredeffect
FROM ingred
WHERE ingredno = 1;

 
commit;