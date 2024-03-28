package com.springprac.learningUserVerification.event;

import com.springprac.learningUserVerification.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class RegistrationCompletionEvent extends ApplicationEvent {

    private UserEntity user;
    private String applicationUrl; // to verify, click on this link

    public RegistrationCompletionEvent(UserEntity user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
