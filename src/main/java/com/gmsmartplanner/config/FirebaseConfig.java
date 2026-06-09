package com.gmsmartplanner.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {

        try {

            InputStream serviceAccount =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(
                                    "firebase/firebase-service-account.json"
                            );

            if (serviceAccount == null) {

                throw new RuntimeException(
                        "Firebase service account file not found"
                );
            }

            FirebaseOptions options =
                    FirebaseOptions.builder()

                            .setCredentials(
                                    GoogleCredentials
                                            .fromStream(serviceAccount)
                            )

                            .build();

            if (FirebaseApp.getApps()
                    .isEmpty()) {

                FirebaseApp.initializeApp(
                        options
                );

                log.info(
                        "Firebase initialized successfully"
                );
            }

        } catch (Exception e) {

            log.error(
                    "Failed to initialize Firebase",
                    e
            );

            throw new RuntimeException(
                    e
            );
        }
    }
}