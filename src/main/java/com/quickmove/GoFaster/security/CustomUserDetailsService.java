package com.quickmove.GoFaster.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.entity.Userr;
import com.quickmove.GoFaster.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
        Userr user = userRepo.findByMobileno(Long.parseLong(mobileNo))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(String.valueOf(user.getMobileno()))
                .password(user.getPassword())
                .roles(user.getRole()) // CUSTOMER or DRIVER
                .build();
    }
}
