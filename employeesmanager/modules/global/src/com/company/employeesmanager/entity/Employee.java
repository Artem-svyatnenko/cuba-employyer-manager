package com.company.employeesmanager.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Table(name = "EMPLOYEESMANAGER_EMPLOYEE")
@Entity(name = "employeesmanager_Employee")
@NamePattern("%s|firstName")
public class Employee extends StandardEntity {
    private static final long serialVersionUID = -3801909075238890516L;

    @Column(name = "TABLE_ID", nullable = false, unique = true)
    @NotNull
    private String tableId;

    @NotNull
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME", nullable = false, length = 80)
    private String lastName;

    @NotNull
    @Column(name = "SECOND_NAME", nullable = false, length = 80)
    private String secondName;

    @Column(name = "PHONE_NUMBER", length = 18)
    private String phoneNumber;

    @Column(name = "EMAIL", unique = true)
    @Email
    private String email;

    @Column(name = "POSITION_")
    private String position;

    @Column(name = "COMPANY")
    private String company;

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}