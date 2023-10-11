package com.example.ApplicationsProcessor.dto;

import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

  private int Id;
  private String name;
  private String surname;
  private String email;
  private List<RoleEnum> roleEnum = new ArrayList<>();

  public UserDTO() {
  }

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<RoleEnum> getRoleEnum() {
    return roleEnum;
  }

  public void setRoleEnum(List<Role> role) {
    for (Role r : role) {
      this.roleEnum.add(r.getRole());
    }
  }
}
