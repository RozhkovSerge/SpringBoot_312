package ru.sergeyrozhkov.springboot_312.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeyrozhkov.springboot_312.model.Role;
import ru.sergeyrozhkov.springboot_312.model.User;
import ru.sergeyrozhkov.springboot_312.service.RoleService;
import ru.sergeyrozhkov.springboot_312.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String show(Model model, Principal principal) {
        User loggedInUser = userService.findUserByEmail(principal.getName());
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(s -> s.toString().substring(5))
                .collect(Collectors.joining(", "));
        model.addAttribute("roles", roles);
        model.addAttribute("emptyUser", new User());
        model.addAttribute("user", loggedInUser); //поменять на loggedInUser
        model.addAttribute("userList", userService.findAll()); // поменять на listUsers
        model.addAttribute("listRoles", roleService.findAll());

        return "admin";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}
