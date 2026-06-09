package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.enums.todo.FriendRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendRequestDTO {

    private Long requestId;

    private FriendUserDTO sender;

    private FriendRequestStatus status;
}