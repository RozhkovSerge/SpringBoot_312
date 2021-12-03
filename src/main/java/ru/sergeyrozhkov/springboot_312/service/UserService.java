package ru.sergeyrozhkov.springboot_312.service;

import ru.sergeyrozhkov.springboot_312.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserByEmail(String email);
}
