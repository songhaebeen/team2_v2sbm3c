-- 헬스 회원(부모): 헬스 회원 번호, 성명
DROP TABLE hmem;

CREATE TABLE hmem(
    hmemno    NUMBER(10)    NOT NULL, -- 헬스 회원 번호
    mname     VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    PRIMARY KEY (hmemno)              -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE hmem is '헬스 회원';
COMMENT ON COLUMN hmem.hmemno is '헬스 회원 번호';
COMMENT ON COLUMN hmem.mname is '성명';

DROP SEQUENCE hmem_seq;

CREATE SEQUENCE hmem_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;

INSERT INTO hmem(hmemno, mname) VALUES(hmem_seq.nextval, '왕눈이');
commit;

SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;
    HMEMNO MNAME               
---------- --------------------
         1 왕눈이      
         
INSERT INTO hmem(hmemno, mname)VALUES(hmem_seq.NEXTVAL, '아로미');
commit;

SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;
    HMEMNO MNAME               
---------- --------------------
         1 왕눈이              
         2 아로미              
         
INSERT INTO hmem(hmemno, mname) VALUES(hmem_seq.nextval,'투투투');
commit;

SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;
    HMEMNO MNAME               
---------- --------------------
         1 왕눈이              
         2 아로미              
         3 투투투            

-- 3번 회원 삭제하세요.
DELETE FROM hmem WHERE hmemno=3;
-- ORA-02292: integrity constraint (KD.SYS_C007067) violated - child record found
-- 가장 멀리 있는 자손부터 모두 삭제를 해 올라와야함.
-- PK(hmemno)를 사용하고 있는 자식 테이블을 찾아가서 레코드 삭제

-- hday 테이블에서 fk 컬럼의 값이 3번인 회원을 삭제한 후 실행
DELETE FROM hmem WHERE hmemno=3;
commit;
SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;

-- 2번 회원 삭제하세요.
DELETE FROM hmem WHERE hmemno=2;
-- ORA-02292: integrity constraint (KD.SYS_C007067) violated - child record found
DELETE FROM hmem WHERE hmemno=2;
commit;
SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;

-- 1번 회원 삭제하세요.
DELETE FROM hmem WHERE hmemno=1;
commit;
SELECT hmemno, mname FROM hmem ORDER BY hmemno ASC;

-- 삭제시는 자식(테이블, 레코드)먼저 삭제
-- 추가는 부모(테이블, 레코드) 먼저 추가



