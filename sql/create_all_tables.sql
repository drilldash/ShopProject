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
  order_date varchar2(200),
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

drop table TUser;
drop table TOrder;
drop table TItem;
DROP table TFolder;
drop table TOrderHistory;
drop TABLE TItemPropertyValue;
drop table TItemProperty;


INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (1, 'a1', 'pa1', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (2, 'a2', 'pa2', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (3, 'a3', 'pa3', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (4, 'a4', 'pa4', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (5, 'a5', 'pa5', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (6, 'admin', 'admin', 'ADMIN');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (7, 'u1', 'p1', 'USER');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (8, 'u2', 'p2', 'USER');
INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE) VALUES (9, 'u3', 'p3', 'USER');

 