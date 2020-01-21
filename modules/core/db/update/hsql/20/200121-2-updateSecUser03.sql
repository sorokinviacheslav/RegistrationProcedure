alter table SEC_USER add column ROLE integer ^
update SEC_USER set ROLE = 10 where ROLE is null ;
alter table SEC_USER alter column ROLE set not null ;
