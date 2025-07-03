package com.example.Aston_traine2.repository;

import com.example.Aston_traine2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}