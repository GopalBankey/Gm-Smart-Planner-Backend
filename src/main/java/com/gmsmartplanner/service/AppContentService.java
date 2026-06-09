package com.gmsmartplanner.service;

import com.gmsmartplanner.dto.response.AppContentResponseDTO;

public interface AppContentService {

    AppContentResponseDTO
    getAboutUs(

            String languageCode
    );

    AppContentResponseDTO
    getPrivacyPolicy(

            String languageCode
    );

    AppContentResponseDTO
    getTermsAndConditions(

            String languageCode
    );
}