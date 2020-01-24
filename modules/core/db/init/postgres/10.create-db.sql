-- begin REGISTRATIONPROCEDURE_ORGANIZATION
create table REGISTRATIONPROCEDURE_ORGANIZATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ROLE integer not null,
    EMAIL varchar(255) not null,
    NAME varchar(255) not null,
    INN varchar(20) not null,
    --
    primary key (ID)
)^
-- end REGISTRATIONPROCEDURE_ORGANIZATION
-- begin SEC_USER
alter table SEC_USER add column DOMAIN_LOGIN varchar(255) ^
alter table SEC_USER add column ROLE integer ^
update SEC_USER set ROLE = 10 where ROLE is null ^
alter table SEC_USER alter column ROLE set not null ^
alter table SEC_USER add column STATUS integer ^
update SEC_USER set STATUS = 10 where STATUS is null ^
alter table SEC_USER alter column STATUS set not null ^
alter table SEC_USER add column PHONE_NUMBER varchar(255) ^
update SEC_USER set PHONE_NUMBER = '' where PHONE_NUMBER is null ^
alter table SEC_USER alter column PHONE_NUMBER set not null ^
alter table SEC_USER add column RECIEVE_EMAIL_NOTIFICATIONS boolean ^
update SEC_USER set RECIEVE_EMAIL_NOTIFICATIONS = false where RECIEVE_EMAIL_NOTIFICATIONS is null ^
alter table SEC_USER alter column RECIEVE_EMAIL_NOTIFICATIONS set not null ^
alter table SEC_USER add column HIDE_EMAIL boolean ^
update SEC_USER set HIDE_EMAIL = false where HIDE_EMAIL is null ^
alter table SEC_USER alter column HIDE_EMAIL set not null ^
alter table SEC_USER add column ORGANIZATION_ID uuid ^
alter table SEC_USER add column COMMENTS varchar(1000) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'registrationprocedure_UserExt' where DTYPE is null ^
-- end SEC_USER
-- begin SEC_ROLE
alter table SEC_ROLE add column ORGANIZATION_ROLE integer ^
update SEC_ROLE set ORGANIZATION_ROLE = 10 where ORGANIZATION_ROLE is null ^
alter table SEC_ROLE alter column ORGANIZATION_ROLE set not null ^
alter table SEC_ROLE add column USER_ROLE integer ^
update SEC_ROLE set USER_ROLE = 10 where USER_ROLE is null ^
alter table SEC_ROLE alter column USER_ROLE set not null ^
alter table SEC_ROLE add column DTYPE varchar(100) ^
update SEC_ROLE set DTYPE = 'registrationprocedure_RoleExt' where DTYPE is null ^
-- end SEC_ROLE
