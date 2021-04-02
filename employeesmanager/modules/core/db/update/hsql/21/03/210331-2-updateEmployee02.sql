alter table EMPLOYEESMANAGER_EMPLOYEE alter column TABLE_ID rename to TABLE_ID__U42726 ^
alter table EMPLOYEESMANAGER_EMPLOYEE alter column TABLE_ID__U42726 set null ;
-- alter table EMPLOYEESMANAGER_EMPLOYEE add column TABLE_ID varchar(255) ^
-- update EMPLOYEESMANAGER_EMPLOYEE set TABLE_ID = <default_value> ;
-- alter table EMPLOYEESMANAGER_EMPLOYEE alter column TABLE_ID set not null ;
alter table EMPLOYEESMANAGER_EMPLOYEE add column TABLE_ID varchar(255) ;
