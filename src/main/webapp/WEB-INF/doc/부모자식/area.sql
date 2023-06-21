DROP TABLE area;

CREATE TABLE area(
    areano    NUMBER(10)    NOT NULL, -- 지역 번호
    aname    VARCHAR(20)    NOT NULL, -- 지역명
    PRIMARY KEY (areano)
);

COMMENT ON TABLE area is '지역';
COMMENT ON COLUMN area.areano is '지역 번호';
COMMENT ON COLUMN area.aname is '지역명';

DROP SEQUENCE area_seq;

CREATE SEQUENCE area_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
  
  