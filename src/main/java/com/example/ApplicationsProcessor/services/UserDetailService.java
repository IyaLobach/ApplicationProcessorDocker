package com.example.ApplicationsProcessor.services;

import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.repositories.IUserRepository;
import com.example.ApplicationsProcessor.security.UserDetail;
import com.example.ApplicationsProcessor.util.UserException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {


  private final IUserRepository userRepository;

  @Autowired
  public UserDetailService(
      IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userRepository.findByEmail(username);
    if (user.isEmpty()) {
      throw new UserException("Пользователь не найден");
    }
    return new UserDetail(user.get());
  }
}
