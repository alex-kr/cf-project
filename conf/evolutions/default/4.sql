# --- !Ups

alter table question add level bigint;

# --- !Downs

alter table question drop level;