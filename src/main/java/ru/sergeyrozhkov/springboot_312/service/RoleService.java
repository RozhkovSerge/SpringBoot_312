package ru.sergeyrozhkov.springboot_312.service;

import ru.sergeyrozhkov.springboot_312.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
}
