package com.esprit.jobfinder.services;

public interface IAuthService {
    public void signup(String userName,String email,String password);
    public void verifyAccount(String token);

    public void login(String userName,String email,String password);
}
