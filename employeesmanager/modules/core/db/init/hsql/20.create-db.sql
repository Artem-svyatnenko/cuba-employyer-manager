-- begin EMPLOYEESMANAGER_EMPLOYEE
create unique index IDX_EMPLOYEESMANAGER_EMPLOYEE_UNIQ_TABLE_ID on EMPLOYEESMANAGER_EMPLOYEE (TABLE_ID)^
create unique index IDX_EMPLOYEESMANAGER_EMPLOYEE_UNIQ_EMAIL on EMPLOYEESMANAGER_EMPLOYEE (EMAIL)^
-- end EMPLOYEESMANAGER_EMPLOYEE
