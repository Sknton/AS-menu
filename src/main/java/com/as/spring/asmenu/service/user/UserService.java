package com.as.spring.asmenu.service.user;

import com.as.spring.asmenu.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);

    User findById(Long id);
    void save(User user);

    boolean activateUser(String code);
}
