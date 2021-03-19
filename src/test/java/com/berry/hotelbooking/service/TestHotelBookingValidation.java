package com.berry.hotelbooking.service;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.model.*;
import com.berry.hotelbooking.utils.GenericResponseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class TestHotelBookingValidation {

  private static final Logger DEFAULT_LOGGER = Logger.getLogger(TestHotelBookingValidation.class.getName());
  private final int DEFAULT_NUMBER_OF_ROOMS = 10;
  private final int DEFAULT_BOOKING_ROOM_NUMBER = 5;
  private final String DEFAULT_USER_NAME = "John Smith";
  private final LocalDate DEFAULT_BOOKING_DATE = LocalDate.now().plusDays(10);

  private Set<Room> rooms = new HashSet<>();
  private Hotel hotel;

  @Before
  public void setUp() {
    initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
  }

  private void initHotelBookingServiceWithNumberOfRooms(int numberOfRooms) {
    rooms.clear();

    for (int i = 1; i <= numberOfRooms; i++) {
      rooms.add(Room.NewBuilder().withRoomNumber(i).build());
    }

    hotel = Hotel.NewBuilder().withRooms(rooms).build();
  }

  @After
  public void tearDown() {
    rooms = null;
    hotel = null;
  }

  @Test
  public void shouldShowSuccessfulValidationWithValidArgument_validateBookingINfo() {
    Booking booking = initValidBooking(DEFAULT_USER_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);
    GenericResponse actualResponse = HotelBookingValidation.validateBookingInfo(DEFAULT_LOGGER, hotel, booking);

    assertNull(actualResponse);
  }

  private Booking initValidBooking(String userName, int roomNumber, LocalDate bookingDate) {
    return Booking
        .NewBuilder()
        .withUser(
            User.NewBuilder().withName(userName).build()
        )
        .withBookingRoom(
            BookingRoom
                .NewBuilder()
                .withRoom(
                    Room.NewBuilder().withRoomNumber(roomNumber).build()
                )
                .withBookingDate(bookingDate)
                .build()
        )
        .build();
  }

  @Test
  public void shouldNotThrowExceptionWithNullArguments_validateBookingInfo() {
    Exception ex = null;

    try {
      HotelBookingValidation.validateBookingInfo(null, null, null);
    } catch (Exception thrownException) {
      ex = thrownException;
    }

    assertNull(ex);
  }

  @Test
  public void shouldShowFailValidationWithNullHotelArgument_validateBookingInfo() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Invalid hotel.");
    GenericResponse actualResponse = HotelBookingValidation.validateBookingInfo(DEFAULT_LOGGER, null, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowFailValidationWithEmptyRoomOfHotelArgument_validateBookingInfo() {
    Hotel testableHotel = Hotel.NewBuilder().build();

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Invalid hotel.");
    GenericResponse actualResponse = HotelBookingValidation.validateBookingInfo(DEFAULT_LOGGER, testableHotel, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowFailValidationWithNullBookingArgument_validateBookingInfo() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Booking info is required.");
    GenericResponse actualResponse = HotelBookingValidation.validateBookingInfo(DEFAULT_LOGGER, hotel, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowSuccessfulValidationWithValidArgument_validateUserInfo() {
    User user = User.NewBuilder().withName(DEFAULT_USER_NAME).build();
    GenericResponse actualResponse = HotelBookingValidation.validateUserInfo(DEFAULT_LOGGER, user);

    assertNull(actualResponse);
  }

  @Test
  public void shouldNotThrowExceptionWithNullArguments_validateUserInfo() {
    Exception ex = null;

    try {
      HotelBookingValidation.validateUserInfo(null, null);
    } catch (Exception thrownException) {
      ex = thrownException;
    }

    assertNull(ex);
  }

  @Test
  public void shouldShowFailValidationWithNullUserArgument_validateUserInfo() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid user.");
    GenericResponse actualResponse = HotelBookingValidation.validateUserInfo(DEFAULT_LOGGER, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowSuccessfulValidationWithValidArgument_validateRoomInfo() {
    Room testableRoom = Room.NewBuilder().withRoomNumber(DEFAULT_BOOKING_ROOM_NUMBER).build();
    GenericResponse actualResponse = HotelBookingValidation.validateRoomInfo(DEFAULT_LOGGER, rooms, testableRoom);

    assertNull(actualResponse);
  }

  @Test
  public void shouldNotThrowExceptionWithNullArguments_validateRoomInfo() {
    Exception ex = null;

    try {
      HotelBookingValidation.validateRoomInfo(null, null, null);
    } catch (Exception thrownException) {
      ex = thrownException;
    }

    assertNull(ex);
  }

  @Test
  public void shouldShowFailValidationWithNullExistedRoomsArgument_validateRoomInfo() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Invalid existed rooms.");
    GenericResponse actualResponse = HotelBookingValidation.validateRoomInfo(DEFAULT_LOGGER, null, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowFailValidationWithNullRoomArgument_validateRoomInfo() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid room.");
    GenericResponse actualResponse = HotelBookingValidation.validateRoomInfo(DEFAULT_LOGGER, rooms, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowFailValidationWithInvalidRoomNumber_validateRoomInfo() {
    Room testableRoom = Room.NewBuilder().withRoomNumber(Integer.MAX_VALUE).build();

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid room.");
    GenericResponse actualResponse = HotelBookingValidation.validateRoomInfo(DEFAULT_LOGGER, rooms, testableRoom);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowSuccessfulValidationWithValidArgument_validateBookingDate() {
    GenericResponse actualResponse = HotelBookingValidation.validateBookingDate(DEFAULT_LOGGER, DEFAULT_BOOKING_DATE);

    assertNull(actualResponse);
  }

  @Test
  public void shouldNotThrowExceptionWithNullArguments_validateBookingDate() {
    Exception ex = null;

    try {
      HotelBookingValidation.validateBookingDate(null, null);
    } catch (Exception thrownException) {
      ex = thrownException;
    }

    assertNull(ex);
  }

  @Test
  public void shouldShowFailValidationWithNullBookingDate_validateBookingDate() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid booking date.");
    GenericResponse actualResponse = HotelBookingValidation.validateBookingDate(DEFAULT_LOGGER, null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowFailValidationWithInvalidBookingDate_validateBookingDate() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid booking date.");
    GenericResponse actualResponse = HotelBookingValidation.validateBookingDate(DEFAULT_LOGGER, LocalDate.now().minusDays(30));

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void shouldShowSuccessfulValidationWithNoEmptyExistedBookingsList_searchForExistedBooking() {
    Booking existedBooking = initValidBooking("Anonymous", DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE.plusDays(3));
    ConcurrentHashMap<BookingRoom, User> existedBookings = new ConcurrentHashMap<>();
    existedBookings.put(existedBooking.getBookingRoom(), existedBooking.getUser());

    Booking testableBooking = initValidBooking(DEFAULT_USER_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);

    GenericResponse actualResponse =
        HotelBookingValidation.searchForExistedBooking(DEFAULT_LOGGER, existedBookings, testableBooking);
    assertNull(actualResponse);
  }

  @Test
  public void shouldNotThrowExceptionWithNullArguments_searchForExistedBooking() {
    Exception ex = null;

    try {
      HotelBookingValidation.searchForExistedBooking(null, null, null);
    } catch (Exception thrownException) {
      ex = thrownException;
    }

    assertNull(ex);
  }

  @Test
  public void shouldShowSuccessfulValidationWithNoExistedBookingsList_searchForExistedBooking() {
    Booking testableBooking = initValidBooking(DEFAULT_USER_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);

    GenericResponse nullExistedBookingsResponse =
        HotelBookingValidation.searchForExistedBooking(DEFAULT_LOGGER, null, testableBooking);
    assertNull(nullExistedBookingsResponse);

    GenericResponse emptyExistedBookingsResponse =
        HotelBookingValidation.searchForExistedBooking(DEFAULT_LOGGER, new ConcurrentHashMap<>(), testableBooking);
    assertNull(emptyExistedBookingsResponse);
  }

  @Test
  public void shouldShowFailValidationWithExistedBooking_searchForExistedBooking() {
    Booking testableBooking = initValidBooking(DEFAULT_USER_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);

    ConcurrentHashMap<BookingRoom, User> existedBookings = new ConcurrentHashMap<>();
    existedBookings.put(testableBooking.getBookingRoom(), testableBooking.getUser());

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("There's no available room at this time.");
    GenericResponse actualResponse =
        HotelBookingValidation.searchForExistedBooking(DEFAULT_LOGGER, existedBookings, testableBooking);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

}
