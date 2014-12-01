# --- !Ups

create table rule (
  id                    bigint not null auto_increment ,
  rule_text             varchar(255),
  constraint pk_question primary key (id))
;

# --- !Downs

SET @@foreign_key_checks = 0;

drop table if exists rule;

SET @@foreign_key_checks = 1;
