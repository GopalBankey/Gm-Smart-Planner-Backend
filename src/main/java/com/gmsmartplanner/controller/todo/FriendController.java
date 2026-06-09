package com.gmsmartplanner.controller.todo;

import com.gmsmartplanner.dto.response.todo.FriendDTO;
import com.gmsmartplanner.dto.response.todo.FriendRequestDTO;
import com.gmsmartplanner.dto.response.UserSearchDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.todo.FriendService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/friends",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
public class FriendController {

    private final FriendService
            friendService;

    // =====================================
    // SEND FRIEND REQUEST
    // =====================================
    @PostMapping("/request/{receiverId}")
    public ResponseEntity<ApiResponse<String>>
    sendFriendRequest(

            Authentication authentication,

            @PathVariable
            @Positive(message = "Receiver id must be valid")
            Long receiverId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Sending friend request from user : {} to user : {}",
                username,
                receiverId
        );

        friendService.sendFriendRequest(

                username,

                receiverId
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Friend request sent successfully"
                        )
                        .data("Request sent")
                        .build()
        );
    }

    // =====================================
    // ACCEPT FRIEND REQUEST
    // =====================================
    @PostMapping("/request/{requestId}/accept")
    public ResponseEntity<ApiResponse<String>>
    acceptFriendRequest(

            Authentication authentication,

            @PathVariable
            @Positive(message = "Request id must be valid")
            Long requestId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Accepting friend request : {}",
                requestId
        );

        friendService.acceptFriendRequest(

                username,

                requestId
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Friend request accepted successfully"
                        )
                        .data("Friend request accepted")
                        .build()
        );
    }

    // =====================================
    // REJECT FRIEND REQUEST
    // =====================================
    @PostMapping("/request/{requestId}/reject")
    public ResponseEntity<ApiResponse<String>>
    rejectFriendRequest(

            Authentication authentication,

            @PathVariable
            @Positive(message = "Request id must be valid")
            Long requestId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Rejecting friend request : {}",
                requestId
        );

        friendService.rejectFriendRequest(

                username,

                requestId
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Friend request rejected successfully"
                        )
                        .data("Friend request rejected")
                        .build()
        );
    }

    // =====================================
    // CANCEL FRIEND REQUEST
    // =====================================
    @DeleteMapping("/request/{receiverId}/cancel")
    public ResponseEntity<ApiResponse<String>>
    cancelFriendRequest(

            Authentication authentication,

            @PathVariable
            @Positive(message = "Receiver id must be valid")
            Long receiverId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Cancelling friend request to user : {}",
                receiverId
        );

        friendService.cancelFriendRequest(

                username,

                receiverId
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Friend request cancelled successfully"
                        )
                        .data("Friend request cancelled")
                        .build()
        );
    }

    // =====================================
    // GET FRIEND REQUESTS
    // =====================================
    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<FriendRequestDTO>>>
    getFriendRequests(

            Authentication authentication

    ) {

        String username =
                authentication.getName();

        log.info(
                "Fetching friend requests for user : {}",
                username
        );

        List<FriendRequestDTO> requests =
                friendService.getFriendRequests(
                        username
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse
                        .<List<FriendRequestDTO>>builder()
                        .success(true)
                        .message(
                                "Friend requests fetched successfully"
                        )
                        .data(requests)
                        .build()
        );
    }

    // =====================================
    // GET FRIENDS
    // =====================================
    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendDTO>>>
    getFriends(

            Authentication authentication

    ) {

        String username =
                authentication.getName();

        log.info(
                "Fetching friends for user : {}",
                username
        );

        List<FriendDTO> friends =
                friendService.getFriends(
                        username
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse
                        .<List<FriendDTO>>builder()
                        .success(true)
                        .message(
                                "Friends fetched successfully"
                        )
                        .data(friends)
                        .build()
        );
    }

    // =====================================
    // REMOVE FRIEND
    // =====================================
    @DeleteMapping("/{friendId}")
    public ResponseEntity<ApiResponse<String>>
    removeFriend(

            Authentication authentication,

            @PathVariable
            @Positive(message = "Friend id must be valid")
            Long friendId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Removing friend : {}",
                friendId
        );

        friendService.removeFriend(

                username,

                friendId
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Friend removed successfully"
                        )
                        .data("Friend removed")
                        .build()
        );
    }

    // =====================================
// SEARCH USERS
// =====================================
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserSearchDTO>>>
    searchUsers(

            Authentication authentication,

            @RequestParam
            String query

    ) {

        String username =
                authentication.getName();

        log.info(
                "Searching users for query : {}",
                query
        );

        List<UserSearchDTO> users =
                friendService.searchUsers(

                        username,

                        query
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse
                        .<List<UserSearchDTO>>builder()
                        .success(true)
                        .message(
                                "Users fetched successfully"
                        )
                        .data(users)
                        .build()
        );
    }
}