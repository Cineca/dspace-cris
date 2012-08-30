alter table RPSubscription drop constraint FK1E4A7BED3064CE;
alter table StatSubscription drop constraint FKA04119B1ED3064CE;
alter table jdyna_containables drop constraint FKB80C84B2CE9E17A;
alter table jdyna_containables drop constraint FKB80C84B2F4B61038;
alter table jdyna_values_model_grant_jdyna_prop drop constraint FK34AFD5191CC700F5;
alter table jdyna_values_model_grant_jdyna_prop drop constraint FK34AFD519926DFEF;
alter table jdyna_values_model_rp_jdyna_prop drop constraint FK6C6EB66BEB64DBE7;
alter table jdyna_values_model_rp_jdyna_prop drop constraint FK6C6EB66B1D8241C1;
alter table model_grant_investigator drop constraint FK8FBFD2C2BEC07DD8;
alter table model_grant_investigator drop constraint FK8FBFD2C24E95E7B4;
alter table model_grant_jdyna_prop drop constraint FK5DD6586973EDB8A0;
alter table model_grant_jdyna_prop drop constraint FK5DD658698E0C674;
alter table model_grant_jdyna_prop drop constraint FKC8A841F5E52079D75dd65869;
alter table model_grant_jdyna_prop drop constraint FK5DD658696AEC32C9;
alter table model_grant_jdyna_widgetcombo2subtipprop drop constraint FKF4DA3D6C65234D52;
alter table model_grant_jdyna_widgetcombo2subtipprop drop constraint FKF4DA3D6C5CB233CB;
alter table model_jdyna_box2containable drop constraint FKD0AF5FA45BA7E0EB;
alter table model_jdyna_box2containable drop constraint FKD0AF5FA4C20F2E09;
alter table model_jdyna_boxgrant2containablegrant drop constraint FK9949552C95630636;
alter table model_jdyna_boxgrant2containablegrant drop constraint FK9949552CC20F2E09;
alter table model_jdyna_edittab drop constraint FK6CD4C7E87FE0C82A;
alter table model_jdyna_edittab2box drop constraint FKE3F6CC41A26AFB4B;
alter table model_jdyna_edittab2box drop constraint FKE3F6CC4154E7AA67;
alter table model_jdyna_edittabgrant drop constraint FK9DD4AA34186781DE;
alter table model_jdyna_edittabgrant2boxgrant drop constraint FK90C068AF78A9B6EF;
alter table model_jdyna_edittabgrant2boxgrant drop constraint FK90C068AFD2F41F47;
alter table model_jdyna_tab2box drop constraint FK80B2D00B54E7AA67;
alter table model_jdyna_tab2box drop constraint FK80B2D00BB39D6AB;
alter table model_jdyna_tabgrant2boxgrant drop constraint FK9D09B7F978A9B6EF;
alter table model_jdyna_tabgrant2boxgrant drop constraint FK9D09B7F937DD8A2;
alter table model_researcher_grant drop constraint FKCED62FFB4E95E7B4;
alter table model_researcher_page drop constraint FK1F761BD0A04339DD;
alter table model_researcher_page_interests drop constraint FKA1D7D0DA182595FC;
alter table model_researcher_page_media drop constraint FK1A91D2B5182595FC;
alter table model_researcher_page_rejectItems drop constraint FK63BB3172182595FC;
alter table model_researcher_page_spokenLanguagesEN drop constraint FK42286293182595FC;
alter table model_researcher_page_spokenLanguagesZH drop constraint FK42286518182595FC;
alter table model_researcher_page_title drop constraint FK1AF68529182595FC;
alter table model_researcher_page_variants drop constraint FKDACE601D182595FC;
alter table model_researcher_page_writtenLanguagesEN drop constraint FK4DAD813C182595FC;
alter table model_researcher_page_writtenLanguagesZH drop constraint FK4DAD83C1182595FC;
alter table model_rp_jdyna_prop drop constraint FK6CF7751B4A70B892;
alter table model_rp_jdyna_prop drop constraint FK6CF7751BD77EA166;
alter table model_rp_jdyna_prop drop constraint FKC8A841F5E52079D76cf7751b;
alter table model_rp_jdyna_prop drop constraint FK6CF7751B22ECC2EE;
alter table model_rp_jdyna_widgetcombo2subtipprop drop constraint FK80E4519E2F4B53A5;
alter table model_rp_jdyna_widgetcombo2subtipprop drop constraint FK80E4519E333533BD;
drop table RPSubscription;
drop table StatSubscription;
drop table jdyna_containables;
drop table jdyna_values;
drop table jdyna_values_model_grant_jdyna_prop;
drop table jdyna_values_model_rp_jdyna_prop;
drop table jdyna_widget_date;
drop table jdyna_widget_link;
drop table jdyna_widget_text;
drop table model_grant_investigator;
drop table model_grant_jdyna_prop;
drop table model_grant_jdyna_propertiesdefinition;
drop table model_grant_jdyna_widgetcombo;
drop table model_grant_jdyna_widgetcombo2subtipprop;
drop table model_grant_jdyna_widgetfile;
drop table model_jdyna_box;
drop table model_jdyna_box2containable;
drop table model_jdyna_box_grant;
drop table model_jdyna_boxgrant2containablegrant;
drop table model_jdyna_edittab;
drop table model_jdyna_edittab2box;
drop table model_jdyna_edittabgrant;
drop table model_jdyna_edittabgrant2boxgrant;
drop table model_jdyna_tab;
drop table model_jdyna_tab2box;
drop table model_jdyna_tab_grant;
drop table model_jdyna_tabgrant2boxgrant;
drop table model_researcher_grant;
drop table model_researcher_page;
drop table model_researcher_page_interests;
drop table model_researcher_page_media;
drop table model_researcher_page_rejectItems;
drop table model_researcher_page_spokenLanguagesEN;
drop table model_researcher_page_spokenLanguagesZH;
drop table model_researcher_page_title;
drop table model_researcher_page_variants;
drop table model_researcher_page_writtenLanguagesEN;
drop table model_researcher_page_writtenLanguagesZH;
drop table model_rp_jdyna;
drop table model_rp_jdyna_prop;
drop table model_rp_jdyna_propertiesdefinition;
drop table model_rp_jdyna_widgetcombo;
drop table model_rp_jdyna_widgetcombo2subtipprop;
drop table model_rp_jdyna_widgetfile;
drop sequence BOX_SEQ;
drop sequence CONTAINABLE_SEQ;
drop sequence PROPERTIESDEFINITION_SEQ;
drop sequence PROPERTY_SEQ;
drop sequence RESEARCHERGRANT_SEQ;
drop sequence RESEARCHERPAGE_SEQ;
drop sequence RPDYNAADDITIONAL_SEQ;
drop sequence STATSUBSCRIPTION_SEQ;
drop sequence SUBSCRIPTIONRP_SEQ;
drop sequence TAB_SEQ;
drop sequence VALUES_SEQ;
drop sequence WIDGET_SEQ;
create table RPSubscription (id int4 not null, epersonID int4 not null, rp_id int4, primary key (id));
create table StatSubscription (id int4 not null, epersonID int4 not null, freq int4 not null, handle varchar(255), rp_id int4, primary key (id));
create table jdyna_containables (DTYPE varchar(31) not null, id int4 not null, propertiesdefinition_fk int4, propertiesdefinitiongrant_fk int4, primary key (id));
create table jdyna_values (DTYPE varchar(31) not null, id int4 not null, sortValue varchar(255), dateValue timestamp, testoValue text, linkdescription varchar(255), linkvalue text, filedescription varchar(255), fileextension varchar(255), filefolder varchar(255), filemime varchar(255), filesuffix varchar(255), filevalue text, primary key (id));
create table jdyna_values_model_grant_jdyna_prop (jdyna_values_id int4 not null, real_id int4 not null, unique (real_id));
create table jdyna_values_model_rp_jdyna_prop (jdyna_values_id int4 not null, real_id int4 not null, unique (real_id));
create table jdyna_widget_date (id int4 not null, maxYear int4, minYear int4, time bool not null, primary key (id));
create table jdyna_widget_link (id int4 not null, labelHeaderLabel varchar(255), labelHeaderURL varchar(255), size int4 not null, primary key (id));
create table jdyna_widget_text (id int4 not null, collisioni bool, col int4 not null, row int4 not null, htmlToolbar varchar(255), multilinea bool not null, regex varchar(255), primary key (id));
create table model_grant_investigator (model_researcher_grant_id int4 not null, extInvestigator varchar(255), intInvestigator_id int4);
create table model_grant_jdyna_prop (id int4 not null, position int4 not null, visibility int4, value_id int4 unique, parent_id int4, parentProperty_id int4, typo_id int4, primary key (id), unique (position, typo_id, parent_id, parentProperty_id));
create table model_grant_jdyna_propertiesdefinition (id int4 not null, advancedSearch bool not null, fieldmin_col int4, fieldmin_row int4, help text, label varchar(255), labelMinSize int4 not null, mandatory bool not null, newline bool not null, onCreation bool, priority int4 not null, repeatable bool not null, shortName varchar(255) unique, showInList bool not null, simpleSearch bool not null, topLevel bool not null, rendering_id int4 unique, accessLevel int4, primary key (id));
create table model_grant_jdyna_widgetcombo (id int4 not null, primary key (id));
create table model_grant_jdyna_widgetcombo2subtipprop (model_grant_jdyna_widgetcombo_id int4 not null, sottoTipologie_id int4 not null, unique (sottoTipologie_id));
create table model_grant_jdyna_widgetfile (id int4 not null, labelAnchor varchar(255), showPreview bool not null, primary key (id));
create table model_jdyna_box (id int4 not null, collapsed bool not null, priority int4 not null, shortName varchar(255) unique, title varchar(255), unrelevant bool not null, visibility int4, primary key (id));
create table model_jdyna_box2containable (model_jdyna_box_id int4 not null, mask_id int4 not null);
create table model_jdyna_box_grant (id int4 not null, collapsed bool not null, priority int4 not null, shortName varchar(255) unique, title varchar(255), unrelevant bool not null, visibility int4, primary key (id));
create table model_jdyna_boxgrant2containablegrant (model_jdyna_box_grant_id int4 not null, mask_id int4 not null);
create table model_jdyna_edittab (id int4 not null, ext varchar(255), mandatory bool not null, mime varchar(255), priority int4 not null, shortName varchar(255) unique, title varchar(255), visibility int4, displayTab_id int4, primary key (id));
create table model_jdyna_edittab2box (model_jdyna_edittab_id int4 not null, mask_id int4 not null);
create table model_jdyna_edittabgrant (id int4 not null, ext varchar(255), mandatory bool not null, mime varchar(255), priority int4 not null, shortName varchar(255) unique, title varchar(255), visibility int4, displayTab_id int4, primary key (id));
create table model_jdyna_edittabgrant2boxgrant (model_jdyna_edittabgrant_id int4 not null, mask_id int4 not null);
create table model_jdyna_tab (id int4 not null, ext varchar(255), mandatory bool not null, mime varchar(255), priority int4 not null, shortName varchar(255) unique, title varchar(255), visibility int4, primary key (id));
create table model_jdyna_tab2box (model_jdyna_tab_id int4 not null, mask_id int4 not null);
create table model_jdyna_tab_grant (id int4 not null, ext varchar(255), mandatory bool not null, mime varchar(255), priority int4 not null, shortName varchar(255) unique, title varchar(255), visibility int4, primary key (id));
create table model_jdyna_tabgrant2boxgrant (model_jdyna_tab_grant_id int4 not null, mask_id int4 not null);
create table model_researcher_grant (id int4 not null, extInvestigator varchar(255), rgCode varchar(255), status bool, timestampCreated timestamp, timestampLastModified timestamp, intInvestigator_id int4, primary key (id));
create table model_researcher_page (id int4 not null, academicName_value text, academicName_visibility int4, address_value text, address_visibility int4, authorIdLinkScopus_value text, authorIdLinkScopus_visibility int4, authorIdScopus_value text, authorIdScopus_visibility int4, bio_value text, bio_visibility int4, chineseName_value text, chineseName_visibility int4, citationCountISI_value text, citationCountISI_visibility int4, citationCountScopus_value text, citationCountScopus_visibility int4, citationLinkISI_value text, citationLinkISI_visibility int4, citationLinkScopus_value text, citationLinkScopus_visibility int4, coAuthorsISI_value text, coAuthorsISI_visibility int4, coAuthorsLinkScopus_value text, coAuthorsLinkScopus_visibility int4, coAuthorsScopus_value text, coAuthorsScopus_visibility int4, cv_url text, cv_type text, cv_ext text, cv_visibility int4, dept_value text, dept_visibility int4, email_value text, email_visibility int4, fullName varchar(255) not null, hindexISI_value text, hindexISI_visibility int4, hindexScopus_value text, hindexScopus_visibility int4, honorific_value text, honorific_visibility int4, namesTimestampLastModified timestamp, officeTel_value text, officeTel_visibility int4, paperCountISI_value text, paperCountISI_visibility int4, paperCountScopus_value text, paperCountScopus_visibility int4, paperLinkISI_value text, paperLinkISI_visibility int4, paperLinkScopus_value text, paperLinkScopus_visibility int4, pict_type text, pict_ext text, pict_visibility int4, ridISI_value text, ridISI_visibility int4, ridLinkISI_value text, ridLinkISI_visibility int4, staffNo varchar(255) not null unique, status bool, timestampCreated timestamp, timestampLastModified timestamp, personal_value text, personal_visibility int4, urlPict text, dynamicField_id int4, primary key (id));
create table model_researcher_page_interests (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_media (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_rejectItems (model_researcher_page_id int4 not null, element int4);
create table model_researcher_page_spokenLanguagesEN (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_spokenLanguagesZH (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_title (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_variants (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_writtenLanguagesEN (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_researcher_page_writtenLanguagesZH (model_researcher_page_id int4 not null, value text, visibility int4);
create table model_rp_jdyna (id int4 not null, primary key (id));
create table model_rp_jdyna_prop (id int4 not null, position int4 not null, visibility int4, value_id int4 unique, parent_id int4, parentProperty_id int4, typo_id int4, primary key (id), unique (position, typo_id, parent_id, parentProperty_id));
create table model_rp_jdyna_propertiesdefinition (id int4 not null, advancedSearch bool not null, fieldmin_col int4, fieldmin_row int4, help text, label varchar(255), labelMinSize int4 not null, mandatory bool not null, newline bool not null, onCreation bool, priority int4 not null, repeatable bool not null, shortName varchar(255) unique, showInList bool not null, simpleSearch bool not null, topLevel bool not null, rendering_id int4 unique, accessLevel int4, primary key (id));
create table model_rp_jdyna_widgetcombo (id int4 not null, primary key (id));
create table model_rp_jdyna_widgetcombo2subtipprop (model_rp_jdyna_widgetcombo_id int4 not null, sottoTipologie_id int4 not null, unique (sottoTipologie_id));
create table model_rp_jdyna_widgetfile (id int4 not null, labelAnchor varchar(255), showPreview bool not null, primary key (id));
alter table RPSubscription add constraint FK1E4A7BED3064CE foreign key (rp_id) references model_researcher_page;
alter table StatSubscription add constraint FKA04119B1ED3064CE foreign key (rp_id) references model_researcher_page;
alter table jdyna_containables add constraint FKB80C84B2CE9E17A foreign key (propertiesdefinition_fk) references model_rp_jdyna_propertiesdefinition;
alter table jdyna_containables add constraint FKB80C84B2F4B61038 foreign key (propertiesdefinitiongrant_fk) references model_grant_jdyna_propertiesdefinition;
alter table jdyna_values_model_grant_jdyna_prop add constraint FK34AFD5191CC700F5 foreign key (real_id) references model_grant_jdyna_prop;
alter table jdyna_values_model_grant_jdyna_prop add constraint FK34AFD519926DFEF foreign key (jdyna_values_id) references jdyna_values;
alter table jdyna_values_model_rp_jdyna_prop add constraint FK6C6EB66BEB64DBE7 foreign key (real_id) references model_rp_jdyna_prop;
alter table jdyna_values_model_rp_jdyna_prop add constraint FK6C6EB66B1D8241C1 foreign key (jdyna_values_id) references jdyna_values;
alter table model_grant_investigator add constraint FK8FBFD2C2BEC07DD8 foreign key (model_researcher_grant_id) references model_researcher_grant;
alter table model_grant_investigator add constraint FK8FBFD2C24E95E7B4 foreign key (intInvestigator_id) references model_researcher_page;
alter table model_grant_jdyna_prop add constraint FK5DD6586973EDB8A0 foreign key (typo_id) references model_grant_jdyna_propertiesdefinition;
alter table model_grant_jdyna_prop add constraint FK5DD658698E0C674 foreign key (parentProperty_id) references model_grant_jdyna_prop;
alter table model_grant_jdyna_prop add constraint FKC8A841F5E52079D75dd65869 foreign key (value_id) references jdyna_values;
alter table model_grant_jdyna_prop add constraint FK5DD658696AEC32C9 foreign key (parent_id) references model_researcher_grant;
alter table model_grant_jdyna_widgetcombo2subtipprop add constraint FKF4DA3D6C65234D52 foreign key (model_grant_jdyna_widgetcombo_id) references model_grant_jdyna_widgetcombo;
alter table model_grant_jdyna_widgetcombo2subtipprop add constraint FKF4DA3D6C5CB233CB foreign key (sottoTipologie_id) references model_grant_jdyna_propertiesdefinition;
alter table model_jdyna_box2containable add constraint FKD0AF5FA45BA7E0EB foreign key (model_jdyna_box_id) references model_jdyna_box;
alter table model_jdyna_box2containable add constraint FKD0AF5FA4C20F2E09 foreign key (mask_id) references jdyna_containables;
alter table model_jdyna_boxgrant2containablegrant add constraint FK9949552C95630636 foreign key (model_jdyna_box_grant_id) references model_jdyna_box_grant;
alter table model_jdyna_boxgrant2containablegrant add constraint FK9949552CC20F2E09 foreign key (mask_id) references jdyna_containables;
alter table model_jdyna_edittab add constraint FK6CD4C7E87FE0C82A foreign key (displayTab_id) references model_jdyna_tab;
alter table model_jdyna_edittab2box add constraint FKE3F6CC41A26AFB4B foreign key (model_jdyna_edittab_id) references model_jdyna_edittab;
alter table model_jdyna_edittab2box add constraint FKE3F6CC4154E7AA67 foreign key (mask_id) references model_jdyna_box;
alter table model_jdyna_edittabgrant add constraint FK9DD4AA34186781DE foreign key (displayTab_id) references model_jdyna_tab_grant;
alter table model_jdyna_edittabgrant2boxgrant add constraint FK90C068AF78A9B6EF foreign key (mask_id) references model_jdyna_box_grant;
alter table model_jdyna_edittabgrant2boxgrant add constraint FK90C068AFD2F41F47 foreign key (model_jdyna_edittabgrant_id) references model_jdyna_edittabgrant;
alter table model_jdyna_tab2box add constraint FK80B2D00B54E7AA67 foreign key (mask_id) references model_jdyna_box;
alter table model_jdyna_tab2box add constraint FK80B2D00BB39D6AB foreign key (model_jdyna_tab_id) references model_jdyna_tab;
alter table model_jdyna_tabgrant2boxgrant add constraint FK9D09B7F978A9B6EF foreign key (mask_id) references model_jdyna_box_grant;
alter table model_jdyna_tabgrant2boxgrant add constraint FK9D09B7F937DD8A2 foreign key (model_jdyna_tab_grant_id) references model_jdyna_tab_grant;
alter table model_researcher_grant add constraint FKCED62FFB4E95E7B4 foreign key (intInvestigator_id) references model_researcher_page;
alter table model_researcher_page add constraint FK1F761BD0A04339DD foreign key (dynamicField_id) references model_rp_jdyna;
alter table model_researcher_page_interests add constraint FKA1D7D0DA182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_media add constraint FK1A91D2B5182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_rejectItems add constraint FK63BB3172182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_spokenLanguagesEN add constraint FK42286293182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_spokenLanguagesZH add constraint FK42286518182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_title add constraint FK1AF68529182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_variants add constraint FKDACE601D182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_writtenLanguagesEN add constraint FK4DAD813C182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_researcher_page_writtenLanguagesZH add constraint FK4DAD83C1182595FC foreign key (model_researcher_page_id) references model_researcher_page;
alter table model_rp_jdyna_prop add constraint FK6CF7751B4A70B892 foreign key (typo_id) references model_rp_jdyna_propertiesdefinition;
alter table model_rp_jdyna_prop add constraint FK6CF7751BD77EA166 foreign key (parentProperty_id) references model_rp_jdyna_prop;
alter table model_rp_jdyna_prop add constraint FKC8A841F5E52079D76cf7751b foreign key (value_id) references jdyna_values;
alter table model_rp_jdyna_prop add constraint FK6CF7751B22ECC2EE foreign key (parent_id) references model_rp_jdyna;
alter table model_rp_jdyna_widgetcombo2subtipprop add constraint FK80E4519E2F4B53A5 foreign key (model_rp_jdyna_widgetcombo_id) references model_rp_jdyna_widgetcombo;
alter table model_rp_jdyna_widgetcombo2subtipprop add constraint FK80E4519E333533BD foreign key (sottoTipologie_id) references model_rp_jdyna_propertiesdefinition;
create sequence BOX_SEQ;
create sequence CONTAINABLE_SEQ;
create sequence PROPERTIESDEFINITION_SEQ;
create sequence PROPERTY_SEQ;
create sequence RESEARCHERGRANT_SEQ;
create sequence RESEARCHERPAGE_SEQ;
create sequence RPDYNAADDITIONAL_SEQ;
create sequence STATSUBSCRIPTION_SEQ;
create sequence SUBSCRIPTIONRP_SEQ;
create sequence TAB_SEQ;
create sequence VALUES_SEQ;
create sequence WIDGET_SEQ;
