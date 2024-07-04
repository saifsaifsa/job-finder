package com.esprit.jobfinder.services;

public interface IAuthService {
    public void signup(String userName,String email,String password);
    public void verifyAccount(String token);

    public String login(String userName,String email,String password);
    void generatePasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
    void generateSuperAdmin();
}
