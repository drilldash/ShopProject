create table test_table
    (
       id int not null,
       text varchar2(1000),
      primary key (id)
  );

  insert into TEST_TABLE (id, text) values (1, 'This is some text.');


create table FolderTable
   (
     id int not null,
     parent_id int,
     primary key(id)
     );

create table ItemTable
( 
  id int not null,
  folder_id int,
  itemtype varchar2(4000),
   primary key(id)
 );


 create table ItemPropertyDescriptionTable
( 
  item_id int not null,
  property_id int not null,
  propertyvalue varchar2(4000)
 );


 create table OrderTable
 (
  id int not null,
  order_status varchar2(500),
  orderdate timestamp,
  user_id int,
  primary key(id)
 );

create table OrderContentTable
(
 order_id int not null,
 item_id int not null
);


create table UserTable
(
 id int not null,
 password varchar2(2000),
 grouptype varchar2(1000),
 order_id int,
 primary key(id)
);
 