create database db_user default charset utf8mb4;
use db_user;
create table pe_user (
  id int unsigned auto_increment primary key ,
  username varchar(32) not null,
  password varchar(32) not null,
  realName varchar(32) not null,
  mobile varchar(32) null,
  email varchar(32) null
);