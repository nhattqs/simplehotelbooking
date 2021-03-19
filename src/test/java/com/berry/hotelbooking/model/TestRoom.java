package com.berry.hotelbooking.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRoom {

  private static final int DEFAULT_ROOM_NUMBER = 10;

  @Test
  public void createRoomSuccessfullyWithCorrectArguments() {
    Room room = Room.NewBuilder().withRoomNumber(DEFAULT_ROOM_NUMBER).build();

    assertNotNull(room);
    assertEquals(DEFAULT_ROOM_NUMBER, room.getRoomNumber());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWithInvalidRoomNumber() {
    Room.NewBuilder().withRoomNumber(0).build();
  }

}
