package com.example.ApplicationsProcessor.repositories;

import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
  Role findByRole(RoleEnum roleEnum);
}
