package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.enums.ERole;

public interface IAuthService {
    public void signup(String userName, String email, String password, ERole role);
    public void verifyAccount(String token);

    public String login(String userName,String email,String password);
    void generatePasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
    void generateSuperAdmin();
    String oauthLinkedin(String code) throws Exception;
}
