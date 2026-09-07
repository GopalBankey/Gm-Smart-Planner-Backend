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

    // =====================================
    // LOAD USER BY MOBILE NUMBER
    // =====================================

    @Override
    public UserDetails loadUserByUsername(

            String mobileNumber

    ) throws UsernameNotFoundException {

        User user =

                userRepository

                        .findByMobileNumberAndActiveTrue(
                                mobileNumber
                        )

                        .orElseThrow(() ->

                                new UsernameNotFoundException(
                                        "User not found or account is no longer active"
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