package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.CreateUserReq;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import com.esprit.jobfinder.services.IUserService;
import com.esprit.jobfinder.utiles.DateUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<User> createUser(@Valid @ModelAttribute CreateUserReq user) {
        User newUser = new User();
        newUser.setBirthDay(DateUtils.parseDate(user.getBirthDay()));
        newUser.setUsername(user.getUsername());
        newUser.setRole(ERole.valueOf(user.getRole()));
        newUser.setPassword(user.getPassword());
        newUser.setPhone(user.getPhone());
        newUser.setLastName(user.getLastName());
        newUser.setFirstName(user.getFirstName());
        newUser.setEmail(user.getEmail());
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

        User userDetails = new User();
        userDetails.setId(id);
        userDetails.setRole(ERole.valueOf(updateReq.getRole()));
        userDetails.setEmail(updateReq.getEmail());
        userDetails.setFirstName(updateReq.getFirstName());
        userDetails.setLastName(updateReq.getLastName());
        userDetails.setPhone(updateReq.getPhone());
        userDetails.setUsername(updateReq.getUsername());
        userDetails.setBirthDay(DateUtils.parseDate(updateReq.getBirthDay()));
        User updatedUser = null;
        try {
            updatedUser = userService.updateUser(userDetails,updateReq.getPhoto());
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
        return ResponseEntity.ok(updatedUser);
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
}