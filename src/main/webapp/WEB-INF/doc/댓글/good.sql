/**********************************/
/* Table Name: 좋아요 */
/**********************************/
DROP TABLE good;

CREATE TABLE good(
        goodno                            NUMBER(10)     NOT NULL         PRIMARY KEY,
        fboardno                           NUMBER(10)    NOT NULL ,
        memberno                            NUMBER(6)  NOT NULL ,
        rdate                              DATE         NOT NULL,
  FOREIGN KEY (fboardno) REFERENCES fboard (fboardno),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE like is '좋아요';
COMMENT ON COLUMN like.likeno is '좋아요번호';
COMMENT ON COLUMN like.fboardno is '공지사항 번호';
COMMENT ON COLUMN like.memberno is '회원 번호';
COMMENT ON COLUMN like.rdate is '등록일';

DROP SEQUENCE good_seq;
CREATE SEQUENCE good_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO good(goodno, fboardno, memberno, rdate)
VALUES(reply_seq.nextval, 5, 3, '댓글1', '1234', sysdate);