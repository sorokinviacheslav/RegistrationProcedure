alter table SEC_USER add column CONFIRMED boolean ^
update SEC_USER set CONFIRMED = false where CONFIRMED is null ;
alter table SEC_USER alter column CONFIRMED set not null ;
