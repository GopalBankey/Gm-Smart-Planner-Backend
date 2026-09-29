package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.AllergySeverity;
import com.gmsmartplanner.enums.health.ReactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAllergyRequestDTO {

    @NotBlank
    private String allergicTo;

    @NotNull
    private ReactionType reaction;

    private String otherReaction;

    @NotNull
    private AllergySeverity severity;

    private String doctorAdvice;
}