package com.company.employeesmanager.web.screens.employee;

import com.company.employeesmanager.service.EmployeeService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.employeesmanager.entity.Employee;
import com.haulmont.cuba.security.entity.User;
import sun.text.resources.CollationData;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@UiController("employeesmanager_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {

    @Inject
    EmployeeService employeeService;

    @Inject
    Label newEmployeesLabel;

    @Inject
    Label changedEmployeesLabel;

    @Subscribe
    public void onInit(InitEvent event) {
        newEmployeesLabel.setValue("Количество новых пользователей: " + employeeService.getNewEmployeeCount());
        changedEmployeesLabel.setValue("Количество измененных пользователей: " + employeeService.getChangedEmployeeCount());
    }
}