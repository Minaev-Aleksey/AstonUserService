package com.example.Aston_traine2.dao;

import com.example.Aston_traine2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}