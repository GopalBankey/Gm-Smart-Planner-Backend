package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.budget.PaymentMethod;
import com.gmsmartplanner.enums.todo.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethod, Long> {

    // =====================================
    // GET PAYMENT METHODS
    // =====================================

    List<PaymentMethod>
    findAllByTypeInAndActiveTrue(
            List<CategoryType> types
    );

    List<PaymentMethod>
    findAllByActiveTrue();
}