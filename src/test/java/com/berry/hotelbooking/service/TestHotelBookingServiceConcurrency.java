package com.berry.hotelbooking.service;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.model.*;
import com.berry.hotelbooking.utils.GenericResponseUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHotelBookingServiceConcurrency {

  private final int DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS = 500;
  private final int DEFAULT_ACCEPTABLE_LATENCY_FOR_CONCURRENT_CALL = 500;

  private final int DEFAULT_NUMBER_OF_ROOMS = 1000;
  private final int DEFAULT_BOOKING_ROOM_NUMBER = 5;
  private final String DEFAULT_GUEST_NAME = "John Smith";
  private final LocalDate DEFAULT_BOOKING_DATE = LocalDate.now().plusDays(10);

  private static final int NUMBER_CORES = Runtime.getRuntime().availableProcessors();

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_CONCURRENT_CALL)
  public void shouldReserveRoomWithBookingArgument_ConcurrentCall() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_CORES);
    List<Callable<GenericResponse>> tasks = new ArrayList<>();
    List<GenericResponse> results = new ArrayList<>();

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    HotelBookingService service = new HotelBookingService(hotel);

    Booking booking = initBookingWithValues(DEFAULT_GUEST_NAME, DEFAULT_BOOKING_ROOM_NUMBER, DEFAULT_BOOKING_DATE);
    for (int i = 0; i < DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS; i++) {
      tasks.add(() -> service.reserveRoom(booking));
    }

    List<Future<GenericResponse>> futureTasks = executorService.invokeAll(tasks);
    executorService.shutdown();

    for (Future<GenericResponse> futureTask : futureTasks) {
      results.add(futureTask.get());
    }

    GenericResponse successfulResponse = GenericResponseUtils.generateFromSuccessfulData(booking);
    GenericResponse failResponse = GenericResponseUtils.generateFromErrorMessage("Oops. Please try again.");

    assertEquals(DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS, results.size());
    assertTrue(results.stream().filter(GenericResponse::isSuccess).findAny().isPresent());
    assertEquals(successfulResponse, results.stream().filter(GenericResponse::isSuccess).findAny().get());
    assertEquals(results.size() - 1, results.stream().filter(item -> !item.isSuccess()).collect(Collectors.toList()).size());
    assertEquals(failResponse, results.stream().filter(item -> !item.isSuccess()).findAny().get());
  }

  private Hotel initHotelBookingServiceWithNumberOfRooms(int numberOfRooms) {
    Set<Room> rooms = new HashSet<>();

    for (int i = 1; i <= numberOfRooms; i++) {
      rooms.add(Room.NewBuilder().withRoomNumber(i).build());
    }

    return Hotel.NewBuilder().withRooms(rooms).build();
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

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_CONCURRENT_CALL)
  public void shouldFindAvailableRoomsWithBookingDate_ConcurrentCall() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_CORES);
    List<Callable<GenericResponse>> tasks = new ArrayList<>();
    List<GenericResponse> results = new ArrayList<>();

    Booking firstBooking = initBookingWithValues(DEFAULT_GUEST_NAME, 2, DEFAULT_BOOKING_DATE);

    Hotel hotel = initHotelBookingServiceWithNumberOfRooms(DEFAULT_NUMBER_OF_ROOMS);
    hotel.getBookings().put(firstBooking.getBookingRoom(), firstBooking.getUser());

    HotelBookingService service = new HotelBookingService(hotel);

    Set<Room> availableRooms =
        hotel.getRooms().stream()
            .filter(item -> item.getRoomNumber() != firstBooking.getBookingRoom().getRoom().getRoomNumber())
            .collect(Collectors.toSet());
    GenericResponse expectedResponse = GenericResponseUtils.generateFromSuccessfulData(availableRooms);

    for (int i = 0; i < DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS; i++) {
      tasks.add(() -> service.findAvailableRooms(DEFAULT_BOOKING_DATE));
    }

    List<Future<GenericResponse>> futureTasks = executorService.invokeAll(tasks);
    executorService.shutdown();

    for (Future<GenericResponse> futureTask : futureTasks) {
      results.add(futureTask.get());
    }

    assertEquals(DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS, results.size());
    assertEquals(expectedResponse, results.stream().findAny().get());
    assertEquals(results.size(), results.stream().filter(GenericResponse::isSuccess).collect(Collectors.toList()).size());
  }

  @Test(timeout = DEFAULT_ACCEPTABLE_LATENCY_FOR_CONCURRENT_CALL)
  public void shouldFindExistedBookingsWithUser_ConcurrentCall() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_CORES);
    List<Callable<GenericResponse>> tasks = new ArrayList<>();
    List<GenericResponse> results = new ArrayList<>();

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

    for (int i = 0; i < DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS; i++) {
      tasks.add(() -> service.findExistedBookings(user));
    }

    List<Future<GenericResponse>> futureTasks = executorService.invokeAll(tasks);
    executorService.shutdown();

    for (Future<GenericResponse> futureTask : futureTasks) {
      results.add(futureTask.get());
    }

    assertEquals(DEFAULT_NUMBER_OF_CONCURRENT_REQUESTS, results.size());
    assertEquals(expectedResponse, results.stream().findAny().get());
    assertEquals(results.size(), results.stream().filter(GenericResponse::isSuccess).collect(Collectors.toList()).size());
  }

}
