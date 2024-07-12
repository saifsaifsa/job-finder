package com.esprit.jobfinder.services;

import com.esprit.jobfinder.dto.LinkedInUserInfo;
import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.exceptions.ConflictException;
import com.esprit.jobfinder.exceptions.UnauthorizedException;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.VerificationToken;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.response.AccessTokenResponse;
import com.esprit.jobfinder.repository.IUserRepository;
import com.esprit.jobfinder.repository.IVerificationTokenRepository;
import com.esprit.jobfinder.security.OAuthAuthenticationToken;
import com.esprit.jobfinder.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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
    private final RestTemplate restTemplate;

    public AuthService(IUserRepository userRepository,RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void signup(String userName,String email, String password, ERole role) throws RuntimeException{
        Boolean userExists = userRepository.existsByEmail(email);
        if (userExists) throw new ConflictException ("User already exists with email "+email);

        Boolean userExistsByUsername = userRepository.existsByUsername(userName);
        if (userExistsByUsername) throw new ConflictException("User already exists with username "+userName);

        String hashedPassword = passwordEncoder.encode(password);
        User savedUser = new User(userName,email,hashedPassword);
        savedUser.setRole(role);
        userRepository.save(savedUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedUser);
        tokenRepository.save(verificationToken);

        String verificationUrl = "http://localhost:4200/confirmation?token=" + token;
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
        tokenRepository.delete(verificationToken);
    }
    @Override
    public String login(String userName, String email, String password) {
        UserDetails userDetails;
//        if (userName != null) {
//            userDetails = userDetailsService.loadUserByUsername(userName);
//        }
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent ()) {
                throw new UnauthorizedException ("Bad credentials");
            }
            userDetails = new UserDetailsImpl(user.get ());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken (userDetails.getUsername(), password)
        );
        User existsUser = user.get();
        existsUser.setLastLogin(LocalDateTime.now());
        userRepository.save(existsUser);
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

            String resetLink = "http://localhost:4200/reset-password?token=" + token;
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
        } else {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    @Override
    public void generateSuperAdmin() {
        Optional<User> userOptional = userRepository.findByEmail("admin@jobfinder.tn");
        if (!userOptional.isPresent()) {
            String hashedPassword = passwordEncoder.encode("Admin1234");
            User savedUser = new User("admin", "admin@jobfinder.tn", hashedPassword);
            savedUser.setRole(ERole.ROLE_ADMIN);
            savedUser.setActive(true);
            savedUser.setFirstName("admin");
            savedUser.setLastName("jobFinder");
            savedUser.setPhone("+21622001003");
            userRepository.save(savedUser);
        }
    }
    public String oauthLinkedin(String code) throws Exception {
        String clientId = "77lz7wunm0s5jn";
        String clientSecret = "24uSXbMPN9VSXjPm";
        String redirectUri = "http://127.0.0.1:4200/callback";

        // Prepare request body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", code);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send POST request to LinkedIn token endpoint
        ResponseEntity<AccessTokenResponse> responseEntity = restTemplate.exchange(
                "https://www.linkedin.com/oauth/v2/accessToken",
                HttpMethod.POST,
                requestEntity,
                AccessTokenResponse.class);

        AccessTokenResponse accessTokenResponse = responseEntity.getBody();

        // Handle errors or proceed with access token
        if (accessTokenResponse != null) {
            String accessToken = accessTokenResponse.getAccess_token();

            LinkedInUserInfo userProfile = fetchLinkedinProfile(accessToken);
            Optional<User> existingUser = userRepository.findByEmail(userProfile.getEmail());
            if (existingUser.isPresent()) {
                UserDetailsImpl userDetailsImpl =new UserDetailsImpl(existingUser.get());
                existingUser.get().setPassword(new BCryptPasswordEncoder ().encode("default"));
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken (userDetailsImpl.getUsername(), "default")
                );SecurityContextHolder.getContext().setAuthentication(authentication);
                return jwtUtil.generateJwtToken ( authentication );
            } else {
                User savedUser = new User(userProfile.getName(),userProfile.getFamilyName(),userProfile.getName().charAt(0)+userProfile.getFamilyName(),userProfile.getEmail(),"");
                savedUser.setProfilePicture(userProfile.getPicture());
                savedUser.setActive(true);
                savedUser.setRole(ERole.ROLE_USER);
                userRepository.save(savedUser);
                savedUser.setPassword(new BCryptPasswordEncoder ().encode("default"));
                UserDetailsImpl userDetailsImpl =new UserDetailsImpl(savedUser);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken (userDetailsImpl.getUsername(), "default")
                );SecurityContextHolder.getContext().setAuthentication(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return jwtUtil.generateJwtToken ( authentication );
            }

        } else {
            throw  new BadRequestException("Failed to obtain access token");
        }
    }

    private LinkedInUserInfo fetchLinkedinProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<LinkedInUserInfo> responseEntity = restTemplate.exchange(
                "https://api.linkedin.com/v2/userinfo",
                HttpMethod.GET,
                entity,
                LinkedInUserInfo.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new BadRequestException("Failed to fetch LinkedIn profile");
        }
    }
}
