package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.todo.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository
        extends JpaRepository<
        Friendship,
        Long> {

    // =====================================
    // CHECK FRIENDSHIP
    // =====================================

    boolean existsByUserAndFriend(

            User user,

            User friend
    );

    // =====================================
    // GET ACTIVE FRIENDS
    // =====================================

    List<Friendship>
    findAllByUserAndFriendActiveTrueOrderByCreatedAtDesc(

            User user
    );

    // =====================================
    // REMOVE FRIEND
    // =====================================

    void deleteByUserAndFriend(

            User user,

            User friend
    );
}