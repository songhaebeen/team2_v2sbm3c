/**********************************/
/* Table Name: 답변 */
/**********************************/
DROP TABLE answer;

CREATE TABLE answer(
        answerno              NUMBER(7)      NOT NULL PRIMARY KEY,
        masterno            NUMBER(10)     NOT NULL , -- FK
        qboardno         NUMBER(7)    NOT NULL,
        acontent            VARCHAR(50)    NOT NULL,
        rdate               DATE           NOT NULL,
        FOREIGN KEY (masterno) REFERENCES master (masterno),
        FOREIGN KEY (qboardno) REFERENCES qboard (qboardno)
);

COMMENT ON TABLE answer is '답변';
COMMENT ON COLUMN answer.masterno is '관리자 번호';
COMMENT ON COLUMN answer.qboardno is '질문 게시판 번호';
COMMENT ON COLUMN answer.rdate is '등록일';
COMMENT ON COLUMN answer.answerno is '답변 번호';
COMMENT ON COLUMN answer.acontent is '답변 내용';
COMMENT ON COLUMN answer.rdate is '등록일';

DROP SEQUENCE answer_seq;

CREATE SEQUENCE answer_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO answer(answerno, qboardno, masterno, acontent, rdate)
VALUES(answer_seq.nextval, 1, 1, '질문 있어요', sysdate);

commit;     

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO answer(answerno, qboardno, masterno, acontent, rdate)
VALUES(answer_seq.nextval, 1, 1, '질문 있어요', sysdate);
            
INSERT INTO answer(answerno, qboardno, masterno, acontent, rdate)
VALUES(answer_seq.nextval, 1, 1, '질문 있어요', sysdate);
            
INSERT INTO answer(answerno, qboardno, masterno, acontent, rdate)
VALUES(answer_seq.nextval, 1, 2, '질문 있어요', sysdate);

-- 유형 1 전체 목록
SELECT answerno, qboardno, masterno, acontent, rdate
FROM answer
ORDER BY answerno ASC;
         
--masterno 1번인 관리자가 등록한 레코드이며, noticeno 1번인 여행에 속한 레코드임
 ANSWERNO   QBOARDNO   MASTERNO ACONTENT                                           RDATE              
---------- ---------- ---------- -------------------------------------------------- -------------------
         1          1          1 질문 있어요                                        2023-05-31 12:40:14
         2          1          1 질문 있어요                                        2023-05-31 12:40:17
         3          1          1 질문 있어요                                        2023-05-31 12:40:17
         4          1          2 질문 있어요                                        2023-05-31 12:40:17

--userno가 member 테이블에 등록이 안되어 있는 번호이면 레코드 삭제 후 다시 INSERT
DELETE FROM answer;
commit;

-- 모든 레코드 삭제
DELETE FROM answer;
commit;

-- 삭제
DELETE FROM answer
WHERE answerno = 1;
commit;

DELETE FROM answer
WHERE answerno=1 AND masterno = 1;

commit;

-- ----------------------------------------------------------------------------
-- 조회
-- ----------------------------------------------------------------------------
SELECT answerno, qboardno, masterno, acontent, rdate
FROM answer
WHERE answerno = 3;

-- 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE answer
SET acontent='답변입니다'
WHERE answerno = 1;

-- SUCCESS
UPDATE answer
SET acontent='답변''입니''다'
WHERE answerno = 1;

-- SUCCESS
UPDATE answer
SET acontent='답변"입니"다'
WHERE answerno = 1;

-- 삭제
DELETE FROM answer
WHERE answerno = 4;

commit;

-- 특정 관리자에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM answer 
WHERE masterno=1;

-- 특정 관리자에 속한 레코드 모두 삭제
DELETE FROM answer
WHERE masterno=1;

-- 다수의 관리자에 속한 레코드 모두 삭제: IN
SELECT answerno, masterno, acontent
FROM answer
WHERE masterno IN(1,2,3);
  ANSWERNO   MASTERNO ACONTENT                                          
---------- ---------- --------------------------------------------------
         4          2 질문 있어요                                                                                                                                          
                                                                                                                                                                                                                    
SELECT answerno, masterno, acontent
FROM answer
WHERE masterno IN('1','2','3');
  ANSWERNO   MASTERNO ACONTENT                                          
---------- ---------- --------------------------------------------------
         4          2 질문 있어요                                                                                                                                           