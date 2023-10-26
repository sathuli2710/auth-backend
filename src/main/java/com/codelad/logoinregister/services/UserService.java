package com.codelad.logoinregister.services;

import com.codelad.logoinregister.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<UserEntity> getAllUsers();
}
