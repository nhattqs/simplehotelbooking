package com.berry.hotelbooking.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBookingRoom {

  private static final int DEFAULT_ROOM_NUMBER = 10;

  @Test
  public void createBookingRoomSuccessfullyWithCorrectArguments() {
    Room room = Room.NewBuilder().withRoomNumber(DEFAULT_ROOM_NUMBER).build();
    LocalDate bookingDate = LocalDate.now();

    BookingRoom bookingRoom =
        BookingRoom
            .NewBuilder()
            .withRoom(room)
            .withBookingDate(bookingDate)
            .build();

    assertNotNull(bookingRoom);
    assertEquals(room, bookingRoom.getRoom());
    assertEquals(bookingDate, bookingRoom.getBookingDate());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullRoom() {
    LocalDate bookingDate = LocalDate.now();

    BookingRoom
        .NewBuilder()
        .withRoom(null)
        .withBookingDate(bookingDate)
        .build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullBookingDate() {
    Room room = Room.NewBuilder().withRoomNumber(DEFAULT_ROOM_NUMBER).build();

    BookingRoom
        .NewBuilder()
        .withRoom(room)
        .withBookingDate(null)
        .build();
  }

}
