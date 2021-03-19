package com.berry.hotelbooking;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestHotelBookingApplication {

  @Test
  public void testSomeBehavior() {
    assertEquals("Empty list should have 0", 0, new ArrayList<>().size());
  }

}
