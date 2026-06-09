package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository
        extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUser(User user);

    Optional<UserAuth> findByFirebaseUid(
            String firebaseUid
    );


}