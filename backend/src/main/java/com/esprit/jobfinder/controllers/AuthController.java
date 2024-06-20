package com.esprit.jobfinder.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.esprit.jobfinder.services.IAuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.models.Role;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.payload.request.LoginRequest;
import com.esprit.jobfinder.payload.request.SignupRequest;
import com.esprit.jobfinder.payload.response.JwtResponse;
import com.esprit.jobfinder.payload.response.MessageResponse;
import com.esprit.jobfinder.repository.RoleRepository;
import com.esprit.jobfinder.repository.IUserRepository;
import com.esprit.jobfinder.security.jwt.JwtUtils;
import com.esprit.jobfinder.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  IAuthService authService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
      this.authService.signup(signUpRequest.getUsername(),signUpRequest.getEmail(),signUpRequest.getPassword());
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }catch (Exception e){
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse(e.getMessage()));
    }
  }

  @GetMapping("/verify")
 public ResponseEntity<?> verifyAccount(@RequestParam("token") String token){
    try {
      authService.verifyAccount(token);
      return ResponseEntity.ok(new MessageResponse("User account validated successfully!"));
    }catch (Exception e){
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse(e.getMessage()));
    }
 }
}
