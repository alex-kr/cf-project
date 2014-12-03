# Initial db model generation
# --- !Ups

create sequence answer_record_seq;

create table answer_record (
  id                        bigint not null default nextval ('answer_record_seq'),
  user1_id                   bigint,
  question_id               bigint,
  choice_id                 bigint,
  correct                   boolean,
  constraint pk_answer_record primary key (id))
;

create sequence question_seq;

create table question (
  id                        bigint not null default nextval ('question_seq') ,
  question_text             varchar(255),
  rule_id                   bigint,
  level                     bigint,
  constraint pk_question primary key (id))
;

create sequence user1_seq;

create table "user1" (
  id                        bigint not null default nextval ('user1_seq'),
  fullname                  varchar(255),
  isAdmin                   boolean,
  constraint pk_user1 primary key (id))
;

create sequence choice_seq;


create table choice (
  id                        bigint not null default nextval ('choice_seq'),
  choice_text_id            bigint,
  question_id               bigint,
  correct                   boolean,
  constraint pk_choice primary key (id))
;

create sequence choice_text_seq;

create table choice_text (
  id                        bigint not null default nextval ('choice_text_seq'),
  text                      varchar(255) not null,
  constraint pk_choice_text primary key (id),
  constraint u_choice_text_t UNIQUE (text))
;

create sequence rule_seq;

create table rule (
  id                    bigint not null default nextval ('rule_seq') ,
  rule_text             varchar(255),
  topic_id              bigint,
  constraint pk_rule primary key (id))
;

create sequence topic_seq;

create table topic (
  id                     bigint not null default nextval ('topic_seq') ,
  topic_text             varchar(255),
  constraint pk_topic primary key (id))
;


alter table answer_record add constraint fk_answer_record_user1_1 foreign key (user1_id) references "user1" (id) on delete restrict on update restrict;
create index ix_answer_record_user1_1 on answer_record (user1_id);
alter table answer_record add constraint fk_answer_record_question_2 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_record_question_2 on answer_record (question_id);
alter table answer_record add constraint fk_answer_record_choice_3 foreign key (choice_id) references choice (id) on delete restrict on update restrict;
create index ix_answer_record_choice_3 on answer_record (choice_id);
alter table choice add constraint fk_choice_choice_text_4 foreign key (choice_text_id) references choice_text (id) on delete restrict on update restrict;
create index ix_choice_choice_text_4 on choice (choice_text_id);
alter table choice add constraint fk_choice_question_5 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_choice_question_5 on choice (question_id);
alter table question add constraint fk_question_rule_6 foreign key (rule_id) references rule (id) on delete restrict on update restrict;
create index ix_question_rule_6 on question (rule_id);
alter table rule add constraint fk_rule_topic_7 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_rule_topic_7 on rule (topic_id);



# --- !Downs

@@foreign_key_checks := 0;

drop table if exists answer_record;

drop table if exists question;

drop table if exists "user1";

drop table if exists choice;

drop table if exists choice_text;

drop table if exists rule;

drop table if exists topic;

@@foreign_key_checks := 1;
