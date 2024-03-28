package com.springprac.learningUserVerification.event.listener;

import com.springprac.learningUserVerification.event.RegistrationCompletionEvent;
import com.springprac.learningUserVerification.user.UserEntity;
import com.springprac.learningUserVerification.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompletionEventListener implements ApplicationListener<RegistrationCompletionEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompletionEvent event) {
        // 1. get the newly registered user
        UserEntity user = event.getUser();

        // 2. create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // 3. save the verification token for the user
        userService.saveUserVerificationToken(user, verificationToken);

        // 4. build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

        // 5. send the email
        try {
            sendVerificationEmail(user, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your email: {}", url);

    }

    public void sendVerificationEmail(UserEntity user, String url) throws Exception {
        String subject = "EMail verification link for spring boot project";
        String senderName = "User Registration";
        String mailContent = "<p>Link: </p><br /><a href=\""+url+"\">Click here</a>";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("insertYourServerEmail@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
