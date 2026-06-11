package com.gmsmartplanner.service;

public interface AccessUserService {

    String getEffectiveUsername(

            String username,

            Long accessId
    );

    void checkViewPermission(
            String username,
            Long accessId
    );

    void checkCreatePermission(
            String username,
            Long accessId
    );

    void checkUpdatePermission(
            String username,
            Long accessId
    );

    void checkDeletePermission(
            String username,
            Long accessId
    );

    void checkTakePermission(
            String username,
            Long accessId
    );
}