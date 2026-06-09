package com.gmsmartplanner.controller;

import com.gmsmartplanner.dto.response.AppContentResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AppContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/app"
)
public class AppContentController {

    private final
    AppContentService
            service;

    @GetMapping(
            "/about-us"
    )
    public ResponseEntity<
            ApiResponse<
                    AppContentResponseDTO
                    >
            >
    getAboutUs(

            @RequestParam
            String languageCode

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<AppContentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "About us fetched successfully"
                        )

                        .data(

                                service
                                        .getAboutUs(
                                                languageCode
                                        )
                        )

                        .build()
        );
    }

    @GetMapping(
            "/privacy-policy"
    )
    public ResponseEntity<
            ApiResponse<
                    AppContentResponseDTO
                    >
            >
    getPrivacy(

            @RequestParam
            String languageCode

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<AppContentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Privacy policy fetched successfully"
                        )

                        .data(

                                service
                                        .getPrivacyPolicy(
                                                languageCode
                                        )
                        )

                        .build()
        );
    }

    @GetMapping(
            "/terms-and-conditions"
    )
    public ResponseEntity<
            ApiResponse<
                    AppContentResponseDTO
                    >
            >
    getTerms(

            @RequestParam
            String languageCode

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<AppContentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Terms and conditions fetched successfully"
                        )

                        .data(

                                service
                                        .getTermsAndConditions(
                                                languageCode
                                        )
                        )

                        .build()
        );
    }
}