package com.berry.hotelbooking.model;

import java.time.LocalDate;
import java.util.Objects;

public class BookingRoom {

  private Room room;
  private LocalDate bookingDate;

  private BookingRoom(Builder builder) {
    this.room = builder.room;
    this.bookingDate = builder.bookingDate;
  }

  public Room getRoom() {
    return this.room;
  }

  public LocalDate getBookingDate() {
    return this.bookingDate;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookingRoom that = (BookingRoom) o;
    return room.equals(that.room) && bookingDate.equals(that.bookingDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room, bookingDate);
  }

  @Override
  public String toString() {
    return "BookingRoom{" +
        "room=" + room +
        ", bookingDate=" + bookingDate +
        '}';
  }

  public static final class Builder {
    private Room room;
    private LocalDate bookingDate;

    public Builder withRoom(Room room) {
      if (room == null) {
        throw new NullPointerException("Room is required.");
      }

      this.room = room;
      return this;
    }

    public Builder withBookingDate(LocalDate bookingDate) {
      if (bookingDate == null) {
        throw new NullPointerException("Booking date is required.");
      }

      this.bookingDate = bookingDate;
      return this;
    }

    public BookingRoom build() {
      return new BookingRoom(this);
    }

  }

}
