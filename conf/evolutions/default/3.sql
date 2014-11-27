# --- !Ups

alter table question add rule_id bigint;
alter table question add constraint fk_question_rule_6 foreign key (rule_id) references rule (id) on delete restrict on update restrict;
create index ix_question_rule_6 on question (rule_id);

# --- !Downs

alter table question drop foreign key fk_question_rule_6;
drop index question_rule_6 on question;
alter table question drop rule_id;
