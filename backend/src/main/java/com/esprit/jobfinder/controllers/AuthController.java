package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.payload.request.ForgotPasswordRequest;
import com.esprit.jobfinder.payload.request.ResetPasswordRequest;
import com.esprit.jobfinder.services.IAuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.esprit.jobfinder.payload.request.LoginRequest;
import com.esprit.jobfinder.payload.request.SignupRequest;
import com.esprit.jobfinder.payload.response.JwtResponse;
import com.esprit.jobfinder.payload.response.MessageResponse;

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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String token = authService.login (loginRequest.getUsername (),loginRequest.getEmail (),loginRequest.getPassword ());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      this.authService.signup(signUpRequest.getUsername(),signUpRequest.getEmail(),signUpRequest.getPassword());
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
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

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    authService.generatePasswordResetToken(request.getEmail());
    return ResponseEntity.ok(new MessageResponse("Password reset link sent!"));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request.getToken(), request.getNewPassword());
    return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
  }
}
