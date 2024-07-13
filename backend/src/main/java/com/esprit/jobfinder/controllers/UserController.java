package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.dto.UserMapper;
import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.CreateUserReq;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import com.esprit.jobfinder.services.ICompetenceService;
import com.esprit.jobfinder.services.IUserService;
import com.esprit.jobfinder.services.SkillService;
import com.esprit.jobfinder.utiles.DateUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    private final ICompetenceService competenceService;

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<User> createUser(@Valid @ModelAttribute CreateUserReq user) {
        User newUser = UserMapper.createUserReqToUser(user);
        List<Competence> competenceList = this.competenceService.findByids(user.getSkills());
        newUser.setSkills(competenceList);
        User savedUser = userService.saveUser(newUser,user.getPhoto());
        user.setPassword(null);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<User> users = userService.getAllUsers(name, email, role, phone, page, size, sortBy, sortOrder);
        return ResponseEntity.ok(users);
    }

    @PutMapping(path="/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<User> updateUser(@PathVariable Long id,@Valid @ModelAttribute UpdateUserReq updateReq) {
        try {
            User updatedUser = userService.updateUser(updateReq,updateReq.getPhoto());
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody PatchUserRequest userDetails) {
        User updatedUser = userService.patchUser(id,userDetails);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUsersStats() {
        return ResponseEntity.ok(userService.getUserStatistics());
    }
}