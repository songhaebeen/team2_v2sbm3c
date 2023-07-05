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

COMMENT ON TABLE good is '좋아요';
COMMENT ON COLUMN good.likeno is '좋아요번호';
COMMENT ON COLUMN good.fboardno is '자유게시판 번호';
COMMENT ON COLUMN good.memberno is '회원 번호';
COMMENT ON COLUMN good.rdate is '등록일';

DROP SEQUENCE good_seq;

CREATE SEQUENCE good_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO good(goodno, fboardno, memberno, rdate)
VALUES(good_seq.nextval, 1, 3, sysdate);

commit;

SELECT goodno FROM good;

DELETE GOOD
WHERE fboardno=1
AND memberno=3

SELECT BB.fboardno, G.memberno, NVL(GC.gcnt,0) AS gcnt
FROM fboard BB LEFT OUTER JOIN good G
                                  ON BB.fboardno = G.fboardno
                                  AND G.memberno= 3  
                     LEFT OUTER JOIN (SELECT COUNT(*) AS gcnt, fboardno
                                      FROM good 
                                      WHERE fboardno= 1
                                      GROUP BY fboardno) GC
                                  ON BB.fboardno = GC.fboardno
WHERE BB.fboardno = 1;

좋아요 했는지 확인
SELECT COUNT(*) FROM good WHERE fboardno = 1 AND memberno = 3;