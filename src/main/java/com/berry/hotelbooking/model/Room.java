package com.berry.hotelbooking.model;

import java.util.Objects;

public class Room {

  private int roomNumber;

  private Room(Builder roomBuilder) {
    this.roomNumber = roomBuilder.roomNumber;
  }

  public int getRoomNumber() {
    return this.roomNumber;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Room room = (Room) o;
    return roomNumber == room.roomNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomNumber);
  }

  @Override
  public String toString() {
    return "Room{" +
        "roomNumber=" + roomNumber +
        '}';
  }

  public static final class Builder {
    private int roomNumber;

    public Builder withRoomNumber(int roomNumber) {
      if (roomNumber <= 0) {
        throw new IllegalArgumentException("Invalid room number.");
      }

      this.roomNumber = roomNumber;
      return this;
    }

    public Room build() {
      return new Room(this);
    }
  }

}
