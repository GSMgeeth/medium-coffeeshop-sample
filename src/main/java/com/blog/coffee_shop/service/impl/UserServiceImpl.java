package com.blog.coffee_shop.service.impl;

import com.blog.coffee_shop.model.User;
import com.blog.coffee_shop.service.UserService;
import com.blog.coffee_shop.util.Constant;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (!username.equals("john_doe")) {
            throw new UsernameNotFoundException("Username not found!");
        }

        final User user = new User();
        user.setId(1);
        user.setUsername("john_doe");
        user.setUserRole(Constant.UserRole.ROLE_USER);
        user.setPassword("$2a$12$WGGjDBSXAH8xulWM.HjfE.erMRjp6EJiiAGR203i6AWSnPuJKfLO."); // welCome1/

        return user;
    }
}
