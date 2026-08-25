package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiPaymentHistoryResponseDTO;
import com.gmsmartplanner.dto.response.budget.EmiResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.budget.EmiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/api/v1/emis")
@RequiredArgsConstructor
public class EmiController {

    private final EmiService
            emiService;

    // =====================================
    // CREATE EMI
    // =====================================

    @PostMapping
    public ResponseEntity<
            ApiResponse<EmiResponseDTO>
            >
    createEmi(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateEmiRequestDTO dto

    ) {

        String username =
                authentication.getName();

        EmiResponseDTO response =

                emiService
                        .createEmi(

                                username,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI created successfully"
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }

    // =====================================
    // GET ALL EMIS
    // =====================================

    @GetMapping
    public ResponseEntity<
            ApiResponse<
                    List<EmiResponseDTO>
                    >
            >
    getEmis(

            Authentication authentication

    ) {

        String username =
                authentication.getName();

        List<EmiResponseDTO> response =

                emiService
                        .getEmis(
                                username
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<EmiResponseDTO>>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "EMIs fetched successfully"
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }

    // =====================================
    // GET EMI
    // =====================================

    @GetMapping(
            "/{emiId}"
    )
    public ResponseEntity<
            ApiResponse<EmiResponseDTO>
            >
    getEmi(

            Authentication authentication,

            @PathVariable
            Long emiId

    ) {

        String username =
                authentication.getName();

        EmiResponseDTO response =

                emiService
                        .getEmi(

                                username,

                                emiId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI fetched successfully"
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE EMI
    // =====================================

    @PatchMapping(
            "/{emiId}"
    )
    public ResponseEntity<
            ApiResponse<EmiResponseDTO>
            >
    updateEmi(

            Authentication authentication,

            @PathVariable
            Long emiId,

            @RequestBody
            UpdateEmiRequestDTO dto

    ) {

        String username =
                authentication.getName();

        EmiResponseDTO response =

                emiService
                        .updateEmi(

                                username,

                                emiId,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI updated successfully"
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE EMI
    // =====================================

    @DeleteMapping(
            "/{emiId}"
    )
    public ResponseEntity<
            ApiResponse<Void>
            >
    deleteEmi(

            Authentication authentication,

            @PathVariable
            Long emiId

    ) {

        String username =
                authentication.getName();

        emiService
                .deleteEmi(

                        username,

                        emiId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI deleted successfully"
                        )

                        .build()
        );
    }


    // =====================================
// PAY EMI
// =====================================

    @PostMapping(
            "/{emiId}/pay"
    )
    public ResponseEntity<
            ApiResponse<Void>
            >
    payEmi(

            Authentication authentication,

            @PathVariable
            Long emiId

    ) {

        String username =
                authentication.getName();

        emiService
                .payEmi(

                        username,

                        emiId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI paid successfully."
                        )

                        .build()
        );
    }

// =====================================
// EMI PAYMENT HISTORY
// =====================================

    @GetMapping(
            "/{emiId}/payment-history"
    )
    public ResponseEntity<
            ApiResponse<
                    List<EmiPaymentHistoryResponseDTO>
                    >
            >
    getPaymentHistory(

            Authentication authentication,

            @PathVariable
            Long emiId

    ) {

        String username =
                authentication.getName();

        List<EmiPaymentHistoryResponseDTO> response =

                emiService
                        .getPaymentHistory(

                                username,

                                emiId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<EmiPaymentHistoryResponseDTO>>builder()

                        .success(
                                true
                        )

                        .message(
                                "EMI payment history fetched successfully."
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }
}