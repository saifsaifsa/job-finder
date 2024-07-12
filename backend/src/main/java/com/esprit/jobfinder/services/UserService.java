package com.esprit.jobfinder.services;

import com.esprit.jobfinder.dto.UserMapper;
import com.esprit.jobfinder.exceptions.ConflictException;
import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.VerificationToken;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import com.esprit.jobfinder.repository.IUserRepository;
import com.esprit.jobfinder.repository.IVerificationTokenRepository;
import com.esprit.jobfinder.security.jwt.JwtUtils;
import com.esprit.jobfinder.utiles.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;
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

    @Autowired
    private IFileUploaderService fileUploaderService;

    @Autowired
    private ICompetenceService competenceService;
    @Override
    public User saveUser(User user, MultipartFile profilePicture) {

        Boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) throw new ConflictException("User already exists with email "+user.getEmail());

        Boolean userExistsByUsername = userRepository.existsByUsername(user.getUsername());
        if (userExistsByUsername) throw new ConflictException("User already exists with username "+user.getUsername());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String filePath = null;
            try {
                filePath = fileUploaderService.uploadFile(profilePicture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            user.setProfilePicture(filePath);
        }
        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);

        String verificationUrl = "http://127.0.0.1:4200/confirmation?token=" + token;
        emailService.sendSimpleMessage(user.getEmail(), "Email Verification", "To verify your email, click the following link: " + verificationUrl);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
    public Page<User> getAllUsers(String name, String email, String role, String phone, int page, int size, String sortBy,String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));

        Specification<User> spec = Specification.where(UserSpecification.hasName(name))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasRole(role))
                .and(UserSpecification.hasPhone(phone));

        return userRepository.findAll(spec, pageable);
    }
    @Override
    public User updateUser(UpdateUserReq user, MultipartFile profilePicture) throws IOException {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + user.getId()));

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String filePath = fileUploaderService.uploadFile(profilePicture);
            existingUser.setProfilePicture(filePath);
        }
        List<Competence> competenceList = this.competenceService.findByids(user.getSkills());
        existingUser.setSkills(competenceList);
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setBirthDay(DateUtils.parseDate(user.getBirthDay()));
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(hashedPassword);
        }
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(ERole.valueOf(user.getRole()));
//        existingUser.setSkills(user.getSkills());
        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User patchUser(Long userId,PatchUserRequest patchUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (patchUserRequest.getUsername() != null) {
            user.setUsername(patchUserRequest.getUsername());
        }
        if (patchUserRequest.getFirstName() != null) {
            user.setFirstName(patchUserRequest.getFirstName());
        }
        if (patchUserRequest.getLastName() != null) {
            user.setLastName(patchUserRequest.getLastName());
        }
        if (patchUserRequest.getActive() != null) {
            user.setActive(patchUserRequest.getActive());
        }
        if (patchUserRequest.getEmail() != null) {
            user.setEmail(patchUserRequest.getEmail());
        }
        if (patchUserRequest.getPassword() != null) {
            user.setPassword(patchUserRequest.getPassword());
        }
        if (patchUserRequest.getPhone() != null) {
            user.setPhone(patchUserRequest.getPhone());
        }
        if (patchUserRequest.getRole() != null) {
            user.setRole(patchUserRequest.getRole());
        }
        return userRepository.save(user);
    }

    public void deleteInactiveUsers() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<User> inactiveUsers = userRepository.findInactiveUsers(oneMonthAgo);
        List<Long> ids = inactiveUsers.stream().map(User::getId).toList();
        userRepository.deleteAllByIdIn(ids);
    }
    public Map<String, Object> getUserStatistics() {
        long totalUsers = userRepository.countAllUsers();
        long activeUsers = userRepository.countActiveUsers();
        List<Object[]> usersByRole = userRepository.countUsersByRole();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);

        Map<String, Long> roleStats = new HashMap<>();
        for (Object[] result : usersByRole) {
            ERole role = (ERole) result[0];
            long count = (long) result[1];
            roleStats.put(role.name(), count);
        }
        stats.put("usersByRole", roleStats);

        return stats;
    }
}
