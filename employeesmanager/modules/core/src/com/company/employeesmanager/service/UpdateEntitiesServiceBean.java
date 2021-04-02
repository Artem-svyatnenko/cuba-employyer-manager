package com.company.employeesmanager.service;

import com.company.employeesmanager.entity.Employee;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(UpdateEntitiesService.NAME)
public class UpdateEntitiesServiceBean implements UpdateEntitiesService {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private UserService userService;

    private String sheetsURL = "https://docs.google.com/spreadsheets/d/1gjNffs9R9yTzQrZk2eOmqm9xl9JNaoTA7W6_TTwX5vo/export?format=csv";

    @Override
    public void updateEntities() {
        //Обновление списка сотрудников и создание аккаунта пользователя для каждого нового сотрудника
        List<Employee> newEmployees = employeeService.updateEmployeeData(sheetsURL);
        for (Employee employee : newEmployees) {
            userService.createUserFromEmployee(employee, true);
        }
    }
}