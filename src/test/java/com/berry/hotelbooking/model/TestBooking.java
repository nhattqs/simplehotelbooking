package com.berry.hotelbooking.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBooking {

  private static final int DEFAULT_ROOM_NUMBER = 10;
  private static final String DEFAULT_USER_NAME = "John Smith";

  @Test
  public void createBookingSuccessfullyWithCorrectArguments() {
    User user = User.NewBuilder().withName(DEFAULT_USER_NAME).build();
    Room room = Room.NewBuilder().withRoomNumber(DEFAULT_ROOM_NUMBER).build();
    LocalDate bookingDate = LocalDate.now();
    BookingRoom bookingRoom =
        BookingRoom.NewBuilder().withRoom(room).withBookingDate(bookingDate).build();

    Booking booking =
        Booking
            .NewBuilder()
            .withUser(user)
            .withBookingRoom(bookingRoom)
            .build();

    assertNotNull(booking);
    assertEquals(user, booking.getUser());
    assertEquals(bookingRoom, booking.getBookingRoom());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullUser() {
    Room room = Room.NewBuilder().withRoomNumber(DEFAULT_ROOM_NUMBER).build();
    LocalDate bookingDate = LocalDate.now();
    BookingRoom bookingRoom =
        BookingRoom.NewBuilder().withRoom(room).withBookingDate(bookingDate).build();

    Booking
        .NewBuilder()
        .withUser(null)
        .withBookingRoom(bookingRoom)
        .build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullRoom() {
    User user = User.NewBuilder().withName(DEFAULT_USER_NAME).build();

    Booking
        .NewBuilder()
        .withUser(user)
        .withBookingRoom(null)
        .build();
  }

}
