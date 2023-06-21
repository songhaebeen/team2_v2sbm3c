/**********************************/
/* Table Name: 카테고리 */
/**********************************/
CREATE TABLE CATE(
		CATENO                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		UDATE                         		DATE		 NULL ,
		SEQNO                         		NUMBER(10)		 NOT NULL,
		visible                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL
);

COMMENT ON TABLE CATE is '카테고리';
COMMENT ON COLUMN CATE.CATENO is '카테고리번호';
COMMENT ON COLUMN CATE.NAME is '카테고리 이름';
COMMENT ON COLUMN CATE.CNT is '관련 자료수';
COMMENT ON COLUMN CATE.RDATE is '등록일';
COMMENT ON COLUMN CATE.UDATE is '수정일';
COMMENT ON COLUMN CATE.SEQNO is '출력 순서';
COMMENT ON COLUMN CATE.visible is '출력 모드';


/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE admin(
		adminno                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY
);

COMMENT ON TABLE admin is '관리자';
COMMENT ON COLUMN admin.adminno is '관리자번호';


/**********************************/
/* Table Name: 컨텐츠 */
/**********************************/
CREATE TABLE contents(
		contentsno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		cateno                        		NUMBER(10)		 NULL ,
		adminno                       		NUMBER(10)		 NULL ,
		CATENO                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (CATENO) REFERENCES CATE (CATENO),
  FOREIGN KEY (adminno) REFERENCES admin (adminno)
);

COMMENT ON TABLE contents is '컨텐츠';
COMMENT ON COLUMN contents.contentsno is '컨텐츠 번호';
COMMENT ON COLUMN contents.cateno is '카테고리번호';
COMMENT ON COLUMN contents.adminno is '관리자번호';
COMMENT ON COLUMN contents.CATENO is '카테고리번호';


/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE member(
		memberno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY
);

COMMENT ON TABLE member is '회원';
COMMENT ON COLUMN member.memberno is '회원번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE reply(
		replyno                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		contentsno                    		NUMBER(10)		 NULL ,
		memberno                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (contentsno) REFERENCES contents (contentsno),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE reply is '댓글';
COMMENT ON COLUMN reply.replyno is '댓글번호';
COMMENT ON COLUMN reply.contentsno is '컨텐츠 번호';
COMMENT ON COLUMN reply.memberno is '회원번호';


/**********************************/
/* Table Name: 첨부파일 */
/**********************************/
CREATE TABLE attachfile(
		attachfileno                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		contentsno                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (contentsno) REFERENCES contents (contentsno)
);

COMMENT ON TABLE attachfile is '첨부파일';
COMMENT ON COLUMN attachfile.attachfileno is '첨부파일  번호';
COMMENT ON COLUMN attachfile.contentsno is '컨텐츠 번호';


/**********************************/
/* Table Name: 신고 */
/**********************************/
CREATE TABLE alert(
		alertno                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		memberno                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE alert is '신고';
COMMENT ON COLUMN alert.alertno is '신고번호';
COMMENT ON COLUMN alert.memberno is '회원번호';


