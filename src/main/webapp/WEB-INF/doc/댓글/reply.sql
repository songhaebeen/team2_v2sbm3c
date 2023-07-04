/**********************************/
/* Table Name: 댓글 */
/**********************************/
DROP TABLE reply;

CREATE TABLE reply(
        replyno                            NUMBER(10)         NOT NULL         PRIMARY KEY,
        fboardno                           NUMBER(10)    NOT     NULL ,
        memberno                            NUMBER(6)         NOT NULL ,
        content                               VARCHAR2(1000)         NOT NULL,
        passwd                                VARCHAR2(20)         NOT NULL,
        rdate                              DATE NOT NULL,
  FOREIGN KEY (fboardno) REFERENCES fboard (fboardno),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE reply is '댓글';
COMMENT ON COLUMN reply.replyno is '댓글번호';
COMMENT ON COLUMN reply.fboardno is '자유게시판 번호';
COMMENT ON COLUMN reply.memberno is '회원 번호';
COMMENT ON COLUMN reply.content is '내용';
COMMENT ON COLUMN reply.passwd is '비밀번호';
COMMENT ON COLUMN reply.rdate is '등록일';

DROP SEQUENCE reply_seq;

CREATE SEQUENCE reply_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지

1) 등록
INSERT INTO reply(replyno, fboardno, memberno, content, passwd, rdate)
VALUES(reply_seq.nextval, 5, 3, '댓글1', '1234', sysdate);
INSERT INTO reply(replyno, fboardno, memberno, content, passwd, rdate)
VALUES(reply_seq.nextval, 5, 3, '댓글2', '1234', sysdate);
INSERT INTO reply(replyno, fboardno, memberno, content, passwd, rdate)
VALUES(reply_seq.nextval, 5, 3, '댓글3', '1234', sysdate);             

2) 전체 목록
SELECT replyno, fboardno, memberno, content, passwd, rdate
FROM reply
ORDER BY replyno DESC;

   REPLYNO   FBOARDNO   MEMBERNO CONTENT          PASSWD               RDATE              
---------- ---------- ---------- ---------------------------------------------------
         3          5          3 댓글3              1234         2023-06-27 10:19:40
         2          5          3 댓글2              1234        2023-06-27 10:19:40
         1          5          3 댓글1              1234        2023-06-27 10:19:24
       
3) reply + member join 목록
SELECT m.id,
          r.replyno, r.fboardno, r.memberno, r.content, r.passwd, r.rdate
FROM member m,  reply r
WHERE m.memberno = r.memberno
ORDER BY r.replyno DESC;

4) reply + member join + 특정 fboardno 별 목록
SELECT m.id,
           r.replyno, r.fboardno, r.memberno, r.content, r.passwd, r.rdate
FROM member m,  reply r
WHERE (m.memberno = r.memberno) AND r.fboardno=5
ORDER BY r.replyno DESC;

ID                                REPLYNO   FBOARDNO   MEMBERNO CONTENT    PASSWD        RDATE              
------------------------------ ---------- ---------- ---------- ------------------------------------------------------- 
user1@gmail.com                         3          5          3 댓글3       1234         2023-06-27 10:19:40
user1@gmail.com                         2          5          3 댓글2       1234         2023-06-27 10:19:40
user1@gmail.com                         1          5          3 댓글1       1234         2023-06-27 10:19:24

4) fboard + reply join + 특정 memberno 별 목록
SELECT r.replyno, m.memberno, r.fboardno, f.ftitle, r.content, r.rdate
FROM fboard f, reply r, member m
WHERE (f.fboardno = r.fboardno) AND m.memberno=2
ORDER BY r.replyno DESC;

5) 삭제
-- 패스워드 검사
SELECT count(passwd) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   1
   
-- 삭제
DELETE FROM reply
WHERE replyno=1;

6) fboardno 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE fboardno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE fboardno=1;

7) memberno에 해당하는 댓글 수 확인 및 삭제
SELECT COUNT(*) as cnt
FROM reply
WHERE memberno=1;

 CNT
 ---
   1

DELETE FROM reply
WHERE memberno=1;
 
8) 삭제용 패스워드 검사
SELECT COUNT(*) as cnt
FROM reply
WHERE replyno=1 AND passwd='1234';

 CNT
 ---
   0

9) 삭제
DELETE FROM reply
WHERE replyno=1;

10) reply + member join + 특정 fboardno 별 목록, 1000건 출력
SELECT id, replyno, fboardno, memberno, content, passwd, rdate, r
FROM (
    SELECT id, replyno, fboardno, memberno, content, passwd, rdate, rownum as r
    FROM (
        SELECT m.id, r.replyno, r.fboardno, r.memberno, r.content, r.passwd, r.rdate
        FROM member m, reply r
        WHERE (m.memberno = r.memberno) AND r.fboardno=2
        ORDER BY r.replyno DESC
    )
)
WHERE r <= 1000;

10) 댓글 조회
SELECT replyno, fboardno, memberno, content, passwd, rdate
FROM reply
WHERE memberno = 1 AND fboardno = 15;

11) 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE reply
SET content='ohhhhhh'
WHERE replyno = 50;

commit;

12) 최신 댓글 10건이나 20건 읽어와 조회 화면 화단에 출력
SELECT mname, replyno, fboardno, memberno, content, passwd, rdate, r
  FROM (
          SELECT mname, replyno, fboardno, memberno, content, passwd, rdate, rownum as r
          FROM (
                    SELECT m.mname, r.replyno, r.fboardno, r.memberno, r.content, r.passwd, r.rdate
                    FROM reply r, member m
                    WHERE (m.memberno = r.memberno) AND r.fboardno = 2
                    ORDER BY r.replyno DESC
           )
    )
    WHERE r <= 10;
