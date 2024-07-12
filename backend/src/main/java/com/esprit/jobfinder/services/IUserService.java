package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.PatchUserRequest;
import com.esprit.jobfinder.payload.request.UpdateUserReq;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user, MultipartFile profilePicture);
    Optional<User> getUserById(Long id);
    User updateUser(UpdateUserReq user, MultipartFile profilePicture) throws IOException;

    void deleteUserById(Long id);

    User patchUser(Long id,PatchUserRequest user);
    public Page<User> getAllUsers(String name, String email, String role, String phone, int page, int size, String sortBy,String sortOrder);
    void deleteInactiveUsers();
    Map<String, Object> getUserStatistics();
}
