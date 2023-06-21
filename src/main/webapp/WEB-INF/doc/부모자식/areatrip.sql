CREATE TABLE areatrip(
    areatripno      NUMBER(10)    NOT NULL,--비행기 번호
    areano    NUMBER(10)    NOT NULL,--비행기 회원 번호
    rdate         VARCHAR(20)   NOT NULL, -- 좌석
    PRIMARY KEY (areatripno),              -- 한번 등록된 값은 중복 안됨
     FOREIGN KEY (areano) REFERENCES hmem (areano)
);

COMMENT ON TABLE areatrip is '헬스 출석';
COMMENT ON COLUMN hday.areatripno is '헬스 출석번호';
COMMENT ON COLUMN hday.areano  is '헬스 회원 번호';
COMMENT ON COLUMN hday.rdate  is '출석일';