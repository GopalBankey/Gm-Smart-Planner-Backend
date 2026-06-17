package com.gmsmartplanner.controller;

import com.gmsmartplanner.dto.response.NotificationResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService
            notificationService;

    // =====================================
    // GET NOTIFICATIONS
    // =====================================
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationResponseDTO>>>
    getNotifications(

            Authentication authentication,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<NotificationResponseDTO> response =
                notificationService.getNotifications(

                        authentication.getName(),

                        pageable
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Page<NotificationResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Notifications fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // MARK AS READ
    // =====================================
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>>
    markNotificationAsRead(

            Authentication authentication,

            @PathVariable
            Long notificationId

    ) {

        notificationService.markNotificationAsRead(

                authentication.getName(),

                notificationId
        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Notification marked as read"
                        )

                        .build()
        );
    }

    // =====================================
    // UNREAD COUNT
    // =====================================
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>>
    getUnreadNotificationCount(

            Authentication authentication

    ) {

        long count =
                notificationService
                        .getUnreadNotificationCount(

                                authentication.getName()
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Long>builder()

                        .success(true)

                        .message(
                                "Unread notification count fetched"
                        )

                        .data(count)

                        .build()
        );
    }


    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse>
    markAllNotificationsAsRead(

            Authentication authentication

    ) {

        notificationService
                .markAllNotificationsAsRead(

                        authentication.getName()
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "All notifications marked as read"
                        )

                        .build()
        );

    }

}