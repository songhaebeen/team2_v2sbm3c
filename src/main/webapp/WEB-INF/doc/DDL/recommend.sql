/**********************************/
/* Table Name: 성분 */
/**********************************/
CREATE TABLE ingred(
		ingredno NUMERIC(10) NOT NULL PRIMARY KEY,
		ingredname VARCHAR(50),
		ingredeffect CHAR(300)
);

/**********************************/
/* Table Name: 화장품타입 */
/**********************************/
CREATE TABLE cosmetype(
		cosmetypeno NUMERIC(10) NOT NULL PRIMARY KEY,
		cosmetypename VARCHAR(20) NOT NULL
);

/**********************************/
/* Table Name: 화장품 종류 */
/**********************************/
CREATE TABLE cosme_cate(
		cosme_cateno NUMBER(10) NOT NULL PRIMARY KEY,
		cosme_catename VARCHAR2(20) NOT NULL
);

/**********************************/
/* Table Name: 화장품 */
/**********************************/
CREATE TABLE COSME(
		COSMENO NUMBER(10) NOT NULL PRIMARY KEY,
		BRAND VARCHAR2(15) NOT NULL,
		COSMENAME VARCHAR2(50) NOT NULL,
		RDATE DATE NOT NULL,
		masterno NUMBER(10),
		cosme_cateno NUMBER(10),
		cosme_file VARCHAR2(500),
		cosme_file_saved VARCHAR2(500),
		cosme_file_preview VARCHAR2(500),
		cosme_file_size INTEGER(10),
		cosme_youtube VARCHAR2(50),
  FOREIGN KEY (cosme_cateno) REFERENCES cosme_cate (cosme_cateno)
);
ALTER TABLE cosme RENAME COLUMN masterno TO adminno;


/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE member(
		memberno NUMERIC(10) NOT NULL PRIMARY KEY,
		id VARCHAR(20) NOT NULL,
		passwd VARCHAR(70) NOT NULL,
		email VARCHAR(70) NOT NULL,
		mname VARCHAR(30) NOT NULL,
		tel VARCHAR(15),
		zipcode VARCHAR(10),
		address1 VARCHAR(80),
		address2 VARCHAR(60),
		mdate DATE NOT NULL,
		grade NUMERIC(2) NOT NULL
);
commit;
/**********************************/
/* Table Name: 사용후기 */
/**********************************/
CREATE TABLE rating(
		ratingno INT NOT NULL PRIMARY KEY,
		COSMENO INTEGER(10),
		userno INT,
		rainggrade INT NOT NULL,
		ratingcontent VARCHAR(200),
		memberno NUMERIC(10),
  FOREIGN KEY (COSMENO) REFERENCES COSME (COSMENO),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

/**********************************/
/* Table Name: 화장품/성분 릴레이션 */
/**********************************/
CREATE TABLE cosne_ingred_relate(
		COSMENO NUMERIC(10),
		ingredno INT,
  FOREIGN KEY (ingredno) REFERENCES ingred (ingredno),
  FOREIGN KEY (COSMENO) REFERENCES COSME (COSMENO)
);

drop table cosme_type;
commit;
/**********************************/
/* Table Name: 화장품/타입 릴레이션 */
/**********************************/
CREATE TABLE cosme_type_relate(
		cosmetypeno INT,
		COSMENO NUMERIC(10),
  FOREIGN KEY (cosmetypeno) REFERENCES cosmetype (cosmetypeno),
  FOREIGN KEY (COSMENO) REFERENCES COSME (COSMENO)
);

/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE master(
		masterno NUMBER(10) NOT NULL PRIMARY KEY,
		id VARCHAR2(20) NOT NULL,
		passwd VARCHAR2(15) NOT NULL,
		mname VARCHAR2(20) NOT NULL,
		rdate DATE NOT NULL,
		authority NUMBER(5) NOT NULL
);
insert into master(masterno, id, passwd, mname, rdate, authority)
values(1,'1234','1234','세민',sysdate,1);
commit;
/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE notice(
		noticeno NUMBER(10) NOT NULL PRIMARY KEY,
		masterno NUMBER(10),
		n_title VARCHAR(50) NOT NULL,
		n_content VARCHAR(50) NOT NULL,
		rdate DATE NOT NULL,
  FOREIGN KEY (masterno) REFERENCES master (masterno)
);

/**********************************/
/* Table Name: 질문게시판 */
/**********************************/
CREATE TABLE q_board(
		q_boardno INT NOT NULL PRIMARY KEY,
		q_boardtitle VARCHAR(50) NOT NULL,
		q_boardcontent VARCHAR(500) NOT NULL,
		userno NUMERIC(10),
		memberno NUMERIC(10),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

/**********************************/
/* Table Name: 답변 */
/**********************************/
CREATE TABLE answer(
		answerno INT NOT NULL PRIMARY KEY,
		answercontent INT,
		masterno NUMBER(10),
		q_boardno INT,
  FOREIGN KEY (q_boardno) REFERENCES q_board (q_boardno),
  FOREIGN KEY (masterno) REFERENCES master (masterno)
);

/**********************************/
/* Table Name: 자유게시판 */
/**********************************/
CREATE TABLE f_board(
		title NUMBER(10) NOT NULL PRIMARY KEY,
		userno NUMERIC(10),
		f_content VARCHAR(500) NOT NULL,
		f_title VARCHAR(50),
		memberno NUMERIC(10),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

CREATE SEQUENCE recommend_seq
  START WITH 1         -- 시작 번호
  INCREMENT BY 1       -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2              -- 2번은 메모리에서만 계산
  NOCYCLE; 

/**********************************/
/* Table Name: 첨부 파일 */
/**********************************/
CREATE TABLE file(
		fileno NUMBER(10) NOT NULL PRIMARY KEY,
		title NUMBER(10),
		file VARCHAR(500),
		file_saved VARCHAR(500),
		file_preview VARCHAR(500),
		file_size INT,
  FOREIGN KEY (title) REFERENCES f_board (title)
);

/**********************************/
/* Table Name: 김세민 */
/**********************************/
CREATE TABLE 김세민(

);

/**********************************/
/* Table Name: 김예은 */
/**********************************/
CREATE TABLE 김예은(

);

/**********************************/
/* Table Name: 신영훈 */
/**********************************/
CREATE TABLE 신영훈(

);

/**********************************/
/* Table Name: 윤영진 */
/**********************************/
CREATE TABLE 윤영진(

);

/**********************************/
/* Table Name: 송해빈 */
/**********************************/
CREATE TABLE 송해빈(

);

/**********************************/
/* Table Name: 추천 */
/**********************************/
CREATE TABLE recommend(
		recommendno NUMBER(10) PRIMARY KEY,
		recommendrank NUMBER(2) NOT NULL,
		recommenddate DATE DEFAULT sysdate NOT NULL,
		memberno NUMBER(10),
		cosmetypeno NUMBER(10),
  FOREIGN KEY (memberno) REFERENCES member (memberno),
  FOREIGN KEY (cosmetypeno) REFERENCES cosmetype (cosmetypeno)
);
COMMIT;
select * from member;
select * from recommend;
recommend_seq.nextval;
select * from cosmetype;
delete from cosmetype;
insert into cosmetype values('1', '수분');
insert into cosmetype values('2', '진정');
insert into cosmetype values('3', '주름');
insert into cosmetype values('4', '미백');
insert into cosmetype values('5', '각질');
select * from recommend;
commit;

