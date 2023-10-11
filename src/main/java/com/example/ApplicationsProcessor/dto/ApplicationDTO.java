package com.example.ApplicationsProcessor.dto;

import com.example.ApplicationsProcessor.models.Status;
import java.util.Date;
public class ApplicationDTO {

  private int id;
  private String text;

  private Status status;
  private Date date;
  private UserForViewDTO userForViewDTO;

  public ApplicationDTO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void applicationTextConversion() {
    text = text.replaceAll(".", "$0-");
  }

  public UserForViewDTO getUserForViewDTO() {
    return userForViewDTO;
  }

  public void setUserForViewDTO(UserForViewDTO userForViewDTO) {
    this.userForViewDTO = userForViewDTO;
  }
}
