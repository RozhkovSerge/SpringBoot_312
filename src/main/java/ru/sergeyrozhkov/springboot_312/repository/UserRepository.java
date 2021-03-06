package ru.sergeyrozhkov.springboot_312.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sergeyrozhkov.springboot_312.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles where u.email=:email")
    User findUserByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles where u.id=:id")
    User findUserById(Long id);
}
