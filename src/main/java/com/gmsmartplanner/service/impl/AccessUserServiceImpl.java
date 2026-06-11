package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.repository.AccountAccessRepository;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessUserServiceImpl
        implements AccessUserService {

    private final UserHelperService
            userHelperService;

    private final AccountAccessRepository
            repository;

    // =====================================
    // EFFECTIVE USER
    // =====================================
    @Override
    public String
    getEffectiveUsername(

            String username,

            Long accessId

    ) {

        if (

                accessId == null

        ) {

            return username;
        }

        return String.valueOf(

                getAccess(

                        username,

                        accessId
                )

                        .getOwner()

                        .getId()
        );
    }

    // =====================================
    // VIEW
    // =====================================

    @Override
    public void
    checkViewPermission(

            String username,

            Long accessId

    ) {

        validate(

                username,

                accessId,

                Permission.VIEW
        );
    }

    // =====================================
    // CREATE
    // =====================================

    @Override
    public void
    checkCreatePermission(

            String username,

            Long accessId

    ) {

        validate(

                username,

                accessId,

                Permission.CREATE
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public void
    checkUpdatePermission(

            String username,

            Long accessId

    ) {

        validate(

                username,

                accessId,

                Permission.UPDATE
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void
    checkDeletePermission(

            String username,

            Long accessId

    ) {

        validate(

                username,

                accessId,

                Permission.DELETE
        );
    }

    // =====================================
    // TAKE
    // =====================================

    @Override
    public void
    checkTakePermission(

            String username,

            Long accessId

    ) {

        validate(

                username,

                accessId,

                Permission.TAKE
        );
    }

    // =====================================
    // VALIDATE
    // =====================================

    private void
    validate(

            String username,

            Long accessId,

            Permission permission

    ) {

        if (

                accessId == null

        ) {

            return;
        }

        AccountAccess access =

                getAccess(

                        username,

                        accessId
                );

        boolean allowed =

                switch (
                        permission
                        ) {

                    case VIEW ->

                            Boolean.TRUE.equals(

                                    access
                                            .getViewPermission()
                            );

                    case CREATE ->

                            Boolean.TRUE.equals(

                                    access
                                            .getCreatePermission()
                            );

                    case UPDATE ->

                            Boolean.TRUE.equals(

                                    access
                                            .getUpdatePermission()
                            );

                    case DELETE ->

                            Boolean.TRUE.equals(

                                    access
                                            .getDeletePermission()
                            );

                    case TAKE ->

                            Boolean.TRUE.equals(

                                    access
                                            .getTakePermission()
                            );
                };

        if (

                !allowed

        ) {

            throw new InvalidRequestException(

                    "Permission denied"
            );
        }
    }

    // =====================================
    // GET ACCESS
    // =====================================

    private AccountAccess
    getAccess(

            String username,

            Long accessId

    ) {

        User member =

                userHelperService

                        .getCurrentUser(
                                username
                        );

        AccountAccess access =

                repository

                        .findById(
                                accessId
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "Access not found"
                                        )
                        );

        if (

                !access

                        .getMember()

                        .getId()

                        .equals(

                                member
                                        .getId()
                        )

        ) {

            throw new InvalidRequestException(

                    "Invalid access"
            );
        }

        if (

                !Boolean.TRUE.equals(

                        access
                                .getOtpVerified()
                )

        ) {

            throw new InvalidRequestException(

                    "Access not verified"
            );
        }

        if (

                !Boolean.TRUE.equals(

                        access
                                .getActive()
                )

        ) {

            throw new InvalidRequestException(

                    "Access inactive"
            );
        }

        return access;
    }

    private enum Permission {

        VIEW,

        CREATE,

        UPDATE,

        DELETE,

        TAKE
    }

}