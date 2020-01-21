-- alter table REGISTRATIONPROCEDURE_ORGANIZATION add column INN varchar(20) ^
-- update REGISTRATIONPROCEDURE_ORGANIZATION set INN = <default_value> ;
-- alter table REGISTRATIONPROCEDURE_ORGANIZATION alter column INN set not null ;
alter table REGISTRATIONPROCEDURE_ORGANIZATION add column INN varchar(20) ;
