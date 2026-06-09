package com.gmsmartplanner.mapper;

import com.gmsmartplanner.dto.response.LoginResponseDTO;
import com.gmsmartplanner.dto.response.UserResponseDTO;
import com.gmsmartplanner.entity.RefreshToken;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final UserResponseMapper
            userResponseMapper;

    // =====================================
    // CREATE MOBILE USER
    // =====================================
    public User createMobileUser(
            String mobileNumber
    ) {

        User user = new User();

        user.setMobileNumber(
                mobileNumber
        );

        user.setProfileCompleted(false);

        return user;
    }

    // =====================================
    // CREATE GOOGLE USER
    // =====================================
    public User createGoogleUser(

            String email,

            String name,

            String profileImageUrl

    ) {

        User user = new User();

        user.setEmail(email);

        user.setName(name);

        user.setProfileImageUrl(
                profileImageUrl
        );

        user.setProfileCompleted(false);

        return user;
    }

    // =====================================
    // CREATE USER AUTH
    // =====================================
    public UserAuth createUserAuth(

            User user,

            LoginType loginType,

            String fcmToken,

            String firebaseUid

    ) {

        UserAuth userAuth =
                new UserAuth();

        userAuth.setUser(user);

        userAuth.setLoginType(
                loginType
        );

        userAuth.setFcmToken(
                fcmToken
        );

        userAuth.setFirebaseUid(
                firebaseUid
        );

        userAuth.setOtpVerified(false);

        userAuth.setEmailVerified(false);

        return userAuth;
    }

    // =====================================
    // UPDATE FCM TOKEN
    // =====================================
    public void updateFcmToken(

            UserAuth userAuth,

            String fcmToken

    ) {

        userAuth.setFcmToken(
                fcmToken
        );
    }

    // =====================================
    // BUILD LOGIN RESPONSE
    // =====================================
    public LoginResponseDTO
    buildLoginResponse(

            String accessToken,

            RefreshToken refreshToken,

            User user,

            UserAuth userAuth,

            String message

    ) {

        UserResponseDTO userResponse =
                userResponseMapper
                        .mapToUserResponse(

                                user,

                                userAuth
                        );

        return LoginResponseDTO.builder()

                .accessToken(accessToken)

                .refreshToken(
                        refreshToken.getToken()
                )

                .user(userResponse)

                .message(message)

                .build();
    }
}