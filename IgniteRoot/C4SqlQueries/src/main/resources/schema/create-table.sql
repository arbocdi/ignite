create table dept(
  deptno integer NOT NULL PRIMARY KEY,
  dname  text,
  loc    text
);

create table emp(
  empno    integer NOT NULL PRIMARY KEY,
  ename    text,
  job      text,
  mgr      integer,
  hiredate date,
  sal      numeric(9,2),
  comm     numeric(9,2),
  deptno   integer references dept (deptno) ON UPDATE CASCADE ON DELETE CASCADE
  
);