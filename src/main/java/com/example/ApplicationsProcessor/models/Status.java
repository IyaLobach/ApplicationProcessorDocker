package com.example.ApplicationsProcessor.models;

public enum Status {
  DRAFT("Черновик"),
  SUBMITTED("Отправлена"),
  REJECTED("Отклонена"),
  ACCEPTED("Принята");

  private String title;

  Status(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
