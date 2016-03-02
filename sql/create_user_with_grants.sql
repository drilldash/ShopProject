create tablespace shop_tabspace datafile 'shop_tabspace.dat' size 10M autoextend on;
create temporary tablespace shop_tabspace_temp tempfile 'shop_tabspace_temp.dat' size 5M autoextend on;
create user verlorener identified by verlorener default tablespace shop_tabspace temporary tablespace shop_tabspace_temp;
grant create session to verlorener;
grant create table to verlorener;
grant unlimited tablespace to verlorener;