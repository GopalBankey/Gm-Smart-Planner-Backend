package com.gmsmartplanner.mapper.todo;

import com.gmsmartplanner.dto.response.todo.FriendDTO;
import com.gmsmartplanner.dto.response.todo.FriendRequestDTO;
import com.gmsmartplanner.dto.response.todo.FriendUserDTO;
import com.gmsmartplanner.dto.response.UserSearchDTO;
import com.gmsmartplanner.entity.todo.FriendRequest;
import com.gmsmartplanner.entity.todo.Friendship;
import com.gmsmartplanner.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper {

    // =====================================
    // MAP FRIEND REQUEST DTO
    // =====================================
    public FriendRequestDTO
    mapToFriendRequestDTO(
            FriendRequest request
    ) {

        return FriendRequestDTO.builder()

                .requestId(
                        request.getId()
                )

                .status(
                        request.getStatus()
                )

                .sender(
                        mapToFriendUserDTO(
                                request.getSender()
                        )
                )

                .build();
    }

    // =====================================
    // MAP FRIEND DTO
    // =====================================
    public FriendDTO
    mapToFriendDTO(
            Friendship friendship
    ) {

        return FriendDTO.builder()

                .friendshipId(
                        friendship.getId()
                )

                .friend(
                        mapToFriendUserDTO(
                                friendship.getFriend()
                        )
                )

                .build();
    }

    // =====================================
    // MAP FRIEND USER DTO
    // =====================================
    public FriendUserDTO
    mapToFriendUserDTO(
            User user
    ) {

        return FriendUserDTO.builder()

                .id(user.getId())

                .name(user.getName())

                .email(user.getEmail())

                .mobileNumber(
                        user.getMobileNumber()
                )

                .profileImageUrl(
                        user.getProfileImageUrl()
                )

                .build();
    }

    // =====================================
    // MAP USER SEARCH DTO
    // =====================================
    public UserSearchDTO
    mapToUserSearchDTO(
            User user
    ) {

        return UserSearchDTO.builder()

                .id(user.getId())

                .name(user.getName())

                .email(user.getEmail())

                .mobileNumber(
                        user.getMobileNumber()
                )

                .profileImageUrl(
                        user.getProfileImageUrl()
                )

                .build();
    }
}