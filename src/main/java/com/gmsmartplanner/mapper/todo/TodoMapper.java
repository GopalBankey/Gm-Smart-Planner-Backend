package com.gmsmartplanner.mapper.todo;

import com.gmsmartplanner.dto.request.todo.CreateSubTaskRequestDTO;
import com.gmsmartplanner.dto.request.todo.CreateTodoRequestDTO;
import com.gmsmartplanner.dto.request.todo.UpdateTodoRequestDTO;
import com.gmsmartplanner.dto.response.*;
import com.gmsmartplanner.dto.response.todo.*;
import com.gmsmartplanner.entity.*;
import com.gmsmartplanner.entity.todo.*;
import com.gmsmartplanner.enums.todo.TodoStatus;
import com.gmsmartplanner.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoMapper {

    private final UserResponseMapper
            userResponseMapper;

    // =====================================
    // CREATE TODO
    // =====================================
    public Todo createTodo(

            CreateTodoRequestDTO dto,

            User owner,

            String sharedTaskId

    ) {

        Todo todo =
                new Todo();

        todo.setTitle(
                dto.getTitle()
        );

        todo.setDescription(
                dto.getDescription()
        );

        todo.setPriority(
                dto.getPriority()
        );

        todo.setTaskDateTime(
                dto.getTaskDateTime()
        );

        todo.setOwner(owner);

        todo.setSharedTaskId(
                sharedTaskId
        );

        todo.setStatus(
                TodoStatus.PENDING
        );

        todo.setCompleted(false);

        todo.setDeleted(false);

        return todo;
    }

    // =====================================
    // UPDATE TODO
    // =====================================
    public void updateTodo(

            Todo todo,

            UpdateTodoRequestDTO dto

    ) {

        // TITLE
        if (dto.getTitle() != null
                && !dto.getTitle().isBlank()) {

            todo.setTitle(
                    dto.getTitle()
            );
        }

        // DESCRIPTION
        if (dto.getDescription() != null) {

            todo.setDescription(
                    dto.getDescription()
            );
        }

        // PRIORITY
        if (dto.getPriority() != null) {

            todo.setPriority(
                    dto.getPriority()
            );
        }

        // STATUS
        if (dto.getStatus() != null) {

            todo.setStatus(
                    dto.getStatus()
            );

            // COMPLETED
            if (dto.getStatus()
                    == TodoStatus.COMPLETED) {

                todo.setCompleted(true);

                todo.setCompletedAt(
                        LocalDateTime.now()
                );
            }

            // REOPEN
            else {

                todo.setCompleted(false);

                todo.setCompletedAt(null);
            }
        }

        // TASK DATE TIME
        if (dto.getTaskDateTime() != null) {

            todo.setTaskDateTime(
                    dto.getTaskDateTime()
            );
        }
    }

    // =====================================
    // CREATE SUB TASK
    // =====================================
    public TodoChecklist createSubTask(

            CreateSubTaskRequestDTO dto,

            Todo todo,

            int order

    ) {

        TodoChecklist checklist =
                new TodoChecklist();

        checklist.setTodo(todo);

        checklist.setTitle(
                dto.getTitle()
        );

        checklist.setCompleted(false);

        checklist.setDeleted(false);

        checklist.setDisplayOrder(order);

        return checklist;
    }

    // =====================================
    // MAP TODO SUMMARY
    // =====================================
    public TodoSummaryDTO mapToTodoSummary(
            Todo todo
    ) {

        return TodoSummaryDTO.builder()

                .id(todo.getId())

                .title(todo.getTitle())

                .status(todo.getStatus())

                .priority(
                        todo.getPriority()
                )

                .completed(
                        todo.isCompleted()
                )

                .taskDateTime(
                        todo.getTaskDateTime()
                )

                .createdAt(
                        todo.getCreatedAt()
                )

                .build();
    }

    // =====================================
    // MAP SUB TASK RESPONSE
    // =====================================
    public SubTaskResponseDTO
    mapToSubTaskResponse(

            TodoChecklist checklist

    ) {

        return SubTaskResponseDTO.builder()

                .id(checklist.getId())

                .title(
                        checklist.getTitle()
                )

                .completed(
                        checklist.isCompleted()
                )

                .completedAt(
                        checklist.getCompletedAt()
                )

                .displayOrder(
                        checklist.getDisplayOrder()
                )

                .createdAt(
                        checklist.getCreatedAt()
                )

                .updatedAt(
                        checklist.getUpdatedAt()
                )

                .build();
    }

    // =====================================
    // MAP SHARED USER RESPONSE
    // =====================================
    public SharedUserResponseDTO
    mapToSharedUserResponse(

            TodoShare share

    ) {

        User user =
                share.getSharedWithUser();

        return SharedUserResponseDTO.builder()

                .userId(user.getId())

                .name(user.getName())

                .email(user.getEmail())

                .profileImageUrl(
                        user.getProfileImageUrl()
                )

                .canEdit(
                        share.isCanEdit()
                )

                .canComplete(
                        share.isCanComplete()
                )

                .canComment(
                        share.isCanComment()
                )

                .build();
    }

    // =====================================
    // MAP COMMENT RESPONSE
    // =====================================
    public CommentResponseDTO
    mapToCommentResponse(

            TodoComment comment,

            UserAuth auth

    ) {

        return CommentResponseDTO.builder()

                .id(comment.getId())

                .comment(
                        comment.getComment()
                )

                .edited(
                        comment.isEdited()
                )

                .deleted(
                        comment.isDeleted()
                )

                .user(
                        userResponseMapper
                                .mapToUserResponse(

                                        comment.getUser(),

                                        auth
                                )
                )

                .createdAt(
                        comment.getCreatedAt()
                )

                .updatedAt(
                        comment.getUpdatedAt()
                )

                .build();
    }

    // =====================================
    // MAP REMINDER RESPONSE
    // =====================================
    public ReminderResponseDTO
    mapToReminderResponse(

            TodoReminder reminder

    ) {

        return ReminderResponseDTO.builder()

                .id(reminder.getId())

                .reminderTime(
                        reminder.getReminderTime()
                )

                .notificationType(
                        reminder.getNotificationType()
                )

                .sent(
                        reminder.isSent()
                )

                .active(
                        reminder.isActive()
                )

                .recurring(
                        reminder.isRecurring()
                )

                .recurringType(
                        reminder.getRecurringType()
                )

                .lastSentAt(
                        reminder.getLastSentAt()
                )

                .build();
    }

    // =====================================
    // MAP ACTIVITY RESPONSE
    // =====================================
    public ActivityResponseDTO
    mapToActivityResponse(

            TodoActivity activity,

            UserAuth auth

    ) {

        return ActivityResponseDTO.builder()

                .id(activity.getId())

                .activityType(
                        activity.getActivityType()
                )

                .description(
                        activity.getDescription()
                )

                .user(
                        userResponseMapper
                                .mapToUserResponse(

                                        activity.getUser(),

                                        auth
                                )
                )

                .createdAt(
                        activity.getCreatedAt()
                )

                .build();
    }

    // =====================================
    // MAP NOTIFICATION RESPONSE
    // =====================================
    public NotificationResponseDTO
    mapToNotificationResponse(

            TodoNotification notification

    ) {

        return NotificationResponseDTO.builder()

                .id(notification.getId())

                .type(
                        notification.getType()
                )

                .title(
                        notification.getTitle()
                )

                .message(
                        notification.getMessage()
                )

                .read(
                        notification.isRead()
                )

                .sent(
                        notification.isSent()
                )

                .createdAt(
                        notification.getCreatedAt()
                )

                .build();
    }

    // =====================================
    // MAP TODO RESPONSE
    // =====================================
    public TodoResponseDTO
    mapToTodoResponse(

            Todo todo,

            UserAuth ownerAuth,

            List<SharedUserResponseDTO>
                    sharedUsers,

            List<SubTaskResponseDTO>
                    subTasks

    ) {

        return TodoResponseDTO.builder()

                .id(todo.getId())

                .title(todo.getTitle())

                .description(
                        todo.getDescription()
                )

                .status(todo.getStatus())

                .priority(
                        todo.getPriority()
                )

                .completed(
                        todo.isCompleted()
                )

                .completedAt(
                        todo.getCompletedAt()
                )

                .taskDateTime(
                        todo.getTaskDateTime()
                )

                .sharedTaskId(
                        todo.getSharedTaskId()
                )

                .owner(
                        userResponseMapper
                                .mapToUserResponse(

                                        todo.getOwner(),

                                        ownerAuth
                                )
                )

                .sharedUsers(
                        sharedUsers
                )

                .subTasks(
                        subTasks
                )

                .createdAt(
                        todo.getCreatedAt()
                )

                .updatedAt(
                        todo.getUpdatedAt()
                )

                .build();
    }

}