package com.redditCloneServer.redditCloneServer.service;

import com.redditCloneServer.redditCloneServer.dto.AuthenticationToken;
import com.redditCloneServer.redditCloneServer.dto.LoginRequest;
import com.redditCloneServer.redditCloneServer.dto.RegisterRequest;
import com.redditCloneServer.redditCloneServer.exception.SpringRedditException;
import com.redditCloneServer.redditCloneServer.model.NotificationEmail;
import com.redditCloneServer.redditCloneServer.model.User;
import com.redditCloneServer.redditCloneServer.model.VerificationToken;
import com.redditCloneServer.redditCloneServer.repository.UserRepository;
import com.redditCloneServer.redditCloneServer.repository.VerificationTokenRepository;
import com.redditCloneServer.redditCloneServer.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + "http://localhost:8080/api/auth/accountVerification/" + token + "end");

        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
    }

    private String generateVerificationToken(User user) {

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not Found With the name " + username));
        user.setEnabled(true);

        userRepository.save(user);
    }


    public ResponseEntity<AuthenticationToken> login(LoginRequest loginRequest) {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);

            authenticationToken.setAuthentication(token);
            authenticationToken.setUsername(loginRequest.getUsername());

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<AuthenticationToken>(authenticationToken, HttpStatus.OK);
    }
}
