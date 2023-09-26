package com.example.EvidenNewsAggregator.user;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = userService.findByUsername(username);
            return UserDetailImp.build(user);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}
