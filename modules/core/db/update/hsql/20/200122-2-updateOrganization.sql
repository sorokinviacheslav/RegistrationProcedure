alter table REGISTRATIONPROCEDURE_ORGANIZATION add column EMAIL varchar(255) ^
update REGISTRATIONPROCEDURE_ORGANIZATION set EMAIL = '' where EMAIL is null ;
alter table REGISTRATIONPROCEDURE_ORGANIZATION alter column EMAIL set not null ;
