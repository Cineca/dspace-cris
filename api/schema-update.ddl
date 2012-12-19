alter table cris_organizationunit add column crisID varchar(255) unique;
alter table cris_project add column crisID varchar(255) unique;
alter table cris_researcherpage add column crisID varchar(255) unique;
alter table jdyna_containables add constraint FKB80C84B2BBDACA2B foreign key (propertiesdefinitionou_fk) references cris_ou_propertiesdefinition;
