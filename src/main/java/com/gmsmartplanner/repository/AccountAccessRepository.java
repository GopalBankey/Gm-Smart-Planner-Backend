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
    // OWNER ALREADY SHARED
    // =====================================

    boolean
    existsByOwnerAndModuleAndActiveTrue(

            User owner,

            AccessModule module
    );

    // =====================================
    // OWNER ACCESS
    // =====================================

    Optional<AccountAccess>
    findByOwnerAndModuleAndActiveTrue(

            User owner,

            AccessModule module
    );

    // =====================================
    // OWNER + MEMBER
    // =====================================

    Optional<AccountAccess>
    findByOwnerAndMemberAndModule(

            User owner,

            User member,

            AccessModule module
    );

    // =====================================
    // MEMBER ALL ACCESS
    // =====================================

    List<AccountAccess>
    findAllByMemberAndActiveTrue(

            User member
    );

    // =====================================
    // VERIFIED
    // =====================================

    Optional<AccountAccess>
    findByMemberAndOtpAndActiveTrue(

            User member,

            String otp
    );

    // =====================================
    // MEMBER + MODULE
    // =====================================

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

    Optional<AccountAccess>
    findByMemberAndActiveTrue(

            User member
    );

    Optional<AccountAccess>
    findByOwnerAndMemberAndModuleAndActiveTrue(

            User owner,

            User member,

            AccessModule module
    );
}