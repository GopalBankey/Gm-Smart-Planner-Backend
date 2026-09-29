package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.response.health.InsuranceCardResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.InsuranceCard;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.InsuranceCardMapper;
import com.gmsmartplanner.repository.health.InsuranceCardRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.InsuranceCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InsuranceCardServiceImpl
        implements InsuranceCardService {

    private final InsuranceCardRepository insuranceCardRepository;

    private final UserHelperService userHelperService;

    private final InsuranceCardMapper insuranceCardMapper;

    private final FileUploadService fileUploadService;


    // ==========================================
    // CREATE INSURANCE CARD
    // ==========================================

    @Override
    public InsuranceCardResponseDTO createInsuranceCard(
            String username,
            CreateInsuranceCardRequestDTO dto
    ) {

        User user =
                userHelperService.getCurrentUser(username);

        InsuranceCard insuranceCard =
                new InsuranceCard();

        // ==========================================
        // USER
        // ==========================================

        insuranceCard.setUser(user);


        // ==========================================
        // INSURANCE / TPA NAME
        // ==========================================

        insuranceCard.setInsuranceTpaName(
                dto.getInsuranceTpaName().trim()
        );


        // ==========================================
        // CARD / INSURANCE NUMBER
        // ==========================================

        insuranceCard.setCardInsuranceNumber(
                dto.getCardInsuranceNumber().trim()
        );


        // ==========================================
        // CARD HOLDER NAME
        // ==========================================

        insuranceCard.setCardHolderName(
                dto.getCardHolderName().trim()
        );


        // ==========================================
        // VALID TILL
        // ==========================================

        insuranceCard.setValidTill(
                dto.getValidTill()
        );


        // ==========================================
        // NOTES
        // ==========================================

        insuranceCard.setNotes(
                dto.getNotes()
        );


        // ==========================================
        // CARD PHOTO
        // ==========================================

        if (
                dto.getCardPhoto() != null
                        &&
                        !dto.getCardPhoto().isEmpty()
        ) {

            insuranceCard.setCardPhoto(
                    uploadCardPhoto(
                            dto.getCardPhoto()
                    )
            );
        }


        // ==========================================
        // SAVE
        // ==========================================

        InsuranceCard saved =
                insuranceCardRepository.save(
                        insuranceCard
                );

        return insuranceCardMapper.toResponse(
                saved
        );
    }


    // ==========================================
    // GET ALL INSURANCE CARDS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<InsuranceCardResponseDTO> getInsuranceCards(
            String username
    ) {

        User user =
                userHelperService.getCurrentUser(username);

        return insuranceCardRepository
                .findAllByUserAndActiveTrueOrderByCreatedAtDesc(
                        user
                )
                .stream()
                .map(
                        insuranceCardMapper::toResponse
                )
                .toList();
    }


    // ==========================================
    // GET INSURANCE CARD BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public InsuranceCardResponseDTO getInsuranceCard(
            String username,
            Long insuranceCardId
    ) {

        User user =
                userHelperService.getCurrentUser(username);

        InsuranceCard insuranceCard =
                insuranceCardRepository
                        .findByIdAndUserAndActiveTrue(
                                insuranceCardId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Insurance card not found"
                                        )
                        );

        return insuranceCardMapper.toResponse(
                insuranceCard
        );
    }


    // ==========================================
    // UPDATE INSURANCE CARD
    // ==========================================

    @Override
    public InsuranceCardResponseDTO updateInsuranceCard(
            String username,
            Long insuranceCardId,
            UpdateInsuranceCardRequestDTO dto
    ) {

        User user =
                userHelperService.getCurrentUser(username);

        InsuranceCard insuranceCard =
                insuranceCardRepository
                        .findByIdAndUserAndActiveTrue(
                                insuranceCardId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Insurance card not found"
                                        )
                        );


        // ==========================================
        // INSURANCE / TPA NAME
        // ==========================================

        if (
                dto.getInsuranceTpaName() != null
                        &&
                        !dto.getInsuranceTpaName().isBlank()
        ) {

            insuranceCard.setInsuranceTpaName(
                    dto.getInsuranceTpaName().trim()
            );
        }


        // ==========================================
        // CARD / INSURANCE NUMBER
        // ==========================================

        if (
                dto.getCardInsuranceNumber() != null
                        &&
                        !dto.getCardInsuranceNumber().isBlank()
        ) {

            insuranceCard.setCardInsuranceNumber(
                    dto.getCardInsuranceNumber().trim()
            );
        }


        // ==========================================
        // CARD HOLDER NAME
        // ==========================================

        if (
                dto.getCardHolderName() != null
                        &&
                        !dto.getCardHolderName().isBlank()
        ) {

            insuranceCard.setCardHolderName(
                    dto.getCardHolderName().trim()
            );
        }


        // ==========================================
        // VALID TILL
        // ==========================================

        if (dto.getValidTill() != null) {

            insuranceCard.setValidTill(
                    dto.getValidTill()
            );
        }


        // ==========================================
        // NOTES
        // ==========================================

        if (dto.getNotes() != null) {

            insuranceCard.setNotes(
                    dto.getNotes()
            );
        }


        // ==========================================
        // CARD PHOTO
        // ==========================================

        if (
                dto.getCardPhoto() != null
                        &&
                        !dto.getCardPhoto().isEmpty()
        ) {

            insuranceCard.setCardPhoto(
                    uploadCardPhoto(
                            dto.getCardPhoto()
                    )
            );
        }


        // ==========================================
        // SAVE
        // ==========================================

        InsuranceCard updated =
                insuranceCardRepository.save(
                        insuranceCard
                );

        return insuranceCardMapper.toResponse(
                updated
        );
    }


    // ==========================================
    // DELETE INSURANCE CARD
    // ==========================================

    @Override
    public void deleteInsuranceCard(
            String username,
            Long insuranceCardId
    ) {

        User user =
                userHelperService.getCurrentUser(username);

        InsuranceCard insuranceCard =
                insuranceCardRepository
                        .findByIdAndUserAndActiveTrue(
                                insuranceCardId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Insurance card not found"
                                        )
                        );

        insuranceCard.setActive(false);

        insuranceCardRepository.save(
                insuranceCard
        );
    }


    // ==========================================
    // UPLOAD CARD PHOTO
    // ==========================================

    private String uploadCardPhoto(
            MultipartFile file
    ) {

        return fileUploadService.uploadImage(
                file,
                "insurance-cards"
        );
    }
}