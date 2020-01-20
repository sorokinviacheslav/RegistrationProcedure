alter table SEC_USER add column COMMENTS varchar(1000) ;
alter table SEC_USER add column DOMAIN_LOGIN varchar(255) ;
-- alter table SEC_USER add column ORGANIZATION_ID varchar(36) ^
-- update SEC_USER set ORGANIZATION_ID = <default_value> ;
-- alter table SEC_USER alter column ORGANIZATION_ID set not null ;
alter table SEC_USER add column ORGANIZATION_ID varchar(36) not null ;
alter table SEC_USER add column RECIEVE_EMAIL_NOTIFICATIONS boolean ^
update SEC_USER set RECIEVE_EMAIL_NOTIFICATIONS = false where RECIEVE_EMAIL_NOTIFICATIONS is null ;
alter table SEC_USER alter column RECIEVE_EMAIL_NOTIFICATIONS set not null ;
alter table SEC_USER add column HIDE_EMAIL boolean ^
update SEC_USER set HIDE_EMAIL = false where HIDE_EMAIL is null ;
alter table SEC_USER alter column HIDE_EMAIL set not null ;
alter table SEC_USER add column PHONE_NUMBER varchar(255) ;
