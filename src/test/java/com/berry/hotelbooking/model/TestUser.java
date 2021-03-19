package com.berry.hotelbooking.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUser {

  private static final String DEFAULT_USER_NAME = "John Smith";

  @Test
  public void createUserSuccessfullyWithCorrectArguments() {
    User user = User.NewBuilder().withName(DEFAULT_USER_NAME).build();

    assertNotNull(user);
    assertEquals(DEFAULT_USER_NAME, user.getName());
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithNullName() {
    User.NewBuilder().withName(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWithEmptyName() {
    User.NewBuilder().withName("").build();
  }

}
