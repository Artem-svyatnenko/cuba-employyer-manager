package com.company.employeesmanager.service;

import com.company.employeesmanager.entity.Employee;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(EmployeeService.NAME)
public class EmployeeServiceBean implements EmployeeService {
    @Inject
    private DataManager dataManager;

    private static int newEnployeesCount = 0;
    private static int changedEnployeesCount = 0;

    @Override
    public List<Employee> updateEmployeeData(String tableURL) {
        InputStream in;
        String result = null;
        boolean isUnic;
        boolean isChanged;
        //Получение файла таблицы как потока и преобразование в строку
        List<Employee> newEmployees = new ArrayList<>();
        try {
            in = new URL(tableURL).openStream();
            result = IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (IOException e) { e.printStackTrace(); }

        //Список сущностей Employee в базе данных
        List<Employee> employeesBD = loadEmployees();
        //Список извлеченных валидных сущностей Employee из таблицы
        List<Employee> employeesFromTable= getEmployeesFromTable(result);
        //Сравнение каждой сущности Employee из базы данных с таблицей
        //на наличие новых или измененных экземепляров
        for(int i = 0; i <= employeesFromTable.size() - 1; i++){
            isUnic = true;
            isChanged = false;
            for(Employee employeeBD:employeesBD){
                //Проверка сотрудника с существующим id на изменения в таблице.
                //Получилась очень громоздкой из-за проверки каждого параметра + несовместимость equals() с null
                if (employeeBD.getTableId().equals(employeesFromTable.get(i).getTableId())) {
                    if(!((employeeBD.getFirstName() == null && employeesFromTable.get(i).getFirstName() == null))) {
                        if(employeeBD.getFirstName() == null || employeesFromTable.get(i).getFirstName() == null) {
                            employeeBD.setFirstName(employeesFromTable.get(i).getFirstName());
                            isChanged = true;
                        } else
                         if(!employeeBD.getFirstName().equals(employeesFromTable.get(i).getFirstName())) {
                            employeeBD.setFirstName(employeesFromTable.get(i).getFirstName());
                            isChanged = true;
                        }
                    }

                    if(!((employeeBD.getLastName() == null && employeesFromTable.get(i).getLastName() == null))) {
                        if(employeeBD.getLastName() == null || employeesFromTable.get(i).getLastName() == null) {
                            employeeBD.setLastName(employeesFromTable.get(i).getLastName());
                            isChanged = true;
                        } else
                         if(!employeeBD.getLastName().equals(employeesFromTable.get(i).getLastName())) {
                            employeeBD.setLastName(employeesFromTable.get(i).getLastName());
                            isChanged = true;
                        }
                    }

                    if(!((employeeBD.getSecondName() == null && employeesFromTable.get(i).getSecondName() == null))) {
                        if(employeeBD.getSecondName() == null || employeesFromTable.get(i).getSecondName() == null) {
                            employeeBD.setSecondName(employeesFromTable.get(i).getSecondName());
                            isChanged = true;
                        } else
                        if(!employeeBD.getSecondName().equals(employeesFromTable.get(i).getSecondName())) {
                            employeeBD.setSecondName(employeesFromTable.get(i).getSecondName());
                            isChanged = true;
                        }
                    }

                    if(!((employeeBD.getPhoneNumber() == null && employeesFromTable.get(i).getPhoneNumber() == null))) {
                        if(employeeBD.getPhoneNumber() == null || employeesFromTable.get(i).getPhoneNumber() == null) {
                            employeeBD.setPhoneNumber(employeesFromTable.get(i).getPhoneNumber());
                            isChanged = true;
                        } else
                        if(!employeeBD.getPhoneNumber().equals(employeesFromTable.get(i).getPhoneNumber())) {
                            employeeBD.setPhoneNumber(employeesFromTable.get(i).getPhoneNumber());
                            isChanged = true;
                        }
                    }

                    boolean isEmptypass = false;
                    if(employeeBD.getEmail() == null) isEmptypass = true;
                    if(!((employeeBD.getEmail() == null && employeesFromTable.get(i).getEmail() == null))) {
                        if(employeeBD.getEmail() == null || employeesFromTable.get(i).getEmail() == null) {
                            employeeBD.setEmail(employeesFromTable.get(i).getEmail());
                            isChanged = true;
                        } else
                        if(!employeeBD.getEmail().equals(employeesFromTable.get(i).getEmail())) {
                            employeeBD.setEmail(employeesFromTable.get(i).getEmail());
                            isChanged = true;
                        }
                    }

                    if(!((employeeBD.getPosition() == null && employeesFromTable.get(i).getPosition() == null))) {
                        if(employeeBD.getPosition() == null || employeesFromTable.get(i).getPosition() == null) {
                            employeeBD.setPosition(employeesFromTable.get(i).getPosition());
                            isChanged = true;
                        } else
                        if(!employeeBD.getPosition().equals(employeesFromTable.get(i).getPosition())) {
                            employeeBD.setPosition(employeesFromTable.get(i).getPosition());
                            isChanged = true;
                        }
                    }
                    if(!((employeeBD.getCompany() == null && employeesFromTable.get(i).getCompany() == null))) {
                        if(employeeBD.getCompany() == null || employeesFromTable.get(i).getCompany() == null) {
                            employeeBD.setCompany(employeesFromTable.get(i).getCompany());
                            isChanged = true;
                        } else
                        if(!employeeBD.getCompany().equals(employeesFromTable.get(i).getCompany())) {
                            employeeBD.setCompany(employeesFromTable.get(i).getCompany());
                            isChanged = true;
                        }
                    }
                    //Перезапись сотрудника с тем же id и обновленными параметрами
                    if(isChanged) {
                        changedEnployeesCount++;
                        dataManager.commit(employeeBD);
                        if (employeeBD.getEmail() != null && isEmptypass) {
                            newEmployees.add(employeeBD);
                        }
                    }
                    isUnic = false;
                    break;
                } else {
                    //Проверка на совпадение email у разных записей
                    if (employeesFromTable.get(i).getEmail() != null && employeeBD.getEmail() != null) {
                        if (employeeBD.getEmail().equals(employeesFromTable.get(i).getEmail())) {
                            isUnic = false;
                            break;
                        }
                    }
                }
            }
            //Формирование списка новых экземпляров Employee
            if (isUnic) {
                dataManager.commit(employeesFromTable.get(i));
                newEmployees.add(employeesFromTable.get(i));
                newEnployeesCount++;
            }
        }
        return newEmployees;
    }

    @Override
    public int getNewEmployeeCount() {
        return newEnployeesCount;
    }

    @Override
    public int getChangedEmployeeCount() {
        return changedEnployeesCount;
    }

    //Возвращает список сущностей Employee из google таблицы
    private List<Employee> getEmployeesFromTable(String tableData){
        List<Employee> employeesFromTable = new ArrayList<>();
        String tableLines[] = tableData.split("\n");
        boolean isHeader = true;
        for(String line:tableLines){
            if (isHeader) {
                isHeader = false;
            } else {
                Employee e = getEmployeeFromString(line.replaceAll("\r",""));
                if(e != null) {
                    employeesFromTable.add(e);
                }
            }
        }
        return employeesFromTable;
    }

    //Разбирает строку, в которой записанны поля одной сущности и возвращает в виде экземпляра Employee
    private Employee getEmployeeFromString(String employeeData){
        String[] employeeParams = new String[8];
        int i = 0;
        for(String string:employeeData.split(",")) {
            employeeParams[i] = string.replaceAll("\n","");
            i++;
        }
        Employee employee = dataManager.create(Employee.class);
        //Проверка каждого поля на соответствие ограничениям сущности Employee
        if(!employeeParams[0].equals("") && employeeParams[0] != null) {
            employee.setTableId(employeeParams[0]);
        } else { return null; }
        if(!employeeParams[1].equals("") && employeeParams[1] != null && employeeParams[1].length() <=50) {
            employee.setFirstName(employeeParams[1]);
        } else { return null; }
        if(!employeeParams[2].equals("") && employeeParams[2] != null && employeeParams[2].length() <=80) {
            employee.setLastName(employeeParams[2]);
        } else { return null; }
        if(!employeeParams[3].equals("") && employeeParams[3] != null && employeeParams[3].length() <=80) {
            employee.setSecondName(employeeParams[3]);
        } else { return null; }
        if(employeeParams[4] != null)
            if(!employeeParams[4].equals(""))
                employee.setPhoneNumber(employeeParams[4]);
        if(employeeParams[5] != null)
            if(employeeParams[5].equals("") || validate(employeeParams[5])) {
                if(!employeeParams[5].equals("")){
                    employee.setEmail(employeeParams[5]);
                }
            } else { return null; }
        if(employeeParams[6] != null)
            if(!employeeParams[6].equals(""))
                employee.setPosition(employeeParams[6]);
        if(employeeParams[7] != null)
            if(!employeeParams[7].equals(""))
                employee.setCompany(employeeParams[7]);

        return employee;

    }
    //Проверка email на соответсвие формату
    public boolean validate(final String hex) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
    //Загрузка списка сущностей Employee из базы данных
    private List<Employee> loadEmployees() {
        LoadContext<Employee> loadContext = LoadContext.create(Employee.class)
                .setQuery(LoadContext.createQuery("select p from employeesmanager_Employee p"));
        return dataManager.loadList(loadContext);
    }
}