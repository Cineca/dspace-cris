alter table jdyna_values add column rpvalue int4;
alter table jdyna_values add column projectvalue int4;
alter table jdyna_values add column ouvalue int4;
alter table jdyna_values add constraint FK51AA118FE5220DBD foreign key (ouvalue) references cris_organizationunit;
alter table jdyna_values add constraint FK51AA118F34F77D96 foreign key (projectvalue) references cris_project;
alter table jdyna_values add constraint FK51AA118F3B074C05 foreign key (rpvalue) references cris_researcherpage;
