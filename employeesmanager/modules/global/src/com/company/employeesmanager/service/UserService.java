package com.company.employeesmanager.service;

import com.company.employeesmanager.entity.Employee;

import java.util.List;

public interface UserService {
    String NAME = "employeesmanager_UserService";
    //Создает пользователя на основе экземпляра Employee
    void createUserFromEmployee(Employee newEmployees, boolean sendEmail);
}