package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(
            String email
    );

    Optional<User> findByMobileNumber(
            String mobileNumber
    );

    boolean existsByEmail(
            String email
    );

    boolean existsByMobileNumber(
            String mobileNumber
    );

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

        """)
    List<User> searchUsers(

            @Param("query")
            String query,

            @Param("currentUserId")
            Long currentUserId
    );

    Optional<User> findByEmailOrMobileNumber(

            String email,

            String mobileNumber
    );

    Optional<User>
    findByCountryCodeAndMobileNumber(

            String countryCode,

            String mobileNumber
    );

    boolean existsByCountryCodeAndMobileNumber(

            String countryCode,

            String mobileNumber
    );
}