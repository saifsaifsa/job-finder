package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.payload.request.*;
import com.esprit.jobfinder.payload.request.ForgotPasswordRequest;
import com.esprit.jobfinder.payload.request.ResetPasswordRequest;
import com.esprit.jobfinder.services.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.esprit.jobfinder.payload.response.JwtResponse;
import com.esprit.jobfinder.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String token = authService.login (loginRequest.getUsername (),loginRequest.getEmail (),loginRequest.getPassword ());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      this.authService.signup(signUpRequest.getUsername(),signUpRequest.getEmail(),signUpRequest.getPassword(),signUpRequest.getRole());
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
  @PostMapping("/generate-admin")
    public ResponseEntity<?> generateSuperAdmin(@RequestBody AdminRequest request) {
        if (request.getCode().equals("12345678")) {
            authService.generateSuperAdmin();
            return ResponseEntity.ok(new MessageResponse("admin generated!"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("code invalid"));
    }
    @PostMapping(  value = "/linkedin",consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<?> oauthLinkedin(@RequestParam("code") String code) throws Exception {
        String token = authService.oauthLinkedin(code);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}

