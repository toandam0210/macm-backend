package com.fpt.macm.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(int id) {
    	Optional<User> userOptional = userRepository.findById(id);
    	User user = new User();
    	if(userOptional.isPresent()) {
         user = userRepository.findById(id).get();
    	}else {
    		throw new ResourceNotFoundException("id");
    	}
        return UserPrincipal.create(user);
    }
}