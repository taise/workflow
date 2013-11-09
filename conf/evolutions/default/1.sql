# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table request (
  id                        bigint not null,
  title                     varchar(255),
  description               varchar(255),
  target_date               varchar(255),
  created_at                timestamp,
  updated_at                timestamp,
  constraint pk_request primary key (id))
;

create table user (
  id                        bigint not null,
  user_id                   varchar(255),
  name                      varchar(255),
  company                   varchar(255),
  division                  varchar(255),
  post                      varchar(255),
  created_at                timestamp,
  updated_at                timestamp,
  constraint pk_user primary key (id))
;

create sequence request_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists request;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists request_seq;

drop sequence if exists user_seq;

