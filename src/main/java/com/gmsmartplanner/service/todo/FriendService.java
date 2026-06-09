package com.gmsmartplanner.service.todo;

import com.gmsmartplanner.dto.response.todo.FriendDTO;
import com.gmsmartplanner.dto.response.todo.FriendRequestDTO;
import com.gmsmartplanner.dto.response.UserSearchDTO;

import java.util.List;

public interface FriendService {

    // =====================================
    // SEND FRIEND REQUEST
    // =====================================

    void sendFriendRequest(

            String username,

            Long receiverId
    );

    // =====================================
    // ACCEPT FRIEND REQUEST
    // =====================================

    void acceptFriendRequest(

            String username,

            Long requestId
    );

    // =====================================
    // REJECT FRIEND REQUEST
    // =====================================

    void rejectFriendRequest(

            String username,

            Long requestId
    );

    // =====================================
    // CANCEL FRIEND REQUEST
    // =====================================

    void cancelFriendRequest(

            String username,

            Long receiverId
    );

    // =====================================
    // REMOVE FRIEND
    // =====================================

    void removeFriend(

            String username,

            Long friendId
    );

    // =====================================
    // GET FRIEND REQUESTS
    // =====================================

    List<FriendRequestDTO>
    getFriendRequests(
            String username
    );

    // =====================================
    // GET FRIENDS
    // =====================================

    List<FriendDTO>
    getFriends(
            String username
    );

    List<UserSearchDTO>
    searchUsers(

            String username,

            String query
    );
}