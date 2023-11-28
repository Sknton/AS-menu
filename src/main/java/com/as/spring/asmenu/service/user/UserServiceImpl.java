package com.as.spring.asmenu.service.user;

import com.as.spring.asmenu.repository.RoleRepository;
import com.as.spring.asmenu.repository.UserRepository;
import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Role;
import com.as.spring.asmenu.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userRepository.findByUsername(userName);
    }

    @Override
    public void save(User user) {

        // assign user details to the user object
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // give user default role of "CLIENT"
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_CLIENT")));
        user.setEnabled(true);
        user.setBasket(new Basket(0, 0D));

        // save user in the database
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
