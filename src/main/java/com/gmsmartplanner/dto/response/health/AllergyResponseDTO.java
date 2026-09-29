package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.AllergySeverity;
import com.gmsmartplanner.enums.health.ReactionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllergyResponseDTO {

    private Long id;

    private String allergicTo;

    private ReactionType reaction;

    private String otherReaction;

    private AllergySeverity severity;

    private String doctorAdvice;

    private boolean active;
}