/**********************************/
/* Table Name: 질문 게시판 */
/**********************************/
DROP TABLE qboard;
DROP TABLE qboard CASCADE CONSTRAINTS; 

CREATE TABLE qboard(
        qboardno            NUMBER(7)    NOT NULL,
        qtitle              VARCHAR(200)    NOT NULL,
        qcontent            VARCHAR(1000)    NOT NULL,
        rdate               DATE           NOT NULL,
        PRIMARY KEY (qboardno)
);

COMMENT ON TABLE qboard is '질문 게시판';
COMMENT ON COLUMN qboard.qboardno is '질문 게시판 번호';
COMMENT ON COLUMN qboard.qtitle is '질문 게시판 제목';
COMMENT ON COLUMN qboard.qcontent is '질문 게시판 내용';
COMMENT ON COLUMN qboard.rdate is '등록일';

DROP SEQUENCE qboard_seq;

CREATE SEQUENCE qboard_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO qboard(qboardno,qtitle, qcontent, rdate)
VALUES(qboard_seq.nextval,'질문', '있어요', sysdate);

commit;     

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO qboard(qboardno, qtitle, qcontent, rdate)
VALUES(qboard_seq.nextval, '질문', '궁금합니다', sysdate);
            
INSERT INTO qboard(qboardno, qtitle, qcontent, rdate)
VALUES(qboard_seq.nextval, '질문', '이 제품 성분이,,,', sysdate);
            
INSERT INTO qboard(qboardno, qtitle, qcontent, rdate)
VALUES(qboard_seq.nextval,  '질문', '써 보신 분 있나요', sysdate);

-- 유형 1 전체 목록
SELECT qboardno, qtitle, qcontent, rdate
FROM qboard
ORDER BY qboardno ASC;
         
--userno 1번인 관리자가 등록한 레코드이며, qboardno 1번인 여행에 속한 레코드임
  QBOARDNO     USERNO QTITLE                                             QCONTENT                                           RDATE              
---------- ---------- -------------------------------------------------- -------------------------------------------------- -------------------
         1          1 질문                                               있어요                                             2023-05-31 12:31:21
         2          1 질문                                               궁금합니다                                         2023-05-31 12:31:43
         3          1 질문                                               이 제품 성분이,,,                                  2023-05-31 12:31:43
         4          1 질문                                               써 보신 분 있나요                                  2023-05-31 12:31:43

--userno가 member 테이블에 등록이 안되어 있는 번호이면 레코드 삭제 후 다시 INSERT
DELETE FROM qboard;
commit;

-- 모든 레코드 삭제
DELETE FROM qboard;
commit;

-- 삭제
DELETE FROM qboard
WHERE qboardno = 12;
commit;

DELETE FROM qboard
WHERE qboardno=1 AND memberno = 1;

commit;

-- ----------------------------------------------------------------------------
-- 조회
-- ----------------------------------------------------------------------------
SELECT qboardno,qtitle, qcontent, rdate
FROM qboard
WHERE qboardno = 3;

-- 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE qboard
SET qtitle='질문입니다!', qcontent='해야할까요'
WHERE qboardno = 1;

-- SUCCESS
UPDATE qboard
SET qtitle='질문입니다!!', qcontent='해야 ''할'' 까요'
WHERE qboardno = 1;

-- SUCCESS
UPDATE qboard
SET qtitle='질문입니다', qcontent='해야 "할" 까요'
WHERE qboardno = 1;

-- 삭제
DELETE FROM qboard
WHERE qboardno = 4;

commit;

-- 특정 관리자에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM qboard 
WHERE memberno=1;

-- 특정 관리자에 속한 레코드 모두 삭제
DELETE FROM qboard
WHERE memberno=1;

-- 다수의 관리자에 속한 레코드 모두 삭제: IN
SELECT qboardno, memberno, qtitle
FROM qboard
WHERE memberno IN(1,2,3);
 QBOARDNO     USERNO QTITLE                                            
---------- ---------- --------------------------------------------------
         1          1 질문                                              
         2          1 질문                                              
         3          1 질문                                              
         4          1 질문                                                                                                                                                    
                                                                                                                                                                                                                    
SELECT qboardno, memberno, qtitle
FROM qboard
WHERE memberno IN('1','2','3');
 QBOARDNO     USERNO QTITLE                                            
---------- ---------- --------------------------------------------------
         1          1 질문                                              
         2          1 질문                                              
         3          1 질문                                              
         4          1 질문     
 commit;   