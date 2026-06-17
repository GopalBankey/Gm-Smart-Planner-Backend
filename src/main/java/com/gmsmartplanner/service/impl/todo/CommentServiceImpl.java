package com.gmsmartplanner.service.impl.todo;

import com.gmsmartplanner.dto.request.todo.CreateCommentRequestDTO;
import com.gmsmartplanner.dto.response.todo.CommentResponseDTO;
import com.gmsmartplanner.entity.*;
import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoActivity;
import com.gmsmartplanner.entity.todo.TodoComment;
import com.gmsmartplanner.entity.todo.TodoShare;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.todo.TodoMapper;
import com.gmsmartplanner.repository.*;
import com.gmsmartplanner.repository.todo.TodoActivityRepository;
import com.gmsmartplanner.repository.todo.TodoCommentRepository;
import com.gmsmartplanner.repository.todo.TodoRepository;
import com.gmsmartplanner.repository.todo.TodoShareRepository;
import com.gmsmartplanner.service.todo.CommentService;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl
        implements CommentService {

    private final TodoRepository
            todoRepository;

    private final TodoCommentRepository
            todoCommentRepository;

    private final TodoShareRepository
            todoShareRepository;

    private final TodoActivityRepository
            todoActivityRepository;

    private final UserAuthRepository
            userAuthRepository;

    private final TodoMapper
            todoMapper;

    private final NotificationHelperService
            notificationHelperService;

    private final FirebaseNotificationService
            firebaseNotificationService;

    private final UserHelperService
            userHelperService;

    // =====================================
    // ADD COMMENT
    // =====================================
    @Override
    public CommentResponseDTO addComment(

            String username,

            Long todoId,

            CreateCommentRequestDTO dto

    ) {

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        Todo todo =
                getTodo(todoId);

        validateTodoAccess(
                todo,
                user
        );

        TodoComment comment =
                new TodoComment();

        comment.setTodo(todo);

        comment.setUser(user);

        comment.setComment(
                dto.getComment()
        );

        comment.setEdited(false);

        comment.setDeleted(false);

        TodoComment savedComment =
                todoCommentRepository.save(
                        comment
                );

        createActivity(

                todo,

                user,

                TodoActivityType.COMMENT_ADDED,

                "Comment added"
        );

        // =====================================
        // SEND NOTIFICATION TO SHARED USERS
        // =====================================

        sendNotificationToSharedUsers(

                todo,

                user,

                "New Comment",

                user.getName()
                        + " commented on : "
                        + todo.getTitle(),

                NotificationType.COMMENT_ADDED
        );

        UserAuth auth =
                getUserAuth(user);

        return todoMapper
                .mapToCommentResponse(

                        savedComment,

                        auth
                );
    }

    // =====================================
    // GET COMMENTS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getComments(

            String username,

            Long todoId

    ) {

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        Todo todo =
                getTodo(todoId);

        validateTodoAccess(
                todo,
                user
        );

        UserAuth auth =
                getUserAuth(user);

        return todoCommentRepository
                .findAllByTodoAndDeletedFalseOrderByCreatedAtAsc(todo)
                .stream()
                .map(comment ->
                        todoMapper.mapToCommentResponse(
                                comment,
                                auth
                        )
                )
                .toList();
    }

    // =====================================
    // UPDATE COMMENT
    // =====================================
    @Override
    public CommentResponseDTO updateComment(

            String username,

            Long commentId,

            CreateCommentRequestDTO dto

    ) {

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        TodoComment comment =
                getComment(commentId);

        if (!comment.getUser()
                .getId()
                .equals(user.getId())) {

            throw new InvalidRequestException(
                    "You can only edit your own comments"
            );
        }

        comment.setComment(
                dto.getComment()
        );

        comment.setEdited(true);

        TodoComment updatedComment =
                todoCommentRepository.save(
                        comment
                );

        createActivity(

                comment.getTodo(),

                user,

                TodoActivityType.UPDATED,

                "Comment updated"
        );

        UserAuth auth =
                getUserAuth(user);

        return todoMapper
                .mapToCommentResponse(

                        updatedComment,

                        auth
                );
    }

    // =====================================
    // DELETE COMMENT
    // =====================================
    @Override
    public void deleteComment(

            String username,

            Long commentId

    ) {

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        TodoComment comment =
                getComment(commentId);

        if (!comment.getUser()
                .getId()
                .equals(user.getId())) {

            throw new InvalidRequestException(
                    "You can only delete your own comments"
            );
        }

        comment.setDeleted(true);

        todoCommentRepository.save(
                comment
        );

        createActivity(

                comment.getTodo(),

                user,

                TodoActivityType.UPDATED,

                "Comment deleted"
        );
    }

    // =====================================
    // SEND NOTIFICATION TO SHARED USERS
    // =====================================

    private void sendNotificationToSharedUsers(

            Todo todo,

            User actionUser,

            String title,

            String message,

            NotificationType type

    ) {

        List<User> receivers =
                new java.util.ArrayList<>();

        // OWNER
        receivers.add(
                todo.getOwner()
        );

        // SHARED USERS
        receivers.addAll(

                todoShareRepository
                        .findAllByTodoAndActiveTrue(todo)
                        .stream()
                        .map(TodoShare::getSharedWithUser)
                        .toList()
        );

        for (User receiver : receivers) {

            // SKIP SELF
            if (receiver.getId()
                    .equals(actionUser.getId())) {

                continue;
            }

            notificationHelperService
                    .createNotification(

                            receiver,

                            todo.getId(),

                            NotificationReferenceType
                                    .TODO,

                            title,

                            message,

                            type
                    );

            UserAuth auth =
                    userAuthRepository
                            .findByUser(receiver)
                            .orElse(null);

            if (auth == null
                    || auth.getFcmToken() == null
                    || auth.getFcmToken().isBlank()) {

                continue;
            }

            try {

                firebaseNotificationService
                        .sendNotification(

                                auth.getFcmToken(),

                                title,

                                message,

                                todo.getId(),

                                type
                        );

            } catch (Exception e) {

                auth.setFcmToken(null);

                userAuthRepository.save(auth);
            }
        }
    }
//    private void sendNotificationToSharedUsers(
//
//            Todo todo,
//
//            User actionUser,
//
//            String title,
//
//            String message,
//
//            NotificationType type
//
//    ) {
//
//        List<TodoShare> sharedUsers =
//                todoShareRepository
//                        .findAllByTodoAndActiveTrue(todo);
//
//        for (TodoShare share : sharedUsers) {
//
//            User sharedUser =
//                    share.getSharedWithUser();
//
//            // =====================================
//            // PREVENT SELF NOTIFICATION
//            // =====================================
//
//            if (sharedUser.getId()
//                    .equals(actionUser.getId())) {
//
//                continue;
//            }
//
//            // =====================================
//            // SAVE DB NOTIFICATION
//            // =====================================
//
//            notificationHelperService
//                    .createNotification(
//
//                            sharedUser,
//
//                            todo,
//
//                            title,
//
//                            message,
//
//                            type
//                    );
//
//            // =====================================
//            // SEND PUSH NOTIFICATION
//            // =====================================
//
//            UserAuth auth =
//                    userAuthRepository
//                            .findByUser(sharedUser)
//                            .orElse(null);
//
//            if (auth == null
//                    || auth.getFcmToken() == null
//                    || auth.getFcmToken().isBlank()) {
//
//                continue;
//            }
//
//            try {
//
//                firebaseNotificationService
//                        .sendNotification(
//
//                                auth.getFcmToken(),
//
//                                title,
//
//                                message,
//
//                                todo.getId(),
//
//                                type
//                        );
//
//            } catch (Exception e) {
//
//                // =====================================
//                // INVALID TOKEN HANDLING
//                // =====================================
//
//                auth.setFcmToken(null);
//
//                userAuthRepository.save(auth);
//            }
//        }
//    }

    // =====================================
    // GET COMMENT
    // =====================================
    private TodoComment getComment(
            Long commentId
    ) {

        return todoCommentRepository
                .findByIdAndDeletedFalse(
                        commentId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Comment not found"
                        )
                );
    }

    // =====================================
    // GET TODO
    // =====================================
    private Todo getTodo(
            Long todoId
    ) {

        return todoRepository
                .findByIdAndDeletedFalse(
                        todoId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found"
                        )
                );
    }



    // =====================================
    // GET USER AUTH
    // =====================================
    private UserAuth getUserAuth(
            User user
    ) {

        return userAuthRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User auth not found"
                        )
                );
    }

    // =====================================
    // VALIDATE ACCESS
    // =====================================
    private void validateTodoAccess(

            Todo todo,

            User user

    ) {

        boolean isOwner =
                todo.getOwner()
                        .getId()
                        .equals(user.getId());

        boolean isShared =
                todoShareRepository
                        .existsByTodoAndSharedWithUserAndActiveTrue(
                                todo,
                                user
                        );

        if (!isOwner && !isShared) {

            throw new ResourceNotFoundException(
                    "Todo not found"
            );
        }
    }

    // =====================================
    // CREATE ACTIVITY
    // =====================================
    private void createActivity(

            Todo todo,

            User user,

            TodoActivityType type,

            String description

    ) {

        TodoActivity activity =
                new TodoActivity();

        activity.setTodo(todo);

        activity.setUser(user);

        activity.setActivityType(type);

        activity.setDescription(description);

        todoActivityRepository.save(activity);
    }
}