package com.company.employeesmanager.service;

import com.company.employeesmanager.entity.Employee;

import java.util.List;

public interface EmployeeService {
    String NAME = "employeesmanager_EmployeeService";

    //Из таблицы по ссылке создает новые и обновляет записи в коллекции сущностей Employee
    //Возвращает список добавленных записей
    List<Employee> updateEmployeeData(String tableURL);

    //Возвращает количество новых записей в коллекции сущностей Employee
    int getNewEmployeeCount();

    //Возвращает количество измененных записей в коллекции сущностей Employee
    int getChangedEmployeeCount();
}