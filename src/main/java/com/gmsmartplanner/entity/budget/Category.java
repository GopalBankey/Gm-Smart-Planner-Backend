package com.gmsmartplanner.entity.budget;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.enums.todo.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // FILE NAME / PATH
    private String icon;

    private String colorCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(nullable = false)
    private boolean active = true;
}