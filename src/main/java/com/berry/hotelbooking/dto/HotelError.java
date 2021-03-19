package com.berry.hotelbooking.dto;

import java.util.Objects;

public class HotelError {

  private String message;

  public HotelError(Builder hotelErrorBuilder) {
    this.message = hotelErrorBuilder.message;
  }

  public String getMessage() {
    return message;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HotelError that = (HotelError) o;
    return Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    return "HotelError{" +
        "message='" + message + '\'' +
        '}';
  }

  public static final class Builder {
    private String message;
    
    public Builder withMessage(String message) {
      if (message == null || "".equals(message)) {
        throw new NullPointerException("Message is required.");
      }
      this.message = message;
      return this;
    }

    public HotelError build() {
      return new HotelError(this);
    }
  }

}
