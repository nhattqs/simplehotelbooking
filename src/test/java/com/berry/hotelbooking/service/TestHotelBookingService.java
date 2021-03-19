package com.berry.hotelbooking.service;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.model.*;
import com.berry.hotelbooking.utils.GenericResponseUtils;
import org.junit.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class TestHotelBookingService {

  private final int DEFAULT_NUMBER_OF_ROOMS = 1000;
  private final int DEFAULT_BOOKING_ROOM_NUMBER = 5;
  private final String DEFAULT_GUEST_NAME = "John Smith";
  private final LocalDate DEFAULT_BOOKING_DATE = LocalDate.now().plusDays(10);

  private final int DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL = 100;

  private HotelBookingService hotelBookingService;

  @Before
  public void setUp() {
    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotelBookingService = new HotelBookingService(hotel);
  }

  private Hotel initHotelBookingServiceWithNumberOfRooms(int numberOfRooms) {
    Set<Room> rooms = new HashSet<>();

    for (int i = 1; i <= numberOfRooms; i++) {
      rooms.add(Room.NewBuilder().withRoomNumber(i).build());
    }

    return Hotel.NewBuilder().withRooms(rooms).build();
  }

  @After
  public void tearDown() {
    hotelBookingService = null;
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldReserveRoomWithBookingArgument_SerialCall() {
    Booking booking = initBookingWithValues(DEFAULT_GUEST_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);

    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(booking);
    GenericResponse<Booking> actualResponse = hotelBookingService.reserveRoom(booking);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  private Booking initBookingWithValues(String guestName, int roomNumber, LocalDate bookingDate) {
    return Booking
        .NewBuilder()
        .withUser(
            User.NewBuilder().withName(guestName).build()
        )
        .withBookingRoom(
            BookingRoom
                .NewBuilder().withRoom(
                Room.NewBuilder().withRoomNumber(roomNumber).build()
            )
                .withBookingDate(bookingDate)
                .build()
        )
        .build();
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldNotReserveRoomWithInvalidBooking() {
    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Booking info is required.");
    GenericResponse<Booking> actualResponse = hotelBookingService.reserveRoom(null);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldNotReserveRoomWithExistedBooking() {
    Booking booking = initBookingWithValues(DEFAULT_GUEST_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);
    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotel.getBookings().put(booking.getBookingRoom(), booking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("There's no available room at this time.");
    GenericResponse<Booking> actualResponse = service.reserveRoom(booking);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindAvailableRoomsWithBookingDate_SerialCall() {
    Booking firstBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 2, DEFAULT_BOOKING_DATE);

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotel.getBookings().put(firstBooking.getBookingRoom(), firstBooking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    Set<Room> availableRooms =
        hotel.getRooms().stream()
            .filter(item -> item.getRoomNumber() != firstBooking.getBookingRoom().getRoom().getRoomNumber())
            .collect(Collectors.toSet());
    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(availableRooms);
    GenericResponse actualResponse = service.findAvailableRooms(DEFAULT_BOOKING_DATE);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindAvailableRoomsWithNoRoomWithBookingDate() {
    Booking firstBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 1, DEFAULT_BOOKING_DATE);
    Booking secondBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 2, DEFAULT_BOOKING_DATE);

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(2);
    hotel.getBookings().put(firstBooking.getBookingRoom(), firstBooking.getUser());
    hotel.getBookings().put(secondBooking.getBookingRoom(), secondBooking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(new HashSet<>());
    GenericResponse actualResponse = service.findAvailableRooms(DEFAULT_BOOKING_DATE);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindAvailableRoomsWithInvalidBookingDate() {
    LocalDate seekingDate = DEFAULT_BOOKING_DATE.minusYears(1);

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid booking date.");
    GenericResponse actualResponse = hotelBookingService.findAvailableRooms(seekingDate);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindExistedBookingsWithUser_SerialCall() {
    User user = User.NewBuilder().withName(DEFAULT_GUEST_NAME).build();
    Booking firstBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 2, DEFAULT_BOOKING_DATE);
    Booking secondBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 5, DEFAULT_BOOKING_DATE);

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotel.getBookings().put(firstBooking.getBookingRoom(), firstBooking.getUser());
    hotel.getBookings().put(secondBooking.getBookingRoom(), secondBooking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    Set<BookingRoom> availableBooking =
        Stream.of(firstBooking.getBookingRoom(), secondBooking.getBookingRoom()).collect(Collectors.toSet());
    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(availableBooking);

    GenericResponse actualResponse = service.findExistedBookings(user);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindExistedBookingWithNoBookingWithNewUser() {
    User user = User.NewBuilder().withName("Anonymous").build();
    Booking firstBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 1, DEFAULT_BOOKING_DATE);
    Booking secondBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 2, DEFAULT_BOOKING_DATE);

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotel.getBookings().put(firstBooking.getBookingRoom(), firstBooking.getUser());
    hotel.getBookings().put(secondBooking.getBookingRoom(), secondBooking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(new HashSet<>());
    GenericResponse actualResponse = service.findExistedBookings(user);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_SERIAL_CALL)
  public void shouldFindExistedBookingsWithInvalidUser() {
    User user = User.NewBuilder().build();

    GenericResponse expectedResponse = GenericResponseUtils.generateFromErrorMessage("Please give a valid user.");
    GenericResponse actualResponse = hotelBookingService.findExistedBookings(user);

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
  }

}
