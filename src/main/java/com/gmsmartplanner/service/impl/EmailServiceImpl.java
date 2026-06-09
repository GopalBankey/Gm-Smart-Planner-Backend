package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl
        implements EmailService {

    private final JavaMailSender
            mailSender;

    // =========================================
    // SEND EMAIL OTP
    // =========================================
    @Override
    public void sendOtpEmail(

            String toEmail,

            String otp

    ) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );

            helper.setTo(toEmail);

            helper.setSubject(
                    "Email Verification OTP - GM Smart Planner"
            );

            helper.setText(

                    """
                    <html>
                        <body style="
                                font-family: Arial, sans-serif;
                                background-color: #f5f5f5;
                                padding: 20px;
                        ">
                        
                            <div style="
                                    max-width: 500px;
                                    margin: auto;
                                    background: white;
                                    padding: 30px;
                                    border-radius: 10px;
                                    text-align: center;
                            ">
                            
                                <h2 style="
                                        color: #2563eb;
                                ">
                                    Verify Your Email
                                </h2>
                                
                                <p style="
                                        font-size: 16px;
                                        color: #555;
                                ">
                                    Use the OTP below to verify your email address.
                                </p>
                                
                                <div style="
                                        margin: 30px 0;
                                ">
                                
                                    <span style="
                                            display: inline-block;
                                            background: #2563eb;
                                            color: white;
                                            padding: 14px 28px;
                                            font-size: 28px;
                                            font-weight: bold;
                                            border-radius: 8px;
                                            letter-spacing: 4px;
                                    ">
                                        %s
                                    </span>
                                    
                                </div>
                                
                                <p style="
                                        font-size: 14px;
                                        color: #777;
                                ">
                                    This OTP is valid for a limited time.
                                </p>
                                
                            </div>
                            
                        </body>
                    </html>
                    """.formatted(
                            otp
                    ),

                    true
            );

            mailSender.send(message);

            log.info(
                    "Email OTP sent successfully to : {}",
                    toEmail
            );

        } catch (MessagingException ex) {

            log.error(
                    "Failed to send email OTP : {}",
                    ex.getMessage()
            );

            throw new RuntimeException(
                    "Failed to send email OTP"
            );
        }
    }
}