package com.gmsmartplanner.dto.response.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendUserDTO {

    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private String profileImageUrl;
}