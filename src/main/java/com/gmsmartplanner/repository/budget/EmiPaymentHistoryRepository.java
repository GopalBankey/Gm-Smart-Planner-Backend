package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.budget.Emi;
import com.gmsmartplanner.entity.budget.EmiPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface EmiPaymentHistoryRepository
        extends JpaRepository<EmiPaymentHistory, Long> {

    Optional<EmiPaymentHistory>
    findByEmiAndPaymentMonthAndPaymentYear(

            Emi emi,

            Integer paymentMonth,

            Integer paymentYear
    );

    List<EmiPaymentHistory>
    findAllByEmiOrderByPaymentDateDesc(

            Emi emi
    );
}