package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    // =====================================
    // FIND USER BY EMAIL
    // =====================================

    Optional<User>
    findByEmail(
            String email
    );

    // =====================================
    // FIND USER BY MOBILE
    // =====================================

    Optional<User>
    findByMobileNumber(
            String mobileNumber
    );

    // =====================================
    // CHECK EMAIL
    // =====================================

    boolean existsByEmail(
            String email
    );

    // =====================================
    // CHECK MOBILE
    // =====================================

    boolean existsByMobileNumber(
            String mobileNumber
    );

    // =====================================
    // SEARCH ACTIVE USERS
    // =====================================

    @Query("""

        SELECT u
        FROM User u

        WHERE (

            LOWER(u.name)
            LIKE LOWER(CONCAT('%', :query, '%'))

            OR

            LOWER(u.email)
            LIKE LOWER(CONCAT('%', :query, '%'))

            OR

            u.mobileNumber
            LIKE CONCAT('%', :query, '%')
        )

        AND u.id <> :currentUserId

        AND u.active = true

        """)
    List<User> searchUsers(

            @Param("query")
            String query,

            @Param("currentUserId")
            Long currentUserId
    );

    // =====================================
    // FIND USER BY EMAIL OR MOBILE
    // =====================================

    Optional<User>
    findByEmailOrMobileNumber(

            String email,

            String mobileNumber
    );

    // =====================================
    // FIND USER BY COUNTRY CODE
    // AND MOBILE NUMBER
    // =====================================

    Optional<User>
    findByCountryCodeAndMobileNumber(

            String countryCode,

            String mobileNumber
    );

    // =====================================
    // CHECK COUNTRY CODE
    // AND MOBILE NUMBER
    // =====================================

    boolean existsByCountryCodeAndMobileNumber(

            String countryCode,

            String mobileNumber
    );

    // =====================================
    // FIND ACTIVE USER BY EMAIL
    // =====================================

    Optional<User>
    findByEmailAndActiveTrue(
            String email
    );

    // =====================================
    // FIND ACTIVE USER BY MOBILE
    // =====================================

    Optional<User>
    findByMobileNumberAndActiveTrue(
            String mobileNumber
    );

    // =====================================
    // FIND ACTIVE USER BY COUNTRY CODE
    // AND MOBILE NUMBER
    // =====================================

    Optional<User>
    findByCountryCodeAndMobileNumberAndActiveTrue(

            String countryCode,

            String mobileNumber
    );

    // =====================================
    // FIND ACTIVE USER BY EMAIL OR MOBILE
    // =====================================

    @Query("""
        SELECT u
        FROM User u

        WHERE u.active = true

          AND (
                u.email = :email

                OR

                u.mobileNumber = :mobileNumber
          )

        """)
    Optional<User>
    findActiveByEmailOrMobileNumber(

            @Param("email")
            String email,

            @Param("mobileNumber")
            String mobileNumber
    );

    // =====================================
    // CHECK ACTIVE COUNTRY CODE
    // AND MOBILE NUMBER
    // =====================================

    boolean
    existsByCountryCodeAndMobileNumberAndActiveTrue(

            String countryCode,

            String mobileNumber
    );
}