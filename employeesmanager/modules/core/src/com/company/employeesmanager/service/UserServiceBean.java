package com.company.employeesmanager.service;

import com.company.employeesmanager.entity.Employee;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(UserService.NAME)
public class UserServiceBean implements UserService {
    @Inject
    private DataManager dataManager;

    @Inject
    protected PasswordEncryption passwordEncryption;

    @Inject
    protected EmailService emailService;


    @Override
    public void createUserFromEmployee(Employee newEmployees, boolean sendEmail) {
        List<User> userList = loadUsers();
        Map<String, Serializable> newUserData;
        boolean isUserExsist = false;
        //проверка на отсутствие пользователя с логином, идентичным email'у сотрудника
        for (User user: userList) {
            if (newEmployees.getEmail() != null) {
                if (user.getLogin().equals(newEmployees.getEmail())) {
                    isUserExsist = true;
                    break;
                }
            }
        }
        //Создание нового пользователя
        if (!isUserExsist && newEmployees.getEmail() != null) {
            newUserData = createUser(
                    newEmployees.getEmail(),
                    newEmployees.getFirstName(),
                    newEmployees.getLastName(),
                    newEmployees.getPosition());
            //Отправка почты, если это указано при вызове метода
            if(sendEmail){
                sendEmail(newUserData);
            }
        }
        newEmployees.getEmail();
    }

    //Загрузка списка сущностей User из базы данных
    private List<User> loadUsers() {
        LoadContext<User> loadContext = LoadContext.create(User.class)
                .setQuery(LoadContext.createQuery("select p from sec$User p"));
        return dataManager.loadList(loadContext);
    }

    //Создает новую запись сущности User в базе данных и вовзращает map с параметрами для отправки email
    private Map<String, Serializable> createUser(String email, String name, String lastname, String position) {
        User user = dataManager.create(User.class);
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setLogin(email);
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setPosition(position);
        //Формирования хеша пароля
        String password = getRndPass();
        user.setPasswordEncryption(passwordEncryption.getHashMethod());
        String passwordHash = passwordEncryption.getPasswordHash(user.getId(), password);
        user.setPassword(passwordHash);
        //Получение списка сущностей Group (обязательное поле при создании User)
        LoadContext<Group> loadContext = LoadContext.create(Group.class)
                .setQuery(LoadContext.createQuery("Select p from sec$Group p"));
        user.setGroup(dataManager.loadList(loadContext).get(0));
        //Принудительная смена пароля при следующем входе
        user.setChangePasswordAtNextLogon(true);
        //Запись новой сущности User в базу данных
        CommitContext commitContext = new CommitContext(user);
        dataManager.commit(commitContext);
        return getUserData(user.getName(), user.getEmail(), password);
    }

    //Отправка email новому пользователю с логином и паролем
    private void sendEmail(Map<String, Serializable> userData) {
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(userData.get("userEmail").toString())
                .setCaption("Регистрация в сервисе ХХХ")
                .setFrom(null)
                .setTemplatePath("com/company/employeesmanager/templates/mail_content.txt")
                .setTemplateParameters(userData)
                .build();
        emailService.sendEmailAsync(emailInfo);
    }
    //Сбор данных пользователя для указания параметров email'а
    private Map<String, Serializable> getUserData(String userName, String userEmail, String userPass) {
        Map<String, Serializable> userdata = new HashMap<>();
        userdata.put("userName", userName);
        userdata.put("userEmail", userEmail);
        userdata.put("userPass", userPass);
        return userdata;
    }

    //генерация случайного пароля
    private String getRndPass() {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator
                .Builder()
                .withinRange('0', 'z')
                .build();
        return pwdGenerator.generate(8);
    }
}