package com.example.ApplicationsProcessor.services;

import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import com.example.ApplicationsProcessor.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService {

  private final IRoleRepository roleRepository;

  @Autowired
  public RoleService(IRoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role findById(int id) {
    return roleRepository.findById(id).get();
  }

  @Transactional
  public void update(int id, Role role) {
    role.setId(id);
    roleRepository.save(role);
  }

  public Role findByRoleEnum(RoleEnum roleEnum) {
    return (Role) roleRepository.findByRole(roleEnum);
  }

  @Transactional
  public void save(Role role) {
    roleRepository.save(role);
  }

}
