package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.AccessModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface
AccountAccessRepository

        extends
        JpaRepository
                <
                        AccountAccess,
                        Long
                        > {

    // =====================================
    // OWNER + MEMBER + MODULE
    // =====================================

    Optional<AccountAccess>
    findByOwnerAndMemberAndModule(

            User owner,

            User member,

            AccessModule module
    );

    Optional<AccountAccess>
    findByOwnerAndMemberAndModuleAndActiveTrue(

            User owner,

            User member,

            AccessModule module
    );

    // =====================================
    // OWNER
    // =====================================

    Optional<AccountAccess>
    findByOwnerAndModule(

            User owner,

            AccessModule module
    );

    Optional<AccountAccess>
    findByOwnerAndModuleAndActiveTrue(

            User owner,

            AccessModule module
    );

    // ADD THIS
    Optional<AccountAccess>
    findByOwnerAndModuleAndOtpVerifiedTrue(

            User owner,

            AccessModule module
    );

    // =====================================
    // MEMBER
    // =====================================

    List<AccountAccess>
    findAllByMemberAndActiveTrue(

            User member
    );

    // ADD THIS
    List<AccountAccess>
    findAllByMemberAndOtpVerifiedTrue(

            User member
    );

    Optional<AccountAccess>
    findByMemberAndActiveTrue(

            User member
    );

    List<AccountAccess>
    findAllByMemberAndModuleAndActiveTrue(

            User member,

            AccessModule module
    );

    Optional<AccountAccess>
    findByMemberAndOwnerAndModuleAndActiveTrue(

            User member,

            User owner,

            AccessModule module
    );

    // =====================================
    // OTP
    // =====================================

    Optional<AccountAccess>
    findByMemberAndOtpAndActiveTrue(

            User member,

            String otp
    );
}