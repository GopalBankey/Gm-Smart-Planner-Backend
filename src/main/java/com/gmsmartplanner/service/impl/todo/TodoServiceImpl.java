package com.gmsmartplanner.service.impl.todo;

import com.gmsmartplanner.dto.request.todo.*;
import com.gmsmartplanner.dto.response.todo.*;
import com.gmsmartplanner.entity.*;
import com.gmsmartplanner.entity.todo.*;
import com.gmsmartplanner.enums.*;
import com.gmsmartplanner.enums.todo.ReminderNotificationType;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import com.gmsmartplanner.enums.todo.TodoStatus;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.todo.TodoMapper;
import com.gmsmartplanner.repository.*;
import com.gmsmartplanner.repository.todo.*;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import com.gmsmartplanner.service.todo.TodoService;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TodoServiceImpl
        implements TodoService {

    private final TodoRepository
            todoRepository;

    private final TodoShareRepository
            todoShareRepository;

    private final TodoChecklistRepository
            todoChecklistRepository;

    private final TodoActivityRepository
            todoActivityRepository;

    private final TodoReminderRepository
            todoReminderRepository;

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
    // CREATE TODO
    // =====================================
    @Override
    public TodoResponseDTO createTodo(

            String username,

            CreateTodoRequestDTO dto

    ) {

        log.info(
                "Creating todo for user : {}",
                username
        );

        User owner =
                 userHelperService
                .getCurrentUser(
                        username
                );

        String sharedTaskId =
                UUID.randomUUID()
                        .toString();

        Todo todo =
                todoMapper.createTodo(

                        dto,

                        owner,

                        sharedTaskId
                );

        Todo savedTodo =
                todoRepository.save(todo);

        // CREATE SUB TASKS
        if (dto.getSubTasks() != null) {

            createSubTasks(

                    savedTodo,

                    dto.getSubTasks()
            );
        }

        // SHARE TODO
        if (dto.getSharedUserIds() != null) {

            shareTodoWithUsers(

                    savedTodo,

                    dto.getSharedUserIds()
            );
        }

        // CREATE REMINDER
        createReminder(savedTodo);

        // CREATE ACTIVITY
        createActivity(

                savedTodo,

                owner,

                TodoActivityType.CREATED,

                "Task created"
        );

        return buildTodoResponse(
                savedTodo
        );
    }

    // =====================================
    // UPDATE TODO
    // =====================================
    @Override
    public TodoResponseDTO updateTodo(

            String username,

            Long todoId,

            UpdateTodoRequestDTO dto

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

        todoMapper.updateTodo(
                todo,
                dto
        );

        // UPDATE SUB TASKS
        updateSubTasks(
                todo,
                dto.getSubTasks()
        );

        Todo updatedTodo =
                todoRepository.save(todo);

        createActivity(

                updatedTodo,

                user,

                TodoActivityType.UPDATED,

                "Task updated"
        );

        sendNotificationToSharedUsers(

                updatedTodo,

                user,

                "Task Updated",

                user.getName()
                        + " updated task : "
                        + updatedTodo.getTitle(),

                getNotificationType(
                        TodoActivityType.UPDATED
                )
        );

        return buildTodoResponse(
                updatedTodo
        );
    }

    // =====================================
    // DELETE TODO
    // =====================================
    @Override
    public void deleteTodo(

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

        validateTodoOwner(
                todo,
                user
        );

        todo.setDeleted(true);

        todoRepository.save(todo);

        createActivity(

                todo,

                user,

                TodoActivityType.UPDATED,

                "Task deleted"
        );
    }

    // =====================================
    // GET TODO DETAILS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public TodoResponseDTO getTodoById(

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

        return buildTodoResponse(
                todo
        );
    }

    // =====================================
    // GET MY TODOS
    // =====================================
// =====================================
// GET MY TODOS
// =====================================
    @Override
    @Transactional(readOnly = true)
    public Page<TodoResponseDTO> getMyTodos(

            String username,

            Pageable pageable

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return todoRepository
                .findMyAndSharedTodos(

                        user,

                        pageable
                )
                .map(this::buildTodoResponse);
    }

    // =====================================
    // SEARCH TODOS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public Page<TodoSummaryDTO> searchTodos(

            String username,

            String query,

            Pageable pageable

    ) {

        User user =
                 userHelperService
                .getCurrentUser(
                        username
                );

        return todoRepository
                .searchTodos(

                        user,

                        query,

                        pageable
                )
                .map(todoMapper::mapToTodoSummary);
    }

    // =====================================
    // SHARE TODO
    // =====================================
    @Override
    public TodoResponseDTO shareTodo(

            String username,

            Long todoId,

            ShareTodoRequestDTO dto

    ) {

        User owner =
                 userHelperService
                .getCurrentUser(
                        username
                );

        Todo todo =
                getTodo(todoId);

        validateTodoOwner(
                todo,
                owner
        );

        shareTodoWithUsers(

                todo,

                dto.getUserIds()
        );

        createActivity(

                todo,

                owner,

                TodoActivityType.SHARED,

                "Task shared"
        );

        return buildTodoResponse(
                todo
        );
    }

    // =====================================
    // COMPLETE TODO
    // =====================================
    @Override
    public TodoResponseDTO completeTodo(

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

        if (todo.isCompleted()) {

            throw new InvalidRequestException(
                    "Todo already completed"
            );
        }

        todo.setCompleted(true);

        todo.setCompletedAt(
                LocalDateTime.now()
        );

        todo.setStatus(
                TodoStatus.COMPLETED
        );

        todoRepository.save(todo);

        createActivity(

                todo,

                user,

                TodoActivityType.COMPLETED,

                "Task completed"
        );

        sendNotificationToSharedUsers(

                todo,

                user,

                "Task Completed",

                user.getName()
                        + " completed task : "
                        + todo.getTitle(),

                getNotificationType(
                        TodoActivityType.COMPLETED
                )
        );

        return buildTodoResponse(
                todo
        );
    }

    // =====================================
    // REOPEN TODO
    // =====================================
    @Override
    public TodoResponseDTO reopenTodo(

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

        todo.setCompleted(false);

        todo.setCompletedAt(null);

        todo.setStatus(
                TodoStatus.PENDING
        );

        todoRepository.save(todo);

        createActivity(

                todo,

                user,

                TodoActivityType.REOPENED,

                "Task reopened"
        );

        sendNotificationToSharedUsers(

                todo,

                user,

                "Task Reopened",

                user.getName()
                        + " reopened task : "
                        + todo.getTitle(),

                getNotificationType(
                        TodoActivityType.REOPENED
                )
        );

        return buildTodoResponse(
                todo
        );
    }

    // =====================================
    // BUILD TODO RESPONSE
    // =====================================
    private TodoResponseDTO
    buildTodoResponse(
            Todo todo
    ) {

        UserAuth ownerAuth =
                getUserAuth(
                        todo.getOwner()
                );

        List<SharedUserResponseDTO>
                sharedUsers =
                todoShareRepository
                        .findAllByTodoAndActiveTrue(todo)
                        .stream()
                        .map(todoMapper::mapToSharedUserResponse)
                        .toList();

        List<SubTaskResponseDTO>
                subTasks =
                todoChecklistRepository
                        .findAllByTodoAndDeletedFalseOrderByDisplayOrderAsc(todo)
                        .stream()
                        .map(todoMapper::mapToSubTaskResponse)
                        .toList();

        return todoMapper
                .mapToTodoResponse(

                        todo,

                        ownerAuth,

                        sharedUsers,

                        subTasks
                );
    }

    // =====================================
    // CREATE SUB TASKS
    // =====================================
    private void createSubTasks(

            Todo todo,

            List<CreateSubTaskRequestDTO>
                    subTasks

    ) {

        for (int i = 0;
             i < subTasks.size();
             i++) {

            TodoChecklist checklist =
                    todoMapper.createSubTask(

                            subTasks.get(i),

                            todo,

                            i + 1
                    );

            todoChecklistRepository.save(
                    checklist
            );
        }
    }

    // =====================================
    // UPDATE SUB TASKS
    // =====================================
    private void updateSubTasks(

            Todo todo,

            List<UpdateSubTaskRequestDTO>
                    requestSubTasks

    ) {

        if (requestSubTasks == null) {

            return;
        }

        List<TodoChecklist> existingSubTasks =
                todoChecklistRepository
                        .findAllByTodoAndDeletedFalseOrderByDisplayOrderAsc(todo);

        for (int i = 0;
             i < requestSubTasks.size();
             i++) {

            UpdateSubTaskRequestDTO dto =
                    requestSubTasks.get(i);

            if (dto.getId() == null) {

                TodoChecklist newSubTask =
                        new TodoChecklist();

                newSubTask.setTodo(todo);

                newSubTask.setTitle(
                        dto.getTitle()
                );

                newSubTask.setCompleted(

                        dto.getCompleted() != null
                                && dto.getCompleted()
                );

                newSubTask.setCompletedAt(

                        Boolean.TRUE.equals(dto.getCompleted())
                                ? LocalDateTime.now()
                                : null
                );

                newSubTask.setDisplayOrder(
                        i + 1
                );

                newSubTask.setDeleted(false);

                todoChecklistRepository.save(
                        newSubTask
                );

                continue;
            }

            TodoChecklist existingSubTask =
                    existingSubTasks.stream()

                            .filter(subTask ->
                                    subTask.getId()
                                            .equals(dto.getId())
                            )

                            .findFirst()

                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Sub task not found"
                                    )
                            );

            existingSubTask.setTitle(
                    dto.getTitle()
            );

            existingSubTask.setCompleted(

                    dto.getCompleted() != null
                            && dto.getCompleted()
            );

            existingSubTask.setCompletedAt(

                    Boolean.TRUE.equals(dto.getCompleted())
                            ? LocalDateTime.now()
                            : null
            );

            existingSubTask.setDisplayOrder(
                    i + 1
            );

            todoChecklistRepository.save(
                    existingSubTask
            );
        }

        List<Long> requestIds =
                requestSubTasks.stream()

                        .map(UpdateSubTaskRequestDTO::getId)

                        .filter(id -> id != null)

                        .toList();

        for (TodoChecklist subTask : existingSubTasks) {

            if (!requestIds.contains(
                    subTask.getId()
            )) {

                subTask.setDeleted(true);

                todoChecklistRepository.save(
                        subTask
                );
            }
        }
    }

    // =====================================
    // SHARE TODO WITH USERS
    // =====================================
    private void shareTodoWithUsers(

            Todo todo,

            List<Long> userIds

    ) {

        for (Long userId : userIds) {

            User user =
                    userRepository.findById(userId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "User not found"
                                    )
                            );

            if (todo.getOwner()
                    .getId()
                    .equals(user.getId())) {

                continue;
            }

            boolean alreadyShared =
                    todoShareRepository
                            .existsByTodoAndSharedWithUserAndActiveTrue(

                                    todo,

                                    user
                            );

            if (alreadyShared) {

                continue;
            }

            TodoShare share =
                    new TodoShare();

            share.setTodo(todo);

            share.setSharedWithUser(
                    user
            );

            share.setCanEdit(true);

            share.setCanComplete(true);

            share.setCanComment(true);

            share.setActive(true);

            todoShareRepository.save(
                    share
            );

            notificationHelperService
                    .createNotification(

                            user,

                            todo,

                            "Task Shared",

                            todo.getOwner().getName()
                                    + " shared a task : "
                                    + todo.getTitle(),

                            NotificationType.TASK_SHARED
                    );

            UserAuth auth =
                    userAuthRepository
                            .findByUser(user)
                            .orElse(null);

            if (auth != null
                    && auth.getFcmToken() != null
                    && !auth.getFcmToken().isBlank()) {

                firebaseNotificationService
                        .sendNotification(

                                auth.getFcmToken(),

                                "Task Shared",

                                todo.getOwner().getName()
                                        + " shared a task : "
                                        + todo.getTitle(),

                                todo.getId(),

                                NotificationType.TASK_SHARED
                        );
            }
        }
    }

    // =====================================
    // CREATE REMINDER
    // =====================================
    private void createReminder(
            Todo todo
    ) {

        if (todo.getTaskDateTime() == null) {

            return;
        }

        TodoReminder reminder =
                new TodoReminder();

        reminder.setTodo(todo);

        reminder.setReminderTime(
                todo.getTaskDateTime()
        );

        reminder.setNotificationType(
                ReminderNotificationType.NORMAL
        );

        reminder.setActive(true);

        reminder.setSent(false);

        reminder.setRecurring(false);

        todoReminderRepository.save(
                reminder
        );
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

        activity.setDescription(
                description
        );

        todoActivityRepository.save(
                activity
        );
    }

    // =====================================
    // GET NOTIFICATION TYPE
    // =====================================
    private NotificationType getNotificationType(

            TodoActivityType activityType

    ) {

        return switch (activityType) {

            case UPDATED ->
                    NotificationType.TODO_UPDATED;

            case COMPLETED ->
                    NotificationType.TODO_COMPLETED;

            case REOPENED ->
                    NotificationType.TODO_REOPENED;

            case SHARED ->
                    NotificationType.TASK_SHARED;

            default ->
                    NotificationType.TODO_UPDATED;
        };
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

            // PREVENT SELF NOTIFICATION
            if (sharedUser.getId()
                    .equals(actionUser.getId())) {

                continue;
            }

            notificationHelperService
                    .createNotification(

                            sharedUser,

                            todo,

                            title,

                            message,

                            type
                    );

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

                // INVALID TOKEN HANDLING
                auth.setFcmToken(null);

                userAuthRepository.save(auth);

                log.error(
                        "Invalid FCM token removed for user : {}",
                        sharedUser.getId()
                );
            }
        }
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
    // VALIDATE OWNER
    // =====================================
    private void validateTodoOwner(

            Todo todo,

            User user

    ) {

        if (!todo.getOwner()
                .getId()
                .equals(user.getId())) {

            throw new InvalidRequestException(
                    "Only owner can perform this action"
            );
        }
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
// FILTER TODOS
// =====================================
    @Override
    @Transactional(readOnly = true)
    public Page<TodoSummaryDTO> filterTodos(

            String username,

            TodoFilterRequestDTO dto,

            Pageable pageable

    ) {

        User user =
                 userHelperService
                .getCurrentUser(
                        username
                );

        FilterDateRange dateRange =
                buildDateRange(dto);

        return todoRepository
                .filterTodos(

                        user,

                        dto.getPriority(),

                        dto.getStatus(),

                        dateRange.startDate(),

                        dateRange.endDate(),

                        dto.getIncludeOwn(),

                        dto.getIncludeShared(),

                        pageable
                )
                .map(todoMapper::mapToTodoSummary);
    }

    // =====================================
// BUILD DATE RANGE
// =====================================
    private FilterDateRange buildDateRange(
            TodoFilterRequestDTO dto
    ) {

        LocalDateTime startDate = null;

        LocalDateTime endDate = null;

        if (dto.getFilterMode()
                == FilterMode.DAILY) {

            startDate =
                    dto.getSelectedDate()
                            .atStartOfDay();

            endDate =
                    dto.getSelectedDate()
                            .atTime(23, 59, 59);
        }

        else if (dto.getFilterMode()
                == FilterMode.MONTHLY) {

            LocalDate monthDate =
                    LocalDate.of(

                            dto.getSelectedMonthYear(),

                            dto.getSelectedMonth(),

                            1
                    );

            startDate =
                    monthDate
                            .withDayOfMonth(1)
                            .atStartOfDay();

            endDate =
                    monthDate
                            .withDayOfMonth(
                                    monthDate.lengthOfMonth()
                            )
                            .atTime(23, 59, 59);
        }

        else if (dto.getFilterMode()
                == FilterMode.YEARLY) {

            startDate =
                    LocalDate.of(

                            dto.getSelectedYear(),

                            1,

                            1
                    ).atStartOfDay();

            endDate =
                    LocalDate.of(

                            dto.getSelectedYear(),

                            12,

                            31
                    ).atTime(23, 59, 59);
        }

        else if (dto.getFilterMode()
                == FilterMode.RANGE) {

            startDate =
                    dto.getStartDate()
                            .atStartOfDay();

            endDate =
                    dto.getEndDate()
                            .atTime(23, 59, 59);
        }

        return new FilterDateRange(
                startDate,
                endDate
        );
    }
    // =====================================
// FILTER DATE RANGE
// =====================================
    private record FilterDateRange(

            LocalDateTime startDate,

            LocalDateTime endDate

    ) {
    }

    // =====================================
// HOME TODOS
// =====================================
    @Override
    @Transactional(readOnly = true)
    public TodoHomeResponseDTO getHomeTodos(
            String username
    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        List<TodoResponseDTO> allTasks =
                todoRepository
                        .findMyAndSharedTodosWithoutPagination(user)
                        .stream()
                        .map(this::buildTodoResponse)
                        .toList();

        LocalDate today =
                LocalDate.now();

        List<TodoResponseDTO> todayTasks =
                allTasks.stream()
                        .filter(task ->

                                task.getTaskDateTime() != null

                                        &&

                                        task.getTaskDateTime()
                                                .toLocalDate()
                                                .isEqual(today)

                                        &&

                                        !task.isCompleted()
                        )
                        .toList();

        List<TodoResponseDTO> upcomingTasks =
                allTasks.stream()
                        .filter(task ->

                                task.getTaskDateTime() != null

                                        &&

                                        task.getTaskDateTime()
                                                .toLocalDate()
                                                .isAfter(today)

                                        &&

                                        !task.isCompleted()
                        )
                        .toList();

        List<TodoResponseDTO> completedTasks =
                allTasks.stream()
                        .filter(TodoResponseDTO::isCompleted)
                        .toList();

        List<TodoResponseDTO> overdueTasks =
                allTasks.stream()
                        .filter(task ->

                                task.getTaskDateTime() != null

                                        &&

                                        task.getTaskDateTime()
                                                .toLocalDate()
                                                .isBefore(today)

                                        &&

                                        !task.isCompleted()
                        )
                        .toList();

        return TodoHomeResponseDTO.builder()
                .allTasks(allTasks)
                .todayTasks(todayTasks)
                .upcomingTasks(upcomingTasks)
                .completedTasks(completedTasks)
                .overdueTasks(overdueTasks)
                .build();
    }

}