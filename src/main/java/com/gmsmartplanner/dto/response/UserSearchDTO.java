package com.gmsmartplanner.dto.response;

import com.gmsmartplanner.enums.todo.FriendshipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSearchDTO {

    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private String profileImageUrl;

    private FriendshipStatus friendshipStatus;
}