package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHelperServiceImpl
        implements UserHelperService {

    private final UserRepository
            userRepository;

    // =====================================
    // GET CURRENT USER
    // =====================================

    @Override
    public User getCurrentUser(
            String username
    ) {

        try {

            Long userId =
                    Long.parseLong(username);

            return userRepository
                    .findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found"
                            )
                    );

        } catch (NumberFormatException ex) {

            throw new ResourceNotFoundException(
                    "Invalid user identifier"
            );
        }
    }
}