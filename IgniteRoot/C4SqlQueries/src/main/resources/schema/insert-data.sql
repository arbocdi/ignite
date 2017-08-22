
insert into dept
values(10, 'ACCOUNTING', 'NEW YORK');
insert into dept
values(20, 'RESEARCH', 'DALLAS');
insert into dept
values(30, 'SALES', 'CHICAGO');
insert into dept
values(40, 'OPERATIONS', 'BOSTON');

insert into emp
values(
  7839, 'KING', 'PRESIDENT', null,
  TO_DATE('17-11-1981  ', 'DD-MM-YYYY   '),
  5000, null, 10
);
insert into emp
values(
  7698, 'BLAKE', 'MANAGER', 7839,
  TO_DATE('1-5-1981  ', 'DD-MM-YYYY   '),
  2850, null, 30
);
insert into emp
values(
  7782, 'CLARK', 'MANAGER', 7839,
  TO_DATE('9-6-1981  ', 'DD-MM-YYYY   '),
  2450, null, 10
);
insert into emp
values(
  7566, 'JONES', 'MANAGER', 7839,
  TO_DATE('2-4-1981  ', 'DD-MM-YYYY   '),
  2975, null, 20
);
insert into emp
values(
  7788, 'SCOTT', 'ANALYST', 7566,
  TO_DATE('13-7-1987  ', 'DD-MM-YYYY   '),
  3000, null, 20
);
insert into emp
values(
  7902, 'FORD', 'ANALYST', 7566,
  TO_DATE('3-12-1981  ', 'DD-MM-YYYY   '),
  3000, null, 20
);
insert into emp
values(
  7369, 'SMITH', 'CLERK', 7902,
  TO_DATE('17-12-1980  ', 'DD-MM-YYYY   '),
  800, null, 20
);
insert into emp
values(
  7499, 'ALLEN', 'SALESMAN', 7698,
  TO_DATE('20-2-1981  ', 'DD-MM-YYYY   '),
  1600, 300, 30
);
insert into emp
values(
  7521, 'WARD', 'SALESMAN', 7698,
  TO_DATE('22-2-1981  ', 'DD-MM-YYYY   '),
  1250, 500, 30
);
insert into emp
values(
  7654, 'MARTIN', 'SALESMAN', 7698,
  TO_DATE('28-9-1981  ', 'DD-MM-YYYY   '),
  1250, 1400, 30
);
insert into emp
values(
  7844, 'TURNER', 'SALESMAN', 7698,
  TO_DATE('8-9-1981  ', 'DD-MM-YYYY   '),
  1500, 0, 30
);
insert into emp
values(
  7876, 'ADAMS', 'CLERK', 7788,
  TO_DATE('13-7-1987  ', 'DD-MM-YYYY   '),
  1100, null, 20
);
insert into emp
values(
  7900, 'JAMES', 'CLERK', 7698,
  TO_DATE('3-12-1981  ', 'DD-MM-YYYY   '),
  950, null, 30
);
insert into emp
values(
  7934, 'MILLER', 'CLERK', 7782,
  TO_DATE('23-1-1982  ', 'DD-MM-YYYY   '),
  1300, null, 10
);