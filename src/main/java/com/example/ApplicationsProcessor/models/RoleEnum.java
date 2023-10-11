package com.example.ApplicationsProcessor.models;

public enum RoleEnum {
  ROLE_USER ("ROLE_USER"),
  ROLE_ADMIN ("ROLE_ADMIN"),
  ROLE_OPERATOR ("ROLE_OPERATOR");

  private String title;

  RoleEnum(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}