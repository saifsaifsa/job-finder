package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
//    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUserById(Long id);

    User patchUser(Long id,PatchUserRequest user);
    public Page<User> getAllUsers(String name, String email, ERole role, String phone, int page, int size, String sortBy,String sortOrder);
}
