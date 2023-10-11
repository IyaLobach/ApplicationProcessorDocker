package com.example.ApplicationsProcessor.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "id"),
      inverseJoinColumns = @JoinColumn(name = "role"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"id", "role"})
  )
  private List<Role> role;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<Application> applications;

  public User() {
  }

  public int getId() {
    return id;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Application> getApplications() {
    return applications;
  }

  public void setApplications(
      List<Application> applications) {
    this.applications = applications;
  }

  public List<Role> getRole() {
    return role;
  }

  public List<String> getRoleTitle() {
    ArrayList<String> role = new ArrayList<>();
    for (Role r : this.role) {
      role.add(r.getRole().getTitle());
    }
    return role;
  }

  public void setRole(List<Role> role) {
    this.role = role;
  }

  public void addApplication(Application application) {
    if (applications == null) {
      applications = new ArrayList<>();
    }
    applications.add(application);
  }

  public void addRole(Role role) {
    if (this.role == null) {
      this.role = new ArrayList<>();
    }
    this.role.add(role);
  }

  public void setId(int id) {
    this.id = id;
  }
}
