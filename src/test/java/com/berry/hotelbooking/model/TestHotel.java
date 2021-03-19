package com.berry.hotelbooking.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestHotel {

  @Test
  public void createHotelSuccessfullyWithCorrectArguments() {
    Set<Room> rooms = new HashSet<>();
    for (int i = 1; i <= 3; i++) {
      rooms.add(Room.NewBuilder().withRoomNumber(i).build());
    }

    ConcurrentHashMap<BookingRoom, User> bookings = new ConcurrentHashMap<>();

    Hotel hotel =
        Hotel
            .NewBuilder()
            .withRooms(rooms)
            .withBookings(bookings)
            .build();

    assertNotNull(hotel);
    assertEquals(rooms, hotel.getRooms());
    assertEquals(bookings, hotel.getBookings());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullRooms() {
    Hotel.NewBuilder().withRooms(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWithEmptyRooms() {
    Hotel.NewBuilder().withRooms(new HashSet<>()).build();
  }

}
