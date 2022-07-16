package com.fpt.macm.security.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.User;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.security.UserPrincipal;
import com.fpt.macm.security.exception.ResourceNotFoundException;

@Service
public class UserServiceOauth2Impl implements UserServiceOauth2 {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
