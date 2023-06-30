/**********************************/
/* Table Name: 화장품 유튜브 팁 */
/**********************************/
CREATE TABLE cosme_youtube_tip(
		youtubeno                     		INTEGER(10)		 NOT NULL		 PRIMARY KEY,
		COSMENO                       		NUMBER(10)		 NOT NULL,
		word                          		VARCHAR2(15)		 NULL ,
		youtubecontent                		VARCHAR2(4000)		 NULL ,
		views                         		NUMBER(10)		 NULL ,
		rdate                         		DATE		 NULL ,
		address                       		VARCHAR2(400)		 NULL ,
		seqno                         		NUMBER(10)		 NULL ,
		visible                       		CHAR(1)		 NULL 
);

COMMENT ON TABLE cosme_youtube_tip is '화장품 유튜브 팁';
COMMENT ON COLUMN cosme_youtube_tip.youtubeno is '유튜브번호';
COMMENT ON COLUMN cosme_youtube_tip.COSMENO is '화장품 번호';
COMMENT ON COLUMN cosme_youtube_tip.word is '검색어';
COMMENT ON COLUMN cosme_youtube_tip.youtubecontent is '내용';
COMMENT ON COLUMN cosme_youtube_tip.views is '조회수';
COMMENT ON COLUMN cosme_youtube_tip.rdate is '등록일';
COMMENT ON COLUMN cosme_youtube_tip.address is '유튜브 주소';
COMMENT ON COLUMN cosme_youtube_tip.seqno is '출력순서';
COMMENT ON COLUMN cosme_youtube_tip.visible is '출력 모드';


