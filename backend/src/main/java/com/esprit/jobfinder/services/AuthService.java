package com.esprit.jobfinder.services;

import com.esprit.jobfinder.exceptions.ConflictException;
import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.exceptions.UnauthorizedException;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.VerificationToken;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.repository.IUserRepository;
import com.esprit.jobfinder.repository.IVerificationTokenRepository;
import com.esprit.jobfinder.security.jwt.JwtUtils;
import com.esprit.jobfinder.utiles.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(String userName,String email, String password) throws RuntimeException{
        Boolean userExists = userRepository.existsByEmail(email);
        if (userExists) throw new ConflictException ("User already exists with email "+email);

        Boolean userExistsByUsername = userRepository.existsByUsername(userName);
        if (userExistsByUsername) throw new ConflictException("User already exists with username "+userName);

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
    public String login(String userName, String email, String password) {
        UserDetails userDetails;
        if (userName != null) {
            userDetails = userDetailsService.loadUserByUsername(userName);
        } else {
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent ()) {
                throw new UnauthorizedException ("Bad credentials");
            }
            userDetails = new UserDetailsImpl(user.get ());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken (userDetails.getUsername(), password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtil.generateJwtToken ( authentication );
    }
    @Override
    public void generatePasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken(token, user);
            tokenRepository.save(verificationToken);

            String resetLink = "http://localhost:3000/reset-password?token=" + token;
            emailService.sendSimpleMessage (user.getEmail(), "Password Reset Request", resetLink);
        }
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken != null && !verificationToken.isExpired()) {
            User user = verificationToken.getUser();
            user.setPassword(new BCryptPasswordEncoder ().encode(newPassword));
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
        } else {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}
