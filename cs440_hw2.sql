--     Bhargav Sai Krishna, Ravuri
--     CS 440
--     Assignment 2
--     January 28, 2015


set echo on
alter table sp drop constraint sp_s#_fk;
alter table sp drop constraint sp_p#_fk;
alter table emp drop constraint emp_deptno_fk;
alter table emp drop constraint emp_mgr_fk; 
alter table dept drop constraint dept_pk;
alter table dept drop constraint dept_uq;
alter table dept drop constraint dept_nn;
alter table emp drop constraint emp_empno_pk;
alter table emp drop constraint emp_ename_uq;
alter table emp drop constraint emp_ename_nn;
--alter table emp drop constraint emp_mgr_fk;
--alter table emp drop constraint emp_deptno_fk;
alter table emp drop constraint emp_sal_chk;
alter table s  drop constraint S_s#_pk;
alter table s drop constraint s_sname_nn;
alter table s drop constraint s_sname_uq;
alter table p drop constraint p_p#_pk;
alter table p drop constraint p_pname_nn;
alter table p drop constraint p_pname_uq;
alter table sp drop constraint sp_s#_p#_pk;
alter table sp drop constraint sp_qty_chk;
drop index deptno_index;
update emp set hiredate=add_months(hiredate,12) where to_char(hiredate,'yyyy')=2012;


-- Problem 1a
     
   alter table dept modify deptno constraint dept_pk primary key deferrable initially immediate;  

-- Problem 1b
   
   alter table dept modify dname constraint dept_uq unique deferrable initially immediate constraint dept_nn not null deferrable initially immediate;
         
-- Problem 2a

   alter table emp modify empno constraint emp_empno_pk primary key deferrable initially immediate;     

-- Problem 2b

   alter table emp modify ename constraint emp_ename_uq unique deferrable initially immediate constraint emp_ename_nn not null deferrable initially immediate;

-- Problem 2c

   alter table emp modify mgr constraint emp_mgr_fk references emp(empno) deferrable initially immediate;

-- Problem 2d
  
   alter table emp modify deptno constraint emp_deptno_fk references dept(deptno) deferrable initially immediate;

-- Problem 2e

   alter table emp modify sal constraint emp_sal_chk check(sal between 500 and 10000) deferrable initially immediate;

-- Problem 3a

   alter table S modify S# constraint S_s#_pk primary key deferrable initially immediate;   

-- Problem 3b
    
   alter table S modify sname constraint s_sname_nn not null deferrable initially immediate constraint s_sname_uq unique deferrable initially immediate;

-- Problem 4a

   alter table p modify p# constraint p_p#_pk primary key deferrable initially immediate;


-- Problem 4b

   alter table p modify pname constraint p_pname_nn not null deferrable initially immediate constraint p_pname_uq unique deferrable initially immediate;

-- Problem 5a
  
   alter table sp add constraint sp_s#_p#_pk primary key (s#,p#) deferrable initially immediate;

-- Problem 5b

   alter table sp modify qty constraint sp_qty_chk check(qty is null or qty>=0) deferrable initially immediate;

-- Problem 5c

   alter table sp modify s# constraint sp_s#_fk references  s(s#) deferrable initially immediate;
   alter table sp modify p# constraint sp_p#_fk references  p(p#) deferrable initially immediate;

-- Problem 6

   create index deptno_index on emp(deptno);


-- Problem 7

   update emp set hiredate=add_months(hiredate,-12) where to_char(hiredate,'yyyy')=2013;
   
commit;

-- Problem 8

  select table_name,index_name from user_indexes;   

-- Problem 9

  select table_name,constraint_name from user_constraints;    

