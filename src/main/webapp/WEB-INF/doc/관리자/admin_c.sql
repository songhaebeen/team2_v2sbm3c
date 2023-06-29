/**********************************/
/* Table Name: 관리자 */
/**********************************/
DROP TABLE admin;
-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)
DROP TABLE admin CASCADE CONSTRAINTS;

CREATE TABLE admin(
    adminno    NUMBER(10)    NOT NULL,
    id         VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
    passwd     VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
    mname      VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    tel        VARCHAR(14)   NOT NULL,
    zipcode    VARCHAR(5)    NOT NULL,
    address1   VARCHAR(80)   NOT NULL,
    address2   VARCHAR(50)   NOT NULL,
    depart     VARCHAR(20)   NOT NULL,
    mdate      DATE          NOT NULL, -- 가입일    
    grade      NUMBER(2)     NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 비회원: 30~39, 정지 회원: 40~49, 탈퇴 회원: 99)    
    PRIMARY KEY (adminno)              -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE admin is '관리자';
COMMENT ON COLUMN admin.adminno is '관리자 번호';
COMMENT ON COLUMN admin.id is '아이디';
COMMENT ON COLUMN admin.PASSWD is '패스워드';
COMMENT ON COLUMN admin.MNAME is '성명';
COMMENT ON COLUMN admin.TEL is '전화번호';
COMMENT ON COLUMN admin.ZIPCODE is '우편번호';
COMMENT ON COLUMN admin.ADDRESS1 is '주소1';
COMMENT ON COLUMN admin.ADDRESS2 is '주소2';
COMMENT ON COLUMN admin.DEPART is '부서';
COMMENT ON COLUMN admin.MDATE is '가입일';
COMMENT ON COLUMN admin.GRADE is '등급';

DROP SEQUENCE admin_seq;

CREATE SEQUENCE admin_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

INSERT INTO admin(adminno, id, passwd, mname,tel, zipcode,
                                 address1, address2,depart, mdate, grade)
VALUES(admin_seq.nextval, 'admin1', '1234', '관리자1','000-0000-0000', '12345',
             '서울시 종로구', '관철동', '개발팀',sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname,tel, zipcode,
                                 address1, address2,depart, mdate, grade)
VALUES(admin_seq.nextval, 'admin2', '1234', '관리자2','000-0000-0000', '12345',
             '서울시 종로구', '관철동','인사팀', sysdate, 1);

INSERT INTO admin(adminno, id, passwd, mname,tel, zipcode,
                                 address1, address2,depart, mdate, grade)
VALUES(admin_seq.nextval, 'admin3', '1234', '관리자3','000-0000-0000', '12345',
             '서울시 종로구', '관철동','웹퍼블리셔팀', sysdate, 1);

commit;

SELECT adminno, id, passwd, mname,tel,zipcode,address1,address2, depart, mdate, grade FROM admin ORDER BY adminno ASC;
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1
         2 admin2               1234            관리자2              2022-10-06 11:47:56          1
         3 admin3               1234            관리자3              2022-10-06 11:47:56          1
         

   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

SELECT adminno, id, passwd, mname, mdate, grade 
FROM admin
WHERE id='admin1';
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

UPDATE admin
SET passwd='1234', mname='관리자1', mdate=sysdate, grade=1
WHERE ADMINNO=1;

COMMIT;
         
-- 로그인, 1: 로그인 성공, 0: 로그인 실패
SELECT COUNT(*) as cnt
FROM admin
WHERE id='admin1' AND passwd='1234'; 

-------------------------------------------------------------------------
-- AWS 관리자 설정
-------------------------------------------------------------------------
DELETE FROM admin;

INSERT INTO admin(adminno, id, passwd, mname, mdate, grade)
VALUES(admin_seq.nextval, 'admin', '69017000', 'Master 관리자', sysdate, 1);

commit;

SELECT adminno, id, passwd, mname, mdate, grade FROM admin ORDER BY adminno ASC;

   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         4 admin                69017000        Master 관리자        2023-04-07 12:17:38          1
         
---------------------------------
SELECT adminno, id, passwd, mname,mdate, grade
FROM member
ORDER BY memberno DESC;




