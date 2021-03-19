package com.berry.hotelbooking.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Hotel {

  private Set<Room> rooms;

  private ConcurrentHashMap<BookingRoom, User> bookings;

  public Hotel(Builder hotelBuilder) {
    this.rooms = hotelBuilder.rooms;
    this.bookings = hotelBuilder.bookings;
  }

  public Set<Room> getRooms() {
    return this.rooms;
  }

  public ConcurrentHashMap<BookingRoom, User> getBookings() {
    return this.bookings;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hotel hotel = (Hotel) o;
    return rooms.equals(hotel.rooms) && bookings.equals(hotel.bookings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rooms, bookings);
  }

  @Override
  public String toString() {
    return "Hotel{" +
        "rooms=" + rooms +
        ", bookings=" + bookings +
        '}';
  }

  public static final class Builder {
    private Set<Room> rooms = new HashSet<>();
    private ConcurrentHashMap<BookingRoom, User> bookings = new ConcurrentHashMap<>();

    public Builder withRooms(Set<Room> rooms) {
      if (rooms == null) {
        throw new NullPointerException("Rooms container is required");
      }

      if (rooms.size() == 0) {
        throw new IllegalArgumentException("Invalid rooms.");
      }

      this.rooms = rooms;
      return this;
    }

    public Builder withBookings(ConcurrentHashMap<BookingRoom, User> bookings) {
      if (bookings == null) {
        bookings = new ConcurrentHashMap<>();
      }

      this.bookings = bookings;
      return this;
    }

    public Hotel build() {
      return new Hotel(this);
    }

  }

}
