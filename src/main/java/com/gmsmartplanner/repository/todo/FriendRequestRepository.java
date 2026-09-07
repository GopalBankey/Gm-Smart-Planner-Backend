package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.todo.FriendRequest;
import com.gmsmartplanner.enums.todo.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    boolean
    existsBySenderAndReceiverAndStatus(

            User sender,

            User receiver,

            FriendRequestStatus status
    );

    // =====================================
    // GET RECEIVED ACTIVE USER REQUESTS
    // =====================================

    @Query("""
        SELECT r
        FROM FriendRequest r

        WHERE r.receiver = :receiver

          AND r.status = :status

          AND r.sender.active = true

        ORDER BY r.createdAt DESC
        """)
    List<FriendRequest>
    findAllActiveReceivedRequests(

            @Param("receiver")
            User receiver,

            @Param("status")
            FriendRequestStatus status
    );

    // =====================================
    // GET SENT ACTIVE USER REQUESTS
    // =====================================

    @Query("""
        SELECT r
        FROM FriendRequest r

        WHERE r.sender = :sender

          AND r.status = :status

          AND r.receiver.active = true

        ORDER BY r.createdAt DESC
        """)
    List<FriendRequest>
    findAllActiveSentRequests(

            @Param("sender")
            User sender,

            @Param("status")
            FriendRequestStatus status
    );

    // =====================================
    // DELETE REQUEST
    // =====================================

    void deleteBySenderAndReceiver(

            User sender,

            User receiver
    );

    // =====================================
    // FIND REQUEST IN BOTH DIRECTIONS
    // =====================================

    Optional<FriendRequest>
    findBySenderAndReceiverOrSenderAndReceiver(

            User sender1,

            User receiver1,

            User sender2,

            User receiver2
    );
}