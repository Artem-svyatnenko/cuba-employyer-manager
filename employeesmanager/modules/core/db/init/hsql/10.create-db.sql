-- begin EMPLOYEESMANAGER_EMPLOYEE
create table EMPLOYEESMANAGER_EMPLOYEE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TABLE_ID varchar(255) not null,
    FIRST_NAME varchar(50) not null,
    LAST_NAME varchar(80) not null,
    SECOND_NAME varchar(80) not null,
    PHONE_NUMBER varchar(18),
    EMAIL varchar(255),
    POSITION_ varchar(255),
    COMPANY varchar(255),
    --
    primary key (ID)
)^
-- end EMPLOYEESMANAGER_EMPLOYEE
