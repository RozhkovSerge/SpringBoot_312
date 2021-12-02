package ru.sergeyrozhkov.springboot_312.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sergeyrozhkov.springboot_312.service.UserServiceImp;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImp userService;
    private final SuccessLoginHandler successLoginHandler;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserServiceImp userService, SuccessLoginHandler successLoginHandler, UserDetailsService userDetailsService) {
        this.successLoginHandler = successLoginHandler;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .usernameParameter("email");
        http
                .authorizeRequests()
                .antMatchers("/").hasRole("ADMIN");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // так работает
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // и так работает
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
