package com.springprac.learningUserVerification.user;

import com.springprac.learningUserVerification.registration.RegistrationRequest;
import com.springprac.learningUserVerification.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getUsers();
    UserEntity registerUser(RegistrationRequest request);
    Optional<UserEntity> findByEmail(String email);

    void saveUserVerificationToken(UserEntity user, String verificationToken);

    String validateToken(String verificationToken);
}
