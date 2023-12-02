package com.as.spring.asmenu.service.user;

import com.as.spring.asmenu.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);

    User findById(Long id);
    void saveNew(User user);
    boolean activateUser(String code);

    List<User> findAll();

    void save(User user);

    void deleteById(Long id);
}
