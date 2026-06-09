package com.gmsmartplanner.dto.response.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendDTO {

    private Long friendshipId;

    private FriendUserDTO friend;
}