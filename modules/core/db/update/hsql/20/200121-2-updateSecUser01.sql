alter table SEC_USER alter column ACTIVATED rename to ACTIVATED__U52230 ^
alter table SEC_USER alter column ACTIVATED__U52230 set null ;
alter table SEC_USER add column STATUS integer ^
update SEC_USER set STATUS = 10 where STATUS is null ;
alter table SEC_USER alter column STATUS set not null ;
