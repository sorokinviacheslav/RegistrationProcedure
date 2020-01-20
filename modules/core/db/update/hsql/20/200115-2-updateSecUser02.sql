alter table SEC_USER alter column CHECKED rename to CHECKED__U59285 ^
alter table SEC_USER alter column CHECKED__U59285 set null ;
alter table SEC_USER add column ACTIVATED boolean ^
update SEC_USER set ACTIVATED = false where ACTIVATED is null ;
alter table SEC_USER alter column ACTIVATED set not null ;
