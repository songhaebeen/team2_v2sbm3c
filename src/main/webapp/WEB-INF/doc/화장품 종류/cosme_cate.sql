/**********************************/
/* Table Name: 화장품 종류 */
/**********************************/
DROP TABLE cosme_cate;

CREATE TABLE cosme_cate(
		cosme_cateno NUMBER(10) NOT NULL PRIMARY KEY,
		cosme_catename VARCHAR2(20) NOT NULL
);

COMMENT ON TABLE cosme_cate is '화장품 종류';
COMMENT ON COLUMN cosme_cate.cosme_cateno is '화장품 종류 번호';
COMMENT ON COLUMN cosme_cate.cosme_catename is '화장품 종류 이름';

DROP SEQUENCE cosme_cate_seq;

CREATE SEQUENCE cosme_cate_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO cosme_cate(cosme_cateno, cosme_catename) VALUES(cosme_cate_seq.nextval, '스킨, 토너');
INSERT INTO cosme_cate(cosme_cateno, cosme_catename) VALUES(cosme_cate_seq.nextval, '로션');
INSERT INTO cosme_cate(cosme_cateno, cosme_catename) VALUES(cosme_cate_seq.nextval, '크림');
INSERT INTO cosme_cate(cosme_cateno, cosme_catename) VALUES(cosme_cate_seq.nextval, '앰플, 세럼');
INSERT INTO cosme_cate(cosme_cateno, cosme_catename) VALUES(cosme_cate_seq.nextval, '오일, 밤');
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
WHERE cosme_cateno = 1
ORDER BY cosme_cateno DESC;

SELECT cosme_cateno, cosme_catename
FROM cosme_cate
WHERE cosme_cateno = 1;


commit;