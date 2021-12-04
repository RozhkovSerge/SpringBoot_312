package ru.sergeyrozhkov.springboot_312.controller;

import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sergeyrozhkov.springboot_312.model.User;
import ru.sergeyrozhkov.springboot_312.service.RoleService;
import ru.sergeyrozhkov.springboot_312.service.UserService;

import java.security.Principal;
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
        User loggedUser = userService.findUserByEmail(principal.getName());
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(s -> s.toString().substring(5))
                .collect(Collectors.joining(", "));
        model.addAttribute("roles", roles);
        model.addAttribute("emptyUser", new User());
        model.addAttribute("principal", loggedUser);
        model.addAttribute("listUsers", userService.findAll());
        model.addAttribute("listRoles", roleService.findAll());

        return "admin";
    }

    @PostMapping("/admin/save")
    public String saveUser(User user) {
        try {
            userService.save(user);
        } catch (NonUniqueResultException ignored) {
            ignored.printStackTrace();
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(User user) {
        userService.delete(user);

        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", roleService.findAll());
        return "edit";
    }

    @GetMapping("/admin/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", roleService.findAll());
        return "delete";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("principal", userService.findUserByEmail(principal.getName()));
        return "user";
    }
}
