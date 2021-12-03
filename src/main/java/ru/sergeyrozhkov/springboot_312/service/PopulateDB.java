package ru.sergeyrozhkov.springboot_312.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeyrozhkov.springboot_312.model.Role;
import ru.sergeyrozhkov.springboot_312.model.User;
import ru.sergeyrozhkov.springboot_312.repository.RoleRepository;
import ru.sergeyrozhkov.springboot_312.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class PopulateDB {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PopulateDB(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    protected void populateDB() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User user = new User();
        user.setFirstname("Bob");
        user.setLastname("Petrov");
        user.setEmail("bob@mail.com");
        user.setAge(25);
        user.setPassword("$2a$12$q98.zeMhcdtMh6.EIN/1eO9eJ5RbeU8G1Zl2Bo5zDMUulqYGjH3Pa");
        user.getRoles().add(roleAdmin);

        User admin = new User();
        admin.setFirstname("Ivan");
        admin.setLastname("Parkinson");
        admin.setEmail("ivan@mail.com");
        admin.setAge(30);
        admin.setPassword("$2a$12$q98.zeMhcdtMh6.EIN/1eO9eJ5RbeU8G1Zl2Bo5zDMUulqYGjH3Pa");
        admin.getRoles().add(roleUser);

        userRepository.save(user);
        userRepository.save(admin);
    }
}