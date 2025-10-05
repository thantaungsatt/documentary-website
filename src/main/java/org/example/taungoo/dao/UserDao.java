package org.example.taungoo.dao;

import org.example.taungoo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
//    User findByUsername(String username);
    Optional<User> findByUsername(String username);
}
