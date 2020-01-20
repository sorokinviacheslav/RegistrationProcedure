-- begin SEC_USER
alter table SEC_USER add column ACTIVATED boolean ^
update SEC_USER set ACTIVATED = false where ACTIVATED is null ^
alter table SEC_USER alter column ACTIVATED set not null ^
alter table SEC_USER add column CONFIRMED boolean ^
update SEC_USER set CONFIRMED = false where CONFIRMED is null ^
alter table SEC_USER alter column CONFIRMED set not null ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'registrationprocedure_UserExt' where DTYPE is null ^
-- end SEC_USER
