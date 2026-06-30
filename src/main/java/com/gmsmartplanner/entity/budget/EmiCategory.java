package com.gmsmartplanner.entity.emi;

import com.gmsmartplanner.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "emi_categories"
)
@Getter
@Setter
public class EmiCategory
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // CATEGORY NAME
    // =====================================

    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String name;

    // =====================================
    // ICON
    // =====================================

    @Column(
            length = 255
    )
    private String icon;

    // =====================================
    // STATUS
    // =====================================

    @Column(
            nullable = false,
            name = "is_active"
    )
    private boolean active =
            true;
}