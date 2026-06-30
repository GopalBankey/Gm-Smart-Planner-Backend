package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.exception.InvalidRequestException;
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
                    Long.parseLong(
                            username
                    );

            User user =

                    userRepository
                            .findById(
                                    userId
                            )

                            .orElseThrow(

                                    () ->

                                            new ResourceNotFoundException(

                                                    "User not found"
                                            )
                            );

            if (

                    !user.isActive()

            ) {

                throw new InvalidRequestException(

                        "Account deleted"
                );
            }

            return user;

        }

        catch (

                NumberFormatException ex

        ) {

            throw new ResourceNotFoundException(

                    "Invalid user identifier"
            );
        }
    }
}