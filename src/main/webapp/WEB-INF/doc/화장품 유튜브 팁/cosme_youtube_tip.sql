/**********************************/
/* Table Name: 화장품 유튜브 팁 */
/**********************************/
DROP TABLE cosme_youtube_tip;

--자식 테이블이 있어도 무시하고 테이블 삭제
DROP TABLE cosme_youtube_tip CASCADE CONSTRAINTS;

select * from cosme_youtube_tip;
CREATE TABLE cosme_youtube_tip(
		youtubeno                     		NUMBER(10)		 NOT NULL PRIMARY KEY,
		cosmeno                       		NUMBER(10)		 NOT NULL,
        youtubetitle                        VARCHAR2(100)   NOT NULL,
		word                          		VARCHAR2(15)		 NULL ,
		youtubecontent                		VARCHAR2(4000)		 NULL ,
		views                         		NUMBER(10)		 NULL ,
		rdate                         		DATE		 NULL ,
		address                    VARCHAR2(1000)     NULL ,
    seqno                             NUMBER(10)     NULL ,
    visible                           CHAR(1)    NULL ,
        FOREIGN KEY (cosmeno) REFERENCES cosme (cosmeno)

);

COMMENT ON TABLE cosme_youtube_tip is '화장품 유튜브 팁';
COMMENT ON COLUMN cosme_youtube_tip.youtubeno is '유튜브번호';
COMMENT ON COLUMN cosme_youtube_tip.COSMENO is '화장품 번호';
COMMENT ON COLUMN cosme_youtube_tip.youtubetitle is '유튜브 제목';
COMMENT ON COLUMN cosme_youtube_tip.word is '검색어';
COMMENT ON COLUMN cosme_youtube_tip.youtubecontent is '내용';
COMMENT ON COLUMN cosme_youtube_tip.views is '조회수';
COMMENT ON COLUMN cosme_youtube_tip.rdate is '등록일';
COMMENT ON COLUMN cosme_youtube_tip.address is '유튜브 주소';
COMMENT ON COLUMN cosme_youtube_tip.seqno is '출력순서';
COMMENT ON COLUMN cosme_youtube_tip.visible is '출력 모드';

DROP SEQUENCE cosme_youtube_tip_seq;
commit;
CREATE SEQUENCE cosme_youtube_tip_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO cosme_youtube_tip(youtubeno, COSMENO, youtubetitle, youtubecontent, rdate, address, seqno, visible)
VALUES(cosme_youtube_tip_seq.nextval, 1, '피부에 좋은 화장품 소개 영상', '내용입니다', sysdate, 'youtube address', 2, 'y');

commit;

SELECT youtubeno, COSMENO, youtubetitle, youtubecontent, views, rdate, address, seqno, visible FROM cosme_youtube_tip ORDER BY youtubeno ASC;

/* 전체 수정 */
UPDATE cosme_youtube_tip
SET cosmeno=2, youtubetitle='기분 좋은 화장품 소개', youtubecontent='수정된 내용입니다', address='유튜브 주소'
WHERE youtubeno=1

DELETE FROM cosme_youtube_tip
WHERE youtubeno=1

commit;
