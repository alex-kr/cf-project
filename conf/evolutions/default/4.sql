# --- !Ups

alter table question add level bigint not null;

# --- !Downs

alter table question drop level;