package com.example.sysubbsproject.db;

import com.example.sysubbsproject.db.bmob.Person;

import cn.bmob.v3.listener.SaveListener;

public class PersonHandler {

    // 注册后端接口
    public static void registerNewPerson(String username, String password, SaveListener<Person> listener) {
        Person person = new Person();
        person.setUsername(username);
        person.setPassword(password);
        person.signUp(listener);
    }
    // 登录后端接口
    public static void login(String username, String password, SaveListener<Person> listener) {
        Person person = new Person();
        person.setUsername(username);
        person.setPassword(password);
        person.login(listener);
    }
}
