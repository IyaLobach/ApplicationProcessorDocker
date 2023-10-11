package com.example.ApplicationsProcessor.services;


import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.repositories.IUserRepository;
import com.example.ApplicationsProcessor.util.UserException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

  private final IUserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional
  public void update(int id, User user) {
    user.setId(id);
    userRepository.save(user);
  }

  @Transactional
  public void updateRole(int id, Role role) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserException("Пользователь не найден");
    }
    List<Role> roles = user.get().getRole();
    for (Role r : roles) {
      if (r.getRole().getTitle().equals("Оператор")) {
        return;
      }
    }
    role.addUser(user.get());
    user.get().addRole(role);
  }

  public User findById(int id) {
    return userRepository.findById(id).get();
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> findByUserName(String userName) {
    return userRepository.findByNameContaining(userName);
  }


}
