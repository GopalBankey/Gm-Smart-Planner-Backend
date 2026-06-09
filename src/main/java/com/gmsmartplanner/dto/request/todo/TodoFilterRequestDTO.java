package com.gmsmartplanner.dto.request.todo;

import com.gmsmartplanner.enums.FilterMode;
import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoFilterRequestDTO {

    // =====================================
    // FILTER MODE
    // =====================================

    private FilterMode filterMode;

    // =====================================
    // DAILY
    // =====================================

    private LocalDate selectedDate;

    // =====================================
    // MONTHLY
    // =====================================

    private Integer selectedMonth;

    private Integer selectedMonthYear;

    // =====================================
    // YEARLY
    // =====================================

    private Integer selectedYear;

    // =====================================
    // RANGE
    // =====================================

    private LocalDate startDate;

    private LocalDate endDate;

    // =====================================
    // PRIORITY
    // =====================================

    private TodoPriority priority;

    // =====================================
    // STATUS
    // =====================================

    private TodoStatus status;

    // =====================================
    // OWN / SHARED
    // =====================================

    private Boolean includeOwn = true;

    private Boolean includeShared = true;
}