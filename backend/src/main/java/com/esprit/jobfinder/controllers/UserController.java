package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import com.esprit.jobfinder.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        System.out.println("user: "+user);
        User savedUser = userService.saveUser(user);
        user.setPassword("");
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

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserReq updateReq) {

        User userDetails = new User();
        userDetails.setId(id);
        userDetails.setRole(updateReq.getRole());
        userDetails.setEmail(updateReq.getEmail());
        userDetails.setFirstName(updateReq.getFirstName());
        userDetails.setLastName(updateReq.getLastName());
        userDetails.setPhone(updateReq.getPhone());
        userDetails.setUsername(updateReq.getUsername());
        User updatedUser = userService.updateUser(userDetails);
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