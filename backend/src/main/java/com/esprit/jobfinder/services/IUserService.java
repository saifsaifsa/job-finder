package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUserById(Long id);
}
