# Initial db model generation
# --- !Ups

create table AnswerRecord (
  id                        bigint not null auto_increment,
  user_id                   bigint,
  question_id               bigint,
  choice_id                 bigint,
  correct                   boolean,
  constraint pk_AnswerRecord primary key (id))
;

create table question (
  id                        bigint not null auto_increment ,
  question_text             varchar(255),
  constraint pk_question primary key (id))
;

create table user (
  id                        bigint not null auto_increment,
  fullname                  varchar(255),
  isAdmin                   boolean,
  constraint pk_user primary key (id))
;

create table choice (
  id                        bigint not null auto_increment,
  choice_text_id            bigint,
  question_id               bigint,
  correct                   boolean,
  constraint pk_choice primary key (id))
;

create table choice_text (
  id                        bigint not null auto_increment,
  text                      varchar(255) not null,
  constraint pk_choice_text primary key (id),
  constraint u_choice_text_t UNIQUE (text))
;



alter table AnswerRecord add constraint fk_AnswerRecord_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_AnswerRecord_user_1 on AnswerRecord (user_id);
alter table AnswerRecord add constraint fk_AnswerRecord_question_2 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_AnswerRecord_question_2 on AnswerRecord (question_id);
alter table AnswerRecord add constraint fk_AnswerRecord_choice_3 foreign key (choice_id) references choice (id) on delete restrict on update restrict;
create index ix_AnswerRecord_choice_3 on AnswerRecord (choice_id);
alter table choice add constraint fk_choice_choice_text_4 foreign key (choice_text_id) references choice_text (id) on delete restrict on update restrict;
create index ix_choice_choice_text_4 on choice (choice_text_id);
alter table choice add constraint fk_choice_question_5 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_choice_question_5 on choice (question_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists AnswerRecord;

drop table if exists question;

drop table if exists user;

drop table if exists choice;

drop table if exists choice_text;

SET REFERENTIAL_INTEGRITY TRUE;

