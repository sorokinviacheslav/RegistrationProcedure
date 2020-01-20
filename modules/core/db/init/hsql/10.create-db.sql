-- begin SEC_USER
alter table SEC_USER add column DOMAIN_LOGIN varchar(255) ^
alter table SEC_USER add column CONFIRMED boolean ^
update SEC_USER set CONFIRMED = false where CONFIRMED is null ^
alter table SEC_USER alter column CONFIRMED set not null ^
alter table SEC_USER add column ACTIVATED boolean ^
update SEC_USER set ACTIVATED = false where ACTIVATED is null ^
alter table SEC_USER alter column ACTIVATED set not null ^
alter table SEC_USER add column PHONE_NUMBER varchar(255) ^
alter table SEC_USER add column RECIEVE_EMAIL_NOTIFICATIONS boolean ^
update SEC_USER set RECIEVE_EMAIL_NOTIFICATIONS = false where RECIEVE_EMAIL_NOTIFICATIONS is null ^
alter table SEC_USER alter column RECIEVE_EMAIL_NOTIFICATIONS set not null ^
alter table SEC_USER add column HIDE_EMAIL boolean ^
update SEC_USER set HIDE_EMAIL = false where HIDE_EMAIL is null ^
alter table SEC_USER alter column HIDE_EMAIL set not null ^
alter table SEC_USER add column ORGANIZATION_ID varchar(36) ^
alter table SEC_USER add column COMMENTS varchar(1000) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'registrationprocedure_UserExt' where DTYPE is null ^
-- end SEC_USER
-- begin REGISTRATIONPROCEDURE_ORGANIZATION
create table REGISTRATIONPROCEDURE_ORGANIZATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end REGISTRATIONPROCEDURE_ORGANIZATION
