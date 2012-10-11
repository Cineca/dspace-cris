alter table model_jdyna_nestedobject_typo add column inline bool not null;
alter table model_project_jdyna_nestedobject_typo add column inline bool not null;
alter table model_rp_jdyna_nestedobject_typo add column inline bool not null;
create index model_rp_jdyna_nestedobject_prop_parent_id on model_rp_jdyna_nestedobject_prop (parent_id);
create index model_rp_jdyna_prop_parent_id on model_rp_jdyna_prop (parent_id);
