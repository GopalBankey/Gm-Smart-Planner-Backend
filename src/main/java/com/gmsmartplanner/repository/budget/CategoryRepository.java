package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.enums.todo.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {


    List<Category>
    findAllByTypeInAndActiveTrue(
            List<CategoryType> types
    );
    List<Category>
    findAllByActiveTrue();
}