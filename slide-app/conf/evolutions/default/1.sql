# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table page (
  page_id                   bigint auto_increment not null,
  duration                  integer,
  name                      varchar(255),
  url                       varchar(255),
  vc_id                     bigint,
  constraint pk_page primary key (page_id))
;

alter table page add constraint fk_page_vcInfo_1 foreign key (vc_id) references page (page_id) on delete restrict on update restrict;
create index ix_page_vcInfo_1 on page (vc_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists page;

SET REFERENTIAL_INTEGRITY TRUE;

