update SEC_USER set CHECKED = false where CHECKED is null ;
alter table SEC_USER alter column CHECKED set not null ;
