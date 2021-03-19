package com.berry.hotelbooking.model;

public class Booking {

  private User user;
  private BookingRoom bookingRoom;

  public Booking(Builder bookingBuilder) {
    this.user = bookingBuilder.user;
    this.bookingRoom = bookingBuilder.bookingRoom;
  }

  public User getUser() {
    return this.user;
  }

  public BookingRoom getBookingRoom() {
    return this.bookingRoom;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private User user;
    private BookingRoom bookingRoom;

    public Builder withUser(User user) {
      if (user == null) {
        throw new NullPointerException("User is required.");
      }

      this.user = user;
      return this;
    }

    public Builder withBookingRoom(BookingRoom bookingRoom) {
      if (bookingRoom == null) {
        throw new NullPointerException("BookingRoom is required.");
      }

      this.bookingRoom = bookingRoom;
      return this;
    }

    public Booking build() {
      return new Booking(this);
    }
  }

}
