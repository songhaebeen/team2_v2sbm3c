/**********************************/
/* Table Name: 화장품 종류 */
/**********************************/
DROP TABLE cosme_cate;

CREATE TABLE cosme_cate(
		cosme_cateno NUMBER(10) NOT NULL PRIMARY KEY,
		cosme_catename VARCHAR2(20) NOT NULL,
        cnt            NUMBER(7)  DEFAULT 0	NOT NULL,
        rdate          DATE NOT NULL,
        seqno          NUMBER(10) DEFAULT 0  NOT NULL
);

COMMENT ON TABLE cosme_cate is '화장품 종류';
COMMENT ON COLUMN cosme_cate.cosme_cateno is '화장품 종류 번호';
COMMENT ON COLUMN cosme_cate.cosme_catename is '화장품 종류 이름';
COMMENT ON COLUMN cosme_cate.cnt is '관련 자료수';
COMMENT ON COLUMN cosme_cate.rdate is '등록일';
COMMENT ON COLUMN cosme_cate.seqno is '출력 순서';

DROP SEQUENCE cosme_cate_seq;

CREATE SEQUENCE cosme_cate_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO cosme_cate(cosme_cateno, cosme_catename, cnt, rdate, seqno) VALUES(cosme_cate_seq.nextval, '스킨, 토너', 0, sysdate, 0);
INSERT INTO cosme_cate(cosme_cateno, cosme_catename, cnt, rdate, seqno) VALUES(cosme_cate_seq.nextval, '로션', 0, sysdate, 0);
INSERT INTO cosme_cate(cosme_cateno, cosme_catename, cnt, rdate, seqno) VALUES(cosme_cate_seq.nextval, '크림', 0, sysdate, 0);
INSERT INTO cosme_cate(cosme_cateno, cosme_catename, cnt, rdate, seqno) VALUES(cosme_cate_seq.nextval, '앰플, 세럼', 0, sysdate, 0);
INSERT INTO cosme_cate(cosme_cateno, cosme_catename, cnt, rdate, seqno) VALUES(cosme_cate_seq.nextval, '오일, 밤', 0, sysdate, 0);
commit;
select * from cosme;
delete from cosme;
select * from cosmetype;

delete from recommend;
ALTER TABLE cosme_ingred_relate
ADD CONSTRAINT ingredfk
FOREIGN KEY (ingredno) REFERENCES ingred(ingredno)
ON DELETE cascade;
ALTER TABLE cosme_type_relate
ADD CONSTRAINT cosmetypefk
FOREIGN KEY (cosmetypeno) REFERENCES cosmetype(cosmetypeno)

select * from cosme_ingred_relate;
select * from cosme_type_relate;
commit;
SELECT * FROM cosme_cate;

UPDATE cosme_cate
SET cosme_catename = '오일, 밤'
WHERE cosme_cateno = 5;

DELETE FROM cosme_cate
WHERE cosme_cateno = 3;

grant select, update, insert, delete on cosme_cate to team2;

SELECT cosme_cateno, cosme_catename
FROM cosme_cate
WHERE cosme_cateno = 1;

-- COUNT(*)
SELECT COUNT(*) as cnt FROM cosme_cate;

-- ------------------------------------------------------------------
-- 출력 순서 변경 관련 SQL
-- ------------------------------------------------------------------
-- 출력 순서 상향(10등 -> 1등), seqno 컬럼의 값 감소, id: update_seqno_decrease
UPDATE cosme_cate SET seqno = seqno - 1 WHERE cosme_cateno=1;

-- 출력 순서 하향(1등 -> 10등), seqno 컬럼의 값 증가, id: update_seqno_increase
UPDATE cosme_cate SET seqno = seqno + 1 WHERE cosme_cateno=1;

SELECT * FROM cosme_cate;

-- 자료수 증가, cnt 커럼 1씩 증가, id: update_cnt_add
UPDATE cosme_cate SET cnt = cnt + 1 WHERE cosme_cateno=1;

-- 자료수 감소, cnt 커럼 1씩 감소, id: update_cnt_sub
UPDATE cosme_cate SET cnt = cnt - 1 WHERE cosme_cateno=1;
 
commit;


DROP TABLE cosme_type_relate;



CREATE TABLE cosme_type_relate (
  -- 테이블 열 정의
  cosmetypeno INT,
  cosmeno int,  
  CONSTRAINT cosmetypefk FOREIGN KEY (cosmetypeno) REFERENCES cosmetype(cosmetypeno) ON DELETE CASCADE,
  CONSTRAINT cosmefk FOREIGN KEY (cosmeno) REFERENCES cosme(cosmeno) ON DELETE CASCADE
);

DROP TABLE cosme_ingred_relate;



CREATE TABLE cosme_ingred_relate (
  -- 테이블 열 정의
  ingredno INT,
  cosmeno int,  
  CONSTRAINT ingredfk FOREIGN KEY (ingredno) REFERENCES ingred(ingredno) ON DELETE CASCADE,
  CONSTRAINT cosmefkinged FOREIGN KEY (cosmeno) REFERENCES cosme(cosmeno) ON DELETE CASCADE
);






