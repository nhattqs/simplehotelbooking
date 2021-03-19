package com.berry.hotelbooking.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestHotelError {

  private static final String DEFAULT_ERROR_MESSAGE = "Error message.";

  @Test
  public void createCustomErrorSuccessfullyWithCorrectArguments() {
    HotelError error = HotelError.NewBuilder().withMessage(DEFAULT_ERROR_MESSAGE).build();

    assertNotNull(error);
    assertEquals(DEFAULT_ERROR_MESSAGE, error.getMessage());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullMessage() {
    HotelError.NewBuilder().withMessage(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithEmptyMessage() {
    HotelError.NewBuilder().withMessage("").build();
  }

}
