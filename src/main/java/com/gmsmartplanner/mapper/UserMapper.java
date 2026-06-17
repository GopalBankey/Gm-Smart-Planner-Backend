package com.gmsmartplanner.mapper;

import com.gmsmartplanner.dto.request.MobileOnboardingDTO;
import com.gmsmartplanner.dto.request.UpdateProfileDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.LoginType;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // =====================================
    // UPDATE ONBOARDING DETAILS
    // =====================================


    public void updateOnboardingDetails(

            User user,

            UserAuth userAuth,

            MobileOnboardingDTO dto

    ) {

        // NAME

        if (

                dto.getName() != null
                        &&

                        !dto.getName().isBlank()

        ) {

            user.setName(

                    dto.getName()
            );
        }

        // =====================================
        // MOBILE LOGIN USER
        // =====================================

        if (

                userAuth.getLoginType()

                        ==

                        LoginType.MOBILE

        ) {

            if (

                    dto.getEmail() != null

                            &&

                            !dto.getEmail().isBlank()

            ) {

                user.setEmail(

                        dto.getEmail()
                );
            }
        }

        // =====================================
        // GOOGLE LOGIN USER
        // =====================================

        if (

                userAuth.getLoginType()

                        ==

                        LoginType.GOOGLE

        ) {

            if (

                    dto.getMobileNumber() != null

                            &&

                            !dto.getMobileNumber().isBlank()

            ) {

                user.setMobileNumber(

                        dto.getMobileNumber()
                );

                user.setCountryCode(

                        dto.getCountryCode() == null

                                ||

                                dto.getCountryCode().isBlank()

                                ?

                                "+91"

                                :

                                dto.getCountryCode()
                );
            }
        }
    }

    // =====================================
    // UPDATE PROFILE
    // =====================================
    public void updateProfile(

            User user,

            UpdateProfileDTO dto

    ) {

        // NAME
        if (dto.getName() != null
                && !dto.getName().isBlank()) {

            user.setName(
                    dto.getName()
            );
        }

        // DOB
        if (dto.getDob() != null) {

            user.setDob(
                    dto.getDob()
            );
        }

        // AGE
        if (dto.getAge() != null) {

            user.setAge(
                    dto.getAge()
            );
        }

        // BLOOD GROUP
        if (dto.getBloodGroup() != null) {

            user.setBloodGroup(
                    dto.getBloodGroup()
            );
        }

        // NOTE
        if (dto.getNote() != null
                && !dto.getNote().isBlank()) {

            user.setNote(
                    dto.getNote()
            );
        }
    }
}