package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "insurance_cards")
@Getter
@Setter
public class InsuranceCard extends BaseEntity {

    // ==========================================
    // PRIMARY KEY
    // ==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================
    // USER
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    // ==========================================
    // INSURANCE / TPA NAME
    // ==========================================

    @Column(
            name = "insurance_tpa_name",
            nullable = false
    )
    private String insuranceTpaName;


    // ==========================================
    // CARD / INSURANCE NUMBER
    // ==========================================

    @Column(
            name = "card_insurance_number",
            nullable = false
    )
    private String cardInsuranceNumber;


    // ==========================================
    // CARD HOLDER NAME
    // ==========================================

    @Column(
            name = "card_holder_name",
            nullable = false
    )
    private String cardHolderName;


    // ==========================================
    // VALID TILL
    // ==========================================

    @Column(
            name = "valid_till",
            nullable = false
    )
    private LocalDate validTill;


    // ==========================================
    // NOTES
    // ==========================================

    @Column(
            name = "notes",
            columnDefinition = "TEXT"
    )
    private String notes;


    // ==========================================
    // CARD PHOTO
    // ==========================================

    @Column(name = "card_photo")
    private String cardPhoto;


    // ==========================================
    // ACTIVE STATUS
    // ==========================================

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean active = true;
}