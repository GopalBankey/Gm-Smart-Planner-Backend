package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.AllergySeverity;
import com.gmsmartplanner.enums.health.ReactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAllergyRequestDTO {

    private String allergicTo;

    private ReactionType reaction;

    private String otherReaction;

    private AllergySeverity severity;

    private String doctorAdvice;
}