-- 헬스 출석 정보(자식): 헬스 출석 번호, 헬스 회원 번호, 출석일
DROP TABLE hday;

CREATE TABLE hday(
    hdayno    NUMBER(10)    NOT NULL, -- 헬스 출석 번호
    hmemno    NUMBER(10)    NOT NULL, -- 헬스 회원 번호
    rdate     VARCHAR(20)   NOT NULL, -- 출석일
    PRIMARY KEY (hdayno),             -- 한번 등록된 값은 중복 안됨
    FOREIGN KEY (hmemno) REFERENCES hmem (hmemno)
);

COMMENT ON TABLE hday is '헬스 출석';
COMMENT ON COLUMN hday.hdayno is '헬스 출석 번호';
COMMENT ON COLUMN hday.hmemno is '헬스 회원 번호';
COMMENT ON COLUMN hday.rdate is '출석일';

DROP SEQUENCE hday_seq;

CREATE SEQUENCE hday_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
-- INSERT INTO hday(hdayno, hmemno, rdate) VALUES(hday_seq.nextval, ???, sysdate);
-- hmemno값을 무엇을 사용해야하나?

INSERT INTO hday(hdayno, hmemno, rdate) VALUES(hday_seq.nextval, 1, sysdate);
-- ORA-02291: integrity constraint (KD.SYS_C007067) violated - parent key not found
-- 1번이 부모테이블에 없음으로 등록 불가!

-- hmem 테이블에 1번 회원이 존재하는 경우
INSERT INTO hday(hdayno, hmemno, rdate) VALUES(hday_seq.nextval, 1, sysdate);
commit;

SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;
    HDAYNO     HMEMNO RDATE               
---------- ---------- --------------------
         2          1 2023-04-11 02:47:40 

[과제] 2번 회원을 등록하고 2번 회원이 오늘 헬스클럽을 사용한 시각을 등록하세요.
INSERT INTO hday(hdayno, hmemno, rdate) VALUES(hday_seq.nextval, 2, sysdate);
commit;
SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;

[과제] 3번 회원을 등록하고 3번 회원이 오늘 헬스클럽을 사용한 시각을 등록하세요.
INSERT INTO  hday (hdayno, hmemno, rdate) VALUES(hday_seq.nextval, 3, sysdate);
commit;
SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;

    HDAYNO     HMEMNO RDATE               
---------- ---------- --------------------
         2          1 2023-04-11 02:47:40 
         3          2 2023-04-11 02:57:22 
         4          3 2023-04-11 03:09:43 
         
-- 자식 레코드 등록시 부모 테이블 레코드 먼저 등록되어 있어야함.

-- 3번 회원을 FK로 사용하는 레코드 모두 삭제
DELETE FROM hday WHERE hmemno=3;
commit;
SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;
    HDAYNO     HMEMNO RDATE               
---------- ---------- --------------------
         2          1 2023-04-11 02:47:40 
         3          2 2023-04-11 02:57:22 

-- 2번 회원을 FK로 사용하는 레코드 모두 삭제
DELETE FROM hday WHERE hmemno=2;
commit;
SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;

-- 12번 회원을 FK로 사용하는 레코드 모두 삭제
DELETE FROM hday WHERE hmemno=1;
commit;
SELECT hdayno, hmemno, rdate FROM hday ORDER BY hdayno ASC;

