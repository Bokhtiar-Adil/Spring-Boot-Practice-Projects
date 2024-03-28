package com.springprac.learningUserVerification.registration;

import com.springprac.learningUserVerification.event.RegistrationCompletionEvent;
import com.springprac.learningUserVerification.registration.token.VerificationToken;
import com.springprac.learningUserVerification.registration.token.VerificationTokenRepository;
import com.springprac.learningUserVerification.user.UserEntity;
import com.springprac.learningUserVerification.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping()
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {
        UserEntity user = userService.registerUser(registrationRequest);
        // publish registration event
        publisher.publishEvent(new RegistrationCompletionEvent(user, applicationUrl(request)));

        return "Success! Check your mail to confirm registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()) {
            return "This account has already been verified";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("Valid and enabled")) {
            return "Email verified successfully";
        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }



}
