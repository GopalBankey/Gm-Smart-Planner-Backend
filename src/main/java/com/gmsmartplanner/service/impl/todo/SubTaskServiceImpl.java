package com.gmsmartplanner.service.impl.todo;

import com.gmsmartplanner.dto.response.todo.SubTaskResponseDTO;
import com.gmsmartplanner.entity.*;
import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoActivity;
import com.gmsmartplanner.entity.todo.TodoChecklist;
import com.gmsmartplanner.entity.todo.TodoShare;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.todo.TodoMapper;
import com.gmsmartplanner.repository.*;
import com.gmsmartplanner.repository.todo.TodoActivityRepository;
import com.gmsmartplanner.repository.todo.TodoChecklistRepository;
import com.gmsmartplanner.repository.todo.TodoShareRepository;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import com.gmsmartplanner.service.todo.SubTaskService;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubTaskServiceImpl
        implements SubTaskService {

    private final TodoChecklistRepository
            todoChecklistRepository;

    private final TodoShareRepository
            todoShareRepository;

    private final TodoActivityRepository
            todoActivityRepository;

    private final UserRepository
            userRepository;

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
    // COMPLETE SUB TASK
    // =====================================
    @Override
    public SubTaskResponseDTO completeSubTask(

            String username,

            Long subTaskId

    ) {

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        TodoChecklist checklist =
                getSubTask(subTaskId);

        validateTodoAccess(
                checklist.getTodo(),
                user
        );

        checklist.setCompleted(
                !checklist.isCompleted()
        );

        checklist.setCompletedAt(

                checklist.isCompleted()
                        ? LocalDateTime.now()
                        : null
        );

        TodoChecklist updatedChecklist =
                todoChecklistRepository.save(
                        checklist
                );

        createActivity(

                checklist.getTodo(),

                user,

                TodoActivityType.UPDATED,

                checklist.isCompleted()
                        ? "Sub task completed"
                        : "Sub task reopened"
        );

        // =====================================
        // SEND NOTIFICATION TO SHARED USERS
        // =====================================

        sendNotificationToSharedUsers(

                checklist.getTodo(),

                user,

                checklist.isCompleted()
                        ? "Sub Task Completed"
                        : "Sub Task Reopened",

                user.getName()
                        + " updated sub task : "
                        + checklist.getTitle(),

                NotificationType.SUBTASK
        );

        return todoMapper
                .mapToSubTaskResponse(
                        updatedChecklist
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

        List<TodoShare> sharedUsers =
                todoShareRepository
                        .findAllByTodoAndActiveTrue(todo);

        for (TodoShare share : sharedUsers) {

            User sharedUser =
                    share.getSharedWithUser();

            // =====================================
            // PREVENT SELF NOTIFICATION
            // =====================================

            if (sharedUser.getId()
                    .equals(actionUser.getId())) {

                continue;
            }

            // =====================================
            // SAVE DB NOTIFICATION
            // =====================================

            notificationHelperService
                    .createNotification(

                            sharedUser,

                            todo,

                            title,

                            message,

                            type
                    );

            // =====================================
            // SEND PUSH NOTIFICATION
            // =====================================

            UserAuth auth =
                    userAuthRepository
                            .findByUser(sharedUser)
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

                // =====================================
                // INVALID TOKEN HANDLING
                // =====================================

                auth.setFcmToken(null);

                userAuthRepository.save(auth);
            }
        }
    }

    // =====================================
    // GET SUB TASK
    // =====================================
    private TodoChecklist getSubTask(
            Long subTaskId
    ) {

        return todoChecklistRepository
                .findByIdAndDeletedFalse(
                        subTaskId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sub task not found"
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

    // =====================================
// REOPEN SUB TASK
// =====================================
    @Override
    public SubTaskResponseDTO reopenSubTask(

            String username,

            Long subTaskId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        TodoChecklist checklist =
                getSubTask(
                        subTaskId
                );

        validateTodoAccess(

                checklist.getTodo(),

                user
        );

        if (!checklist.isCompleted()) {

            throw new ResourceNotFoundException(
                    "Sub task already open"
            );
        }

        checklist.setCompleted(
                false
        );

        checklist.setCompletedAt(
                null
        );

        TodoChecklist updated =
                todoChecklistRepository
                        .save(
                                checklist
                        );

        createActivity(

                checklist.getTodo(),

                user,

                TodoActivityType.UPDATED,

                "Sub task reopened"
        );

        sendNotificationToSharedUsers(

                checklist.getTodo(),

                user,

                "Sub Task Reopened",

                user.getName()
                        + " reopened sub task : "
                        + checklist.getTitle(),

                NotificationType.SUBTASK
        );

        return todoMapper
                .mapToSubTaskResponse(
                        updated
                );
    }
}