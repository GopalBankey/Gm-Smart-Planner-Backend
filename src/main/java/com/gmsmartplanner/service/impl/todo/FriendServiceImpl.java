package com.gmsmartplanner.service.impl.todo;

import com.gmsmartplanner.dto.response.todo.FriendDTO;
import com.gmsmartplanner.dto.response.todo.FriendRequestDTO;
import com.gmsmartplanner.dto.response.UserSearchDTO;
import com.gmsmartplanner.entity.todo.FriendRequest;
import com.gmsmartplanner.entity.todo.Friendship;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.todo.FriendRequestStatus;
import com.gmsmartplanner.enums.todo.FriendshipStatus;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.todo.FriendMapper;
import com.gmsmartplanner.repository.todo.FriendRequestRepository;
import com.gmsmartplanner.repository.todo.FriendshipRepository;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.todo.FriendService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendServiceImpl
        implements FriendService {

    private final UserRepository
            userRepository;

    private final FriendRequestRepository
            friendRequestRepository;

    private final FriendshipRepository
            friendshipRepository;

    private final FriendMapper
            friendMapper;

    private final UserHelperService
            userHelperService;

    private final NotificationHelperService
            notificationHelperService;

    private final FirebaseNotificationService
            firebaseNotificationService;

    private final UserAuthRepository
            userAuthRepository;

    // =====================================
    // SEND FRIEND REQUEST
    // =====================================
    @Override
    @Transactional
    public void sendFriendRequest(

            String username,

            Long receiverId

    ) {

        User sender =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        User receiver =

                getUserById(
                        receiverId
                );

        // SELF REQUEST
        if (

                sender.getId()

                        .equals(

                                receiver.getId()
                        )

        ) {

            throw new InvalidRequestException(

                    "You cannot send request to yourself"
            );
        }

        // ALREADY FRIEND
        if (

                friendshipRepository

                        .existsByUserAndFriend(

                                sender,

                                receiver
                        )

        ) {

            throw new InvalidRequestException(

                    "User is already your friend"
            );
        }

        // REVERSE REQUEST
        if (

                friendRequestRepository

                        .existsBySenderAndReceiverAndStatus(

                                receiver,

                                sender,

                                FriendRequestStatus.PENDING
                        )

        ) {

            throw new InvalidRequestException(

                    "This user already sent you a friend request"
            );
        }

        FriendRequest request =

                friendRequestRepository

                        .findBySenderAndReceiver(

                                sender,

                                receiver
                        )

                        .orElse(
                                null
                        );

        // EXISTING REQUEST
        if (

                request != null

        ) {

            switch (

                    request.getStatus()

            ) {

                case PENDING ->

                        throw new InvalidRequestException(

                                "Friend request already sent"
                        );

                case ACCEPTED ->

                        throw new InvalidRequestException(

                                "User is already your friend"
                        );

                case CANCELLED,
                     REJECTED -> {

                    request.setStatus(

                            FriendRequestStatus.PENDING
                    );

                    friendRequestRepository
                            .save(
                                    request
                            );
                }
            }

        } else {

            request =
                    new FriendRequest();

            request.setSender(
                    sender
            );

            request.setReceiver(
                    receiver
            );

            request.setStatus(

                    FriendRequestStatus.PENDING
            );

            friendRequestRepository
                    .save(
                            request
                    );
        }

        sendNotification(

                receiver,

                "Friend Request",

                sender.getName()
                        +
                        " sent you a friend request",

                sender.getId(),

                NotificationType.FRIEND_REQUEST
        );

        log.info(

                "Friend request sent from {} to {}",

                sender.getId(),

                receiver.getId()
        );
    }
    // =====================================
    // ACCEPT FRIEND REQUEST
    // =====================================
    @Override
    public void acceptFriendRequest(

            String username,

            Long requestId

    ) {

        User currentUser =
                  userHelperService
                .getCurrentUser(
                        username
                );

        FriendRequest request =
                getFriendRequest(requestId);

        // VALIDATE RECEIVER
        if (!request.getReceiver()
                .getId()
                .equals(currentUser.getId())) {

            throw new InvalidRequestException(
                    "You are not authorized to accept this request"
            );
        }

        // VALIDATE STATUS
        if (request.getStatus()
                != FriendRequestStatus.PENDING) {

            throw new InvalidRequestException(
                    "Invalid friend request status"
            );
        }

        User sender =
                request.getSender();

        User receiver =
                request.getReceiver();

        // ALREADY FRIENDS
        boolean alreadyFriends =
                friendshipRepository
                        .existsByUserAndFriend(
                                sender,
                                receiver
                        );

        if (alreadyFriends) {

            throw new InvalidRequestException(
                    "Users are already friends"
            );
        }

        // CREATE BOTH SIDE FRIENDSHIPS
        createFriendship(
                sender,
                receiver
        );

        createFriendship(
                receiver,
                sender
        );

        // UPDATE REQUEST STATUS
        request.setStatus(
                FriendRequestStatus.ACCEPTED
        );

        friendRequestRepository.save(request);

        sendNotification(

                sender,

                "Friend Request Accepted",

                receiver.getName()
                        + " accepted your friend request",

                receiver.getId(),

                NotificationType.FRIEND_REQUEST_ACCEPTED
        );

        log.info(
                "Friend request accepted : {}",
                requestId
        );
    }

    // =====================================
    // REJECT FRIEND REQUEST
    // =====================================
    @Override
    public void rejectFriendRequest(

            String username,

            Long requestId

    ) {

        User currentUser =
                  userHelperService
                .getCurrentUser(
                        username
                );

        FriendRequest request =
                getFriendRequest(requestId);

        if (!request.getReceiver()
                .getId()
                .equals(currentUser.getId())) {

            throw new InvalidRequestException(
                    "You are not authorized to reject this request"
            );
        }

        request.setStatus(
                FriendRequestStatus.REJECTED
        );

        friendRequestRepository.save(request);

        log.info(
                "Friend request rejected : {}",
                requestId
        );
    }

    // =====================================
    // CANCEL FRIEND REQUEST
    // =====================================
    @Override
    public void cancelFriendRequest(

            String username,

            Long receiverId

    ) {

        User sender =
                  userHelperService
                .getCurrentUser(
                        username
                );

        User receiver =
                getUserById(receiverId);

        FriendRequest request =
                friendRequestRepository
                        .findBySenderAndReceiver(
                                sender,
                                receiver
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Friend request not found"
                                )
                        );

        if (request.getStatus()
                != FriendRequestStatus.PENDING) {

            throw new InvalidRequestException(
                    "Cannot cancel this request"
            );
        }

        request.setStatus(
                FriendRequestStatus.CANCELLED
        );

        friendRequestRepository.save(request);

        log.info(
                "Friend request cancelled"
        );
    }

    @Override
    public void removeFriend(

            String username,

            Long friendId

    ) {

        User currentUser =
                userHelperService
                        .getCurrentUser(username);

        User friend =
                getUserById(friendId);

        boolean isFriend =
                friendshipRepository
                        .existsByUserAndFriend(
                                currentUser,
                                friend
                        );

        if (!isFriend) {

            throw new InvalidRequestException(
                    "User is not your friend"
            );
        }

        // DELETE BOTH SIDE FRIENDSHIPS

        friendshipRepository
                .deleteByUserAndFriend(
                        currentUser,
                        friend
                );

        friendshipRepository
                .deleteByUserAndFriend(
                        friend,
                        currentUser
                );

        // DELETE OLD FRIEND REQUEST

        friendRequestRepository
                .findBySenderAndReceiverOrSenderAndReceiver(

                        currentUser,
                        friend,

                        friend,
                        currentUser
                )
                .ifPresent(
                        friendRequestRepository::delete
                );

        log.info(
                "Friend removed successfully"
        );
    }

    // =====================================
    // GET FRIEND REQUESTS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public List<FriendRequestDTO>
    getFriendRequests(
            String username
    ) {

        User currentUser =
                  userHelperService
                .getCurrentUser(
                        username
                );

        List<FriendRequest> requests =
                friendRequestRepository
                        .findAllByReceiverAndStatusOrderByCreatedAtDesc(

                                currentUser,

                                FriendRequestStatus.PENDING
                        );

        return requests.stream()
                .map(friendMapper::mapToFriendRequestDTO)
                .toList();
    }

    // =====================================
    // GET FRIENDS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public List<FriendDTO>
    getFriends(
            String username
    ) {

        User currentUser =
                  userHelperService
                .getCurrentUser(
                        username
                );

        List<Friendship> friendships =
                friendshipRepository
                        .findAllByUserOrderByCreatedAtDesc(
                                currentUser
                        );

        return friendships.stream()
                .map(friendMapper::mapToFriendDTO)
                .toList();
    }

    // =====================================
    // SEARCH USERS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public List<UserSearchDTO>
    searchUsers(

            String username,

            String query

    ) {

        User currentUser =
                  userHelperService
                .getCurrentUser(
                        username
                );

        if (query == null
                || query.isBlank()) {

            throw new InvalidRequestException(
                    "Search query is required"
            );
        }

        List<User> users =
                userRepository.searchUsers(

                        query.trim(),

                        currentUser.getId()
                );

        return users.stream()

                .map(user -> {

                    FriendshipStatus status =
                            getFriendshipStatus(
                                    currentUser,
                                    user
                            );

                    UserSearchDTO dto =
                            friendMapper
                                    .mapToUserSearchDTO(
                                            user
                                    );

                    dto.setFriendshipStatus(
                            status
                    );

                    return dto;
                })

                .toList();
    }

    // =====================================
    // GET FRIENDSHIP STATUS
    // =====================================
    private FriendshipStatus getFriendshipStatus(

            User currentUser,

            User targetUser

    ) {

        // FRIENDS
        boolean isFriend =
                friendshipRepository
                        .existsByUserAndFriend(

                                currentUser,

                                targetUser
                        );

        if (isFriend) {

            return FriendshipStatus.FRIENDS;
        }

        // REQUEST SENT
        boolean requestSent =
                friendRequestRepository
                        .existsBySenderAndReceiverAndStatus(

                                currentUser,

                                targetUser,

                                FriendRequestStatus.PENDING
                        );

        if (requestSent) {

            return FriendshipStatus.REQUEST_SENT;
        }

        // REQUEST RECEIVED
        boolean requestReceived =
                friendRequestRepository
                        .existsBySenderAndReceiverAndStatus(

                                targetUser,

                                currentUser,

                                FriendRequestStatus.PENDING
                        );

        if (requestReceived) {

            return FriendshipStatus.REQUEST_RECEIVED;
        }

        return FriendshipStatus.ADD_FRIEND;
    }

    // =====================================
    // CREATE FRIENDSHIP
    // =====================================
    private void createFriendship(

            User user,

            User friend

    ) {

        Friendship friendship =
                new Friendship();

        friendship.setUser(user);

        friendship.setFriend(friend);

        friendshipRepository.save(friendship);
    }



    // =====================================
    // GET USER BY ID
    // =====================================
    private User getUserById(
            Long userId
    ) {

        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }

    // =====================================
    // GET FRIEND REQUEST
    // =====================================
    private FriendRequest getFriendRequest(
            Long requestId
    ) {

        return friendRequestRepository
                .findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Friend request not found"
                        )
                );
    }

    private void sendNotification(

            User targetUser,

            String title,

            String message,

            Long referenceId,

            NotificationType type

    ) {

        notificationHelperService
                .createNotification(

                        targetUser,

                        referenceId,

                        NotificationReferenceType
                                .USER,

                        title,

                        message,

                        type
                );

        UserAuth auth =

                userAuthRepository
                        .findByUser(
                                targetUser
                        )
                        .orElse(null);

        if (

                auth == null

                        ||

                        auth.getFcmToken() == null

                        ||

                        auth.getFcmToken()
                                .isBlank()

        ) {

            return;
        }

        try {

            firebaseNotificationService
                    .sendNotification(

                            auth.getFcmToken(),

                            title,

                            message,

                            referenceId,

                            type
                    );

        }

        catch (

                Exception e

        ) {

            auth.setFcmToken(
                    null
            );

            userAuthRepository
                    .save(
                            auth
                    );

            log.error(

                    "Invalid FCM token removed for user : {}",

                    targetUser.getId()
            );
        }
    }
}