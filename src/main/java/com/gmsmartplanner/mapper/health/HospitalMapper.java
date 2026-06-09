package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateHospitalRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateHospitalRequestDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.entity.health.Hospital;
import org.springframework.stereotype.Component;

@Component
public class HospitalMapper {

    // =====================================
    // CREATE
    // =====================================

    public Hospital createHospital(
            CreateHospitalRequestDTO dto
    ) {

        Hospital hospital =
                new Hospital();

        hospital.setHospitalName(
                dto.getHospitalName()
        );

        hospital.setCountryCode(

                dto.getCountryCode() == null
                        || dto.getCountryCode().isBlank()

                        ?

                        "+91"

                        :

                        dto.getCountryCode()
        );

        hospital.setMobileNumber(
                dto.getMobileNumber()
        );

        hospital.setOpeningTime(
                dto.getOpeningTime()
        );

        hospital.setClosingTime(
                dto.getClosingTime()
        );

        hospital.setAddress(
                dto.getAddress()
        );

        hospital.setActive(
                true
        );

        return hospital;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateHospital(

            Hospital hospital,

            UpdateHospitalRequestDTO dto

    ) {

        if (dto.getHospitalName() != null) {

            hospital.setHospitalName(
                    dto.getHospitalName()
            );
        }

        if (dto.getCountryCode() != null
                && !dto.getCountryCode().isBlank()) {

            hospital.setCountryCode(
                    dto.getCountryCode()
            );
        }

        if (dto.getMobileNumber() != null) {

            hospital.setMobileNumber(
                    dto.getMobileNumber()
            );
        }

        if (dto.getOpeningTime() != null) {

            hospital.setOpeningTime(
                    dto.getOpeningTime()
            );
        }

        if (dto.getClosingTime() != null) {

            hospital.setClosingTime(
                    dto.getClosingTime()
            );
        }

        if (dto.getAddress() != null) {

            hospital.setAddress(
                    dto.getAddress()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public HospitalResponseDTO mapToResponse(

            Hospital hospital

    ) {

        return HospitalResponseDTO
                .builder()

                .id(
                        hospital.getId()
                )

                .hospitalName(
                        hospital.getHospitalName()
                )

                .countryCode(
                        hospital.getCountryCode()
                )

                .mobileNumber(
                        hospital.getMobileNumber()
                )

                .openingTime(
                        hospital.getOpeningTime()
                )

                .closingTime(
                        hospital.getClosingTime()
                )

                .address(
                        hospital.getAddress()
                )

                .build();
    }
}