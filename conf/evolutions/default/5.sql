# --- !Ups

create table topic (
  id                     bigint not null auto_increment ,
  topic_text             varchar(255),
  constraint pk_rule primary key (id))
;

# --- !Downs

SET @@foreign_key_checks = 0;

drop table if exists topic;

SET @@foreign_key_checks = 1;