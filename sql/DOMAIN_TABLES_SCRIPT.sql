CREATE TABLE LOGIN_USER (
    id number not null,
    username varchar2(45) not null,
    password varchar2(200) not null,
    FULL_NAME VARCHAR2(200) NOT NULL,
    active varchar2(1) not null,
    CURSO_ID NUMBER NOT NULL,
    primary key(id),
    FOREIGN KEY (CURSO_ID) REFERENCES CURSO(ID);
);

CREATE SEQUENCE LOGIN_USER_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000000
    CYCLE
    CACHE 2;

CREATE TABLE CURSO (
   id number not null,
   curso_desc varchar2(100) not null,
   active varchar2(1) not null,
   primary key(id)
);

CREATE SEQUENCE CURSO_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000000
    CYCLE
    CACHE 2;

CREATE TABLE DOMAIN_SALT (
    id number not null,
    salt VARCHAR2(100),
    salt_date DATE,
    primary key(id)
);

CREATE SEQUENCE DOMAIN_SALT_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000000
    CYCLE
    CACHE 2;