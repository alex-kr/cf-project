# --- !Ups

alter table rule add topic_id bigint;
alter table rule add constraint fk_rule_topic_7 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_rule_topic_7 on rule (topic_id);

# --- !Downs

alter table rule drop foreign key fk_rule_topic_7;
drop index ix_rule_topic_7 on rule;
alter table rule drop topic_id;
