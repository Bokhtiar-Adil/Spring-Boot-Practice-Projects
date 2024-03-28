package com.springprac.learningUserVerification.user;

import com.springprac.learningUserVerification.exception.UserAlreadyExistsException;
import com.springprac.learningUserVerification.registration.RegistrationRequest;
import com.springprac.learningUserVerification.registration.token.VerificationToken;
import com.springprac.learningUserVerification.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // auto-wires the final dependencies
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity registerUser(RegistrationRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.email());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        var newUser = new UserEntity();
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(UserEntity user, String token) {
        var verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        // does it exist?
        if (token == null) {
            return "Invalid verification token";
        }
        // exists, get the user
        UserEntity user = token.getUser();
        // is the token expired?
        Calendar calendar = Calendar.getInstance();
        if (token.getTokenExpirationTime().getTime() <= calendar.getTime().getTime()) {
            verificationTokenRepository.delete(token);
            return "Token already expired";
        }
        // not expired, so enable the user
        user.setEnabled(true);
        userRepository.save(user);
        return "Valid and enabled";
    }
}
