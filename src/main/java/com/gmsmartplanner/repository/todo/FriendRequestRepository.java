package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.FriendRequest;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository
        extends JpaRepository<
        FriendRequest,
        Long> {

    // =====================================
    // FIND REQUEST BETWEEN TWO USERS
    // =====================================

    Optional<FriendRequest>
    findBySenderAndReceiver(
            User sender,
            User receiver
    );

    // =====================================
    // CHECK PENDING REQUEST
    // =====================================

    boolean existsBySenderAndReceiverAndStatus(

            User sender,

            User receiver,

            FriendRequestStatus status
    );

    // =====================================
    // GET RECEIVED REQUESTS
    // =====================================

    List<FriendRequest>
    findAllByReceiverAndStatusOrderByCreatedAtDesc(

            User receiver,

            FriendRequestStatus status
    );

    // =====================================
    // GET SENT REQUESTS
    // =====================================

    List<FriendRequest>
    findAllBySenderAndStatusOrderByCreatedAtDesc(

            User sender,

            FriendRequestStatus status
    );

    // =====================================
    // DELETE REQUEST
    // =====================================

    void deleteBySenderAndReceiver(
            User sender,
            User receiver
    );

    Optional<FriendRequest>
    findBySenderAndReceiverOrSenderAndReceiver(

            User sender1,

            User receiver1,

            User sender2,

            User receiver2
    );
}