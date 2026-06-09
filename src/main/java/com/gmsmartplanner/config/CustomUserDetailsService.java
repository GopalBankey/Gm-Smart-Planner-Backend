package com.gmsmartplanner.config;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository
            userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String mobileNumber
    ) throws UsernameNotFoundException {

        User user = userRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        return new org.springframework.security.core.userdetails.User(

                user.getMobileNumber(),

                "",

                Collections.singletonList(
                        new SimpleGrantedAuthority(
                                "USER"
                        )
                )
        );
    }
}