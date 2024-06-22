package com.esprit.jobfinder.services;

import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new NotFoundException("User not found with id: " + user.getId());
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User patchUser(Long userId,PatchUserRequest patchUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (patchUserRequest.getUsername() != null) {
            user.setUsername(patchUserRequest.getUsername());
        }
        if (patchUserRequest.getFirstName() != null) {
            user.setFirstName(patchUserRequest.getFirstName());
        }
        if (patchUserRequest.getLastName() != null) {
            user.setLastName(patchUserRequest.getLastName());
        }
        if (patchUserRequest.getActive() != null) {
            user.setActive(patchUserRequest.getActive());
        }
        if (patchUserRequest.getEmail() != null) {
            user.setEmail(patchUserRequest.getEmail());
        }
        if (patchUserRequest.getPassword() != null) {
            user.setPassword(patchUserRequest.getPassword());
        }
        if (patchUserRequest.getPhone() != null) {
            user.setPhone(patchUserRequest.getPhone());
        }
        if (patchUserRequest.getRole() != null) {
            user.setRole(patchUserRequest.getRole());
        }
        return userRepository.save(user);
    }
}
