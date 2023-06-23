/**********************************/
/* Table Name: 화장품/타입 릴레이션 */
/**********************************/
DROP TABLE cosmetype;
select * from cosmetype;

CREATE TABLE cosmetype(
		cosmetypeno NUMBER(10) NOT NULL PRIMARY KEY,
		cosmetypename VARCHAR2(20) NOT NULL
);

COMMENT ON TABLE cosmetype is '화장품 타입';
COMMENT ON COLUMN cosmetype.cosmetypeno is '화장품 타입 번호';
COMMENT ON COLUMN cosmetype.cosmetypename is '화장품 타입 이름';

DROP SEQUENCE cosmetype_seq;

CREATE SEQUENCE cosmetype_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE;             -- 다시 1부터 생성되는 것을 방지
  
-- CREATE -> SELECT LIST -> SELECT READ -> UPDATE -> DELETE -> COUNT(*)
-- CREATE
INSERT INTO cosmetype(cosmetypeno, cosmetypename) VALUES(cosmetype_seq.nextval, '수분');
INSERT INTO cosmetype(cosmetypeno, cosmetypename) VALUES(cosmetype_seq.nextval, '미백');
INSERT INTO cosmetype(cosmetypeno, cosmetypename) VALUES(cosmetype_seq.nextval, '진정');
INSERT INTO cosmetype(cosmetypeno, cosmetypename) VALUES(cosmetype_seq.nextval, '저자극');
INSERT INTO cosmetype(cosmetypeno, cosmetypename) VALUES(cosmetype_seq.nextval, '탄력');
commit;

SELECT * FROM cosmetype;

UPDATE cosmetype
SET cosmetypename = '보습'
WHERE cosmetypeno = 5;

DELETE FROM cosmetype
WHERE cosmetypeno = 10;

SELECT cosmetypeno, cosmetypename
FROM cosmetype
WHERE cosmetypeno = 1;

select
    constraint_name,
    table_name,
    r_constraint_name
from
    user_constraints
where
    constraint_name = 'SYS_C008520';

commit;

