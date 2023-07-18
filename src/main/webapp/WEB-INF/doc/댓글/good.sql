/**********************************/
/* Table Name: 좋아요 */
/**********************************/
DROP TABLE good CASCADE CONSTRAINTS ;

CREATE TABLE good(
        goodno                            NUMBER(10)     NOT NULL         PRIMARY KEY,
        fboardno                           NUMBER(10)    NOT NULL ,
        memberno                            NUMBER(6)  NOT NULL ,
        rdate                              DATE         NOT NULL,
  FOREIGN KEY (fboardno) REFERENCES fboard (fboardno) ON DELETE CASCADE,
  FOREIGN KEY (memberno) REFERENCES member (memberno) ON DELETE CASCADE
);

COMMENT ON TABLE good is '좋아요';
COMMENT ON COLUMN good.goodno is '좋아요번호';
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

commit;

--좋아요 눌렀나 체크
SELECT COUNT(*) as cnt
FROM good 
WHERE fboardno = 3 AND memberno = 1;

UPDATE good
SET like_check = like_check + 1 
WHERE memberno=3 AND fboardno=1;

--좋아요
INSERT INTO good(goodno, fboardno, memberno, rdate)
VALUES(good_seq.nextval, 2, 1, sysdate);

--조회
SELECT goodno, fboardno, memberno, rdate
FROM good
WHERE fboardno = 3 AND memberno = 1;

--전체 목록
SELECT goodno, fboardno, memberno, rdate
FROM good ORDER BY goodno ASC;

--회원별 목록
SELECT g.goodno, m.memberno, g.fboardno, f.ftitle, g.rdate
FROM fboard f, good g, member m
WHERE (f.fboardno = g.fboardno) AND m.memberno=3
ORDER BY g.goodno DESC;

COMMIT;

ROLLBACK;

--좋아요 취소
DELETE FROM 
good WHERE goodno = 5;

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


DELETE FROM good 
WHERE fboardno=1 AND memberno=1;

