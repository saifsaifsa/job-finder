package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.VerificationToken;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.repository.IUserRepository;
import com.esprit.jobfinder.repository.IVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class AuthService implements IAuthService{
    final private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IVerificationTokenRepository tokenRepository;
    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(String userName,String email, String password) throws RuntimeException{
        Boolean userExists = userRepository.existsByEmail(email);
        if (userExists) throw new RuntimeException("User already exists");

        Boolean userExistsByUsername = userRepository.existsByUsername(email);
        if (userExistsByUsername) throw new RuntimeException("User already exists");

        String hashedPassword = passwordEncoder.encode(password);
        User savedUser = new User(userName,email,hashedPassword);
        savedUser.setRole(ERole.ROLE_USER);
        userRepository.save(savedUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedUser);
        tokenRepository.save(verificationToken);

        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        emailService.sendSimpleMessage(savedUser.getEmail(), "Email Verification", "To verify your email, click the following link: " + verificationUrl);
    }
    public void verifyAccount(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if(verificationToken == null) throw new RuntimeException("Token invalid");
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new RuntimeException("Token invalid");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void login(String userName, String email, String password) {

    }
}
