package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{
    final private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(String userName,String email, String password) throws RuntimeException{
        // get user by email to check if he already exists
        Boolean userExists = userRepository.existsByEmail(email);
        if (userExists) throw new RuntimeException("User already exists");
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(userName,email,hashedPassword);
        userRepository.save(user);
    }
}
