package com.esprit.jobfinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  IUserRepository IUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = IUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return new UserDetailsImpl(user);
  }

}
