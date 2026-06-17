package com.gmsmartplanner.mapper;

import com.gmsmartplanner.dto.response.UserResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponseDTO mapToUserResponse(

            User user,

            UserAuth auth

    ) {

        return UserResponseDTO.builder()

                .id(user.getId())

                .name(user.getName())

                .email(user.getEmail())

                .mobileNumber(user.getMobileNumber())

                .profileImageUrl(
                        user.getProfileImageUrl()
                )

                .dob(user.getDob())

                .age(user.getAge())

                .bloodGroup(
                        user.getBloodGroup()
                )

                .note(user.getNote())

                .profileCompleted(
                        user.isProfileCompleted()
                )

                // USER AUTH
                .loginType(
                        auth.getLoginType()
                )

                .emailVerified(
                        auth.isEmailVerified()
                )

                .mobileVerified(
                        auth.isOtpVerified()
                )

                .firebaseUid(
                        auth.getFirebaseUid()
                )
                .countryCode(
                        user.getCountryCode()
                )

                .createdAt(
                        user.getCreatedAt()
                )

                .build();
    }
}