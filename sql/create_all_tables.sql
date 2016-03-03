create table test_table
    (
       id int not null,
       name varchar2(200),
       text varchar2(100),
      primary key (id)
  );

  insert into TEST_TABLE (id, text) values (1, 'This is some text.');


create table TFolder
   (
     id int not null,
     name varchar2(200),
     parent_id int,
     primary key(id)
     );

create table TItem
( 
  id int not null,
  name varchar2(200),
  folder_id int,
  itemtype varchar2(200),
   primary key(id)
 );


 create table TItemProperty
( 
  id int not null,
  name varchar2(200),
  property_id int not null,
  propertyname varchar2(200),
  primary key(id)
 );


 create table TItemPropertyValue
( 
  item_id int not null,
  property_id int not null,
  propertyvalue varchar2(4000)
 );


 create table TOrder
 (
  id int not null,
  name varchar2(200),
  order_status varchar2(200),
  order_date timestamp,
  user_id int,
  primary key(id)
 );

create table TOrderHistory
(
 order_id int not null,
 item_id int not null
);


create table TUser
(
 id int not null,
 name varchar2(200),
 password varchar2(200),
 grouptype varchar2(200),
 order_id int,
 primary key(id)
);

drop table FOLDERTABLE;
drop table ITEMPROPERTYDESCRIPTIONTABLE;
drop table ITEMTABLE;
DROP table JOHNY_TABLE;
drop table ORDERCONTENTTABLE;
drop TABLE TEST_TABLE;
drop table USERTABLE;
drop TABLE ORDERTABLE;
drop TABLE ITEMPROPERTYTABLE;

INSERT into TFolder (id, NAME, PARENT_ID) VALUES (1,"test", null);
UPDATE TFolder VALUES (1,"test", null);


drop table TUser;
drop table TOrder;
drop table TItem;
DROP table TFolder;
drop table TOrderHistory;
drop TABLE TItemPropertyValue;
drop table TItemProperty;




 