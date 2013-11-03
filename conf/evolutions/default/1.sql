# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table request (
  title                     varchar(255) not null,
  description               varchar(255),
  request_date              varchar(255),
  created_at                timestamp,
  updated_at                timestamp,
  constraint pk_request primary key (title))
;

create sequence request_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists request;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists request_seq;

