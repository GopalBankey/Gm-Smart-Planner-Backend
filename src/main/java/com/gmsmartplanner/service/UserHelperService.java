package com.gmsmartplanner.service;

import com.gmsmartplanner.entity.User;

public interface UserHelperService {

    // =====================================
    // GET CURRENT USER
    // =====================================

    User getCurrentUser(
            String username
    );
}