DROP TABLE contentsco CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE contentsco;

CREATE TABLE contentsco(
        contentscono                            NUMBER(10)         NOT NULL         PRIMARY KEY,
        adminno                              NUMBER(10)     NOT NULL , -- FK
        catecono                                NUMBER(10)         NOT NULL , -- FK
        title                                 VARCHAR2(200)         NOT NULL,
        content                               CLOB                  NOT NULL,
        recom                                 NUMBER(7)         DEFAULT 0         NOT NULL,
        cnt                                   NUMBER(7)         DEFAULT 0         NOT NULL,
        replycnt                              NUMBER(7)         DEFAULT 0         NOT NULL,
        passwd                                VARCHAR2(15)         NOT NULL,
        word                                  VARCHAR2(100)         NULL ,
        rdate                                 DATE               NOT NULL,
        file1                                   VARCHAR(100)          NULL,  -- 원본 파일명 image
        file1saved                            VARCHAR(100)          NULL,  -- 저장된 파일명, image
        thumb1                              VARCHAR(100)          NULL,   -- preview image
        size1                                 NUMBER(10)      DEFAULT 0 NULL,  -- 파일 사이즈
        map                                   VARCHAR2(1000)            NULL,
        youtube                               VARCHAR2(1000)            NULL,
        FOREIGN KEY (adminno) REFERENCES admin (adminno),
        FOREIGN KEY (catecono) REFERENCES cateco (catecono)
);

COMMENT ON TABLE contentsco is '컨텐츠 - 순례길';
COMMENT ON COLUMN contentsco.contentscono is '컨텐츠 번호';
COMMENT ON COLUMN contentsco.adminno is '관리자 번호';
COMMENT ON COLUMN contentsco.catecono is '카테고리 번호';
COMMENT ON COLUMN contentsco.title is '제목';
COMMENT ON COLUMN contentsco.content is '내용';
COMMENT ON COLUMN contentsco.recom is '추천수';
COMMENT ON COLUMN contentsco.cnt is '조회수';
COMMENT ON COLUMN contentsco.replycnt is '댓글수';
COMMENT ON COLUMN contentsco.passwd is '패스워드';
COMMENT ON COLUMN contentsco.word is '검색어';
COMMENT ON COLUMN contentsco.rdate is '등록일';
COMMENT ON COLUMN contentsco.file1 is '메인 이미지';
COMMENT ON COLUMN contentsco.file1saved is '실제 저장된 메인 이미지';
COMMENT ON COLUMN contentsco.thumb1 is '메인 이미지 Preview';
COMMENT ON COLUMN contentsco.size1 is '메인 이미지 크기';
COMMENT ON COLUMN contentsco.map is '지도';
COMMENT ON COLUMN contentsco.youtube is 'Youtube 영상';

DROP SEQUENCE contentsco_seq;

CREATE SEQUENCE contentsco_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO contentsco(contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(contentsco_seq.nextval, 1, 1, '청계천 매화 거리', '제기동역에서 가까움 명품 산책로', 0, 0, 0, '123',
       '산책', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);

-- 유형 1 전체 목록
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1
FROM contentsco
ORDER BY contentscono DESC;

-- 유형 2 카테고리별 목록
INSERT INTO contentsco(contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(contentsco_seq.nextval, 1, 2, '대행사', '흙수저와 금수저의 성공 스토리', 0, 0, 0, '123',
       '드라마,K드라마,넷플릭스', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);
            
INSERT INTO contentsco(contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(contentsco_seq.nextval, 1, 2, '더글로리', '학폭의 결말', 0, 0, 0, '123',
       '드라마,K드라마,넷플릭스', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);

INSERT INTO contentsco(contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(contentsco_seq.nextval, 1, 2, '더글로리', '학폭의 결말', 0, 0, 0, '123',
       '드라마,K드라마,넷플릭스', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);

COMMIT;

-- 1번 catecono 만 출력
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=1
ORDER BY contentscono DESC;

-- 2번 catecono 만 출력
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=2
ORDER BY contentscono ASC;

-- 3번 catecono 만 출력
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=3
ORDER BY contentscono ASC;

-- 모든 레코드 삭제
DELETE FROM contentsco;
commit;

-- 삭제
DELETE FROM contentsco
WHERE contentscono = 25;
commit;

DELETE FROM contentsco
WHERE catecono=12 AND contentscono <= 41;

commit;


-- ----------------------------------------------------------------------------------------------------
-- 검색, catecono별 검색 목록
-- ----------------------------------------------------------------------------------------------------
-- 모든글
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, word, rdate,
       file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
ORDER BY contentscono ASC;

-- 카테고리별 목록
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, word, rdate,
       file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=2
ORDER BY contentscono ASC;

-- 1) 검색
-- ① catecono별 검색 목록
-- word 컬럼의 존재 이유: 검색 정확도를 높이기 위하여 중요 단어를 명시
-- 글에 'swiss'라는 단어만 등장하면 한글로 '스위스'는 검색 안됨.
-- 이런 문제를 방지하기위해 'swiss,스위스,스의스,수의스,유럽' 검색어가 들어간 word 컬럼을 추가함.
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=8 AND word LIKE '%부대찌게%'
ORDER BY contentscono DESC;

-- title, content, word column search
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=8 AND (title LIKE '%부대찌게%' OR content LIKE '%부대찌게%' OR word LIKE '%부대찌게%')
ORDER BY contentscono DESC;

-- ② 검색 레코드 갯수
-- 전체 레코드 갯수, 집계 함수
SELECT COUNT(*)
FROM contentsco
WHERE catecono=5;

  COUNT(*)  <- 컬럼명
----------
         5
         
SELECT COUNT(*) as cnt -- 함수 사용시는 컬럼 별명을 선언하는 것을 권장
FROM contentsco
WHERE catecono=1;

       CNT <- 컬럼명
----------
         5

-- catecono 별 검색된 레코드 갯수
SELECT COUNT(*) as cnt
FROM contentsco
WHERE catecono=8 AND word LIKE '%부대찌게%';

SELECT COUNT(*) as cnt
FROM contentsco
WHERE catecono=8 AND (title LIKE '%부대찌게%' OR content LIKE '%부대찌게%' OR word LIKE '%부대찌게%');

-- SUBSTR(컬럼명, 시작 index(1부터 시작), 길이), 부분 문자열 추출
SELECT contentscono, SUBSTR(title, 1, 4) as title
FROM contentsco
WHERE catecono=8 AND (content LIKE '%부대%');

-- SQL은 대소문자를 구분하지 않으나 WHERE문에 명시하는 값은 대소문자를 구분하여 검색
SELECT contentscono, title, word
FROM contentsco
WHERE catecono=8 AND (word LIKE '%FOOD%');

SELECT contentscono, title, word
FROM contentsco
WHERE catecono=8 AND (word LIKE '%food%'); 

SELECT contentscono, title, word
FROM contentsco
WHERE catecono=8 AND (LOWER(word) LIKE '%food%'); -- 대소문자를 일치 시켜서 검색

SELECT contentscono, title, word
FROM contentsco
WHERE catecono=8 AND (UPPER(word) LIKE '%' || UPPER('FOOD') || '%'); -- 대소문자를 일치 시켜서 검색 ★

SELECT contentscono, title, word
FROM contentsco
WHERE catecono=8 AND (LOWER(word) LIKE '%' || LOWER('Food') || '%'); -- 대소문자를 일치 시켜서 검색

SELECT contentscono || '. ' || title || ' 태그: ' || word as title -- 컬럼의 결합, ||
FROM contentsco
WHERE catecono=8 AND (LOWER(word) LIKE '%' || LOWER('Food') || '%'); -- 대소문자를 일치 시켜서 검색


SELECT UPPER('한글') FROM dual; -- dual: 오라클에서 SQL 형식을 맞추기위한 시스템 테이블

-- ----------------------------------------------------------------------------------------------------
-- 검색 + 페이징 + 메인 이미지
-- ----------------------------------------------------------------------------------------------------
-- step 1
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE catecono=1 AND (title LIKE '%단풍%' OR content LIKE '%단풍%' OR word LIKE '%단풍%')
ORDER BY contentscono DESC;

-- step 2
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, rownum as r
FROM (
          SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                     file1, file1saved, thumb1, size1, map, youtube
          FROM contentsco
          WHERE catecono=1 AND (title LIKE '%단풍%' OR content LIKE '%단풍%' OR word LIKE '%단풍%')
          ORDER BY contentscono DESC
);

-- step 3, 1 page
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM contentsco
                     WHERE catecono=1 AND (title LIKE '%단풍%' OR content LIKE '%단풍%' OR word LIKE '%단풍%')
                     ORDER BY contentscono DESC
           )          
)
WHERE r >= 1 AND r <= 3;

-- step 3, 2 page
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM contentsco
                     WHERE catecono=1 AND (title LIKE '%단풍%' OR content LIKE '%단풍%' OR word LIKE '%단풍%')
                     ORDER BY contentscono DESC
           )          
)
WHERE r >= 4 AND r <= 6;

-- 대소문자를 처리하는 페이징 쿼리
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM contentsco
                     WHERE catecono=1 AND (UPPER(title) LIKE '%' || UPPER('단풍') || '%' 
                                         OR UPPER(content) LIKE '%' || UPPER('단풍') || '%' 
                                         OR UPPER(word) LIKE '%' || UPPER('단풍') || '%')
                     ORDER BY contentscono DESC
           )          
)
WHERE r >= 1 AND r <= 3;

-- ----------------------------------------------------------------------------
-- 조회
-- ----------------------------------------------------------------------------
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
WHERE contentscono = 1;

-- ----------------------------------------------------------------------------
-- 다음 지도, MAP, 먼저 레코드가 등록되어 있어야함.
-- map                                   VARCHAR2(1000)         NULL ,
-- ----------------------------------------------------------------------------
-- MAP 등록/수정
UPDATE contentsco SET map='카페산 지도 스크립트' WHERE contentscono=1;

-- MAP 삭제
UPDATE contentsco SET map='' WHERE contentscono=1;

commit;

-- ----------------------------------------------------------------------------
-- Youtube, 먼저 레코드가 등록되어 있어야함.
-- youtube                                   VARCHAR2(1000)         NULL ,
-- ----------------------------------------------------------------------------
-- youtube 등록/수정
UPDATE contentsco SET youtube='Youtube 스크립트' WHERE contentscono=13;

-- youtube 삭제
UPDATE contentsco SET youtube='' WHERE contentscono=1;

commit;

-- 패스워드 검사, id="password_check"
SELECT COUNT(*) as cnt 
FROM contentsco
WHERE contentscono=1 AND passwd='123';

-- 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE contentsco
SET title='기차를 타고', content='계획없이 여행 출발',  word='나,기차,생각' 
WHERE contentscono = 2;

-- ERROR, " 사용 에러
UPDATE contentsco
SET title='기차를 타고', content="계획없이 '여행' 출발",  word='나,기차,생각'
WHERE contentscono = 1;

-- ERROR, \' 에러
UPDATE contentsco
SET title='기차를 타고', content='계획없이 \'여행\' 출발',  word='나,기차,생각'
WHERE contentscono = 1;

-- SUCCESS, '' 한번 ' 출력됨.
UPDATE contentsco
SET title='기차를 타고', content='계획없이 ''여행'' 출발',  word='나,기차,생각'
WHERE contentscono = 1;

-- SUCCESS
UPDATE contentsco
SET title='기차를 타고', content='계획없이 "여행" 출발',  word='나,기차,생각'
WHERE contentscono = 1;

commit;

-- 파일 수정
UPDATE contentsco
SET file1='train.jpg', file1saved='train.jpg', thumb1='train_t.jpg', size1=5000
WHERE contentscono = 1;

-- 삭제
DELETE FROM contentsco
WHERE contentscono = 42;

commit;

DELETE FROM contentsco
WHERE contentscono >= 7;

commit;

-- 추천
UPDATE contentsco
SET recom = recom + 1
WHERE contentscono = 1;

-- catecono FK 특정 그룹에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM contentsco 
WHERE catecono=1;

-- adminno FK 특정 관리자에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM contentsco 
WHERE adminno=1;

-- catecono FK 특정 그룹에 속한 레코드 모두 삭제
DELETE FROM contentsco
WHERE catecono=1;

-- adminno FK 특정 관리자에 속한 레코드 모두 삭제
DELETE FROM contentsco
WHERE adminno=1;

commit;

-- 다수의 카테고리에 속한 레코드 갯수 산출: IN
SELECT COUNT(*) as cnt
FROM contentsco
WHERE catecono IN(1,2,3);

-- 다수의 카테고리에 속한 레코드 모두 삭제: IN
SELECT contentscono, adminno, catecono, title
FROM contentsco
WHERE catecono IN(1,2,3);

contentscono    ADMINNO     catecono TITLE                                                                                                                                                                                                                                                                                                       
---------- ---------- ---------- ------------------------
         3             1                   1           인터스텔라                                                                                                                                                                                                                                                                                                  
         4             1                   2           드라마                                                                                                                                                                                                                                                                                                      
         5             1                   3           컨저링                                                                                                                                                                                                                                                                                                      
         6             1                   1           마션       
         
SELECT contentscono, adminno, catecono, title
FROM contentsco
WHERE catecono IN('1','2','3');

contentscono    ADMINNO     catecono TITLE                                                                                                                                                                                                                                                                                                       
---------- ---------- ---------- ------------------------
         3             1                   1           인터스텔라                                                                                                                                                                                                                                                                                                  
         4             1                   2           드라마                                                                                                                                                                                                                                                                                                      
         5             1                   3           컨저링                                                                                                                                                                                                                                                                                                      
         6             1                   1           마션       

-- ----------------------------------------------------------------------------------------------------
-- cate + contents INNER JOIN
-- ----------------------------------------------------------------------------------------------------
-- 모든글
SELECT c.name,
       t.contentscono, t.adminno, t.catecono, t.title, t.content, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube
FROM cateco c, contentsco t
WHERE c.catecono = t.catecono
ORDER BY t.contentscono DESC;

-- contents, admin INNER JOIN
SELECT t.contentscono, t.adminno, t.catecono, t.title, t.content, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube,
       a.mname
FROM admin a, contentsco t
WHERE a.adminno = t.adminno
ORDER BY t.contentscono DESC;

SELECT t.contentscono, t.adminno, t.catecono, t.title, t.content, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube,
       a.mname
FROM admin a INNER JOIN contentsco t ON a.adminno = t.adminno
ORDER BY t.contentscono DESC;

-- ----------------------------------------------------------------------------------------------------
-- View + paging
-- ----------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW vcontents
AS
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, word, rdate,
        file1, file1saved, thumb1, size1, map, youtube
FROM contentsco
ORDER BY contentscono DESC;
                     
-- 1 page
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
       file1, file1saved, thumb1, size1, map, youtube, r
FROM (
     SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
            file1, file1saved, thumb1, size1, map, youtube, rownum as r
     FROM vcontents -- View
     WHERE catecono=14 AND (title LIKE '%야경%' OR content LIKE '%야경%' OR word LIKE '%야경%')
)
WHERE r >= 1 AND r <= 3;

-- 2 page
SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
       file1, file1saved, thumb1, size1, map, youtube, r
FROM (
     SELECT contentscono, adminno, catecono, title, content, recom, cnt, replycnt, rdate,
            file1, file1saved, thumb1, size1, map, youtube, rownum as r
     FROM vcontents -- View
     WHERE catecono=14 AND (title LIKE '%야경%' OR content LIKE '%야경%' OR word LIKE '%야경%')
)
WHERE r >= 4 AND r <= 6;
