-- update REGISTRATIONPROCEDURE_ORGANIZATION set INN = <default_value> where INN is null ;
alter table REGISTRATIONPROCEDURE_ORGANIZATION alter column INN set not null ;
