package com.berry.hotelbooking.service;

import com.berry.hotelbooking.model.*;
import com.berry.hotelbooking.utils.GenericResponseUtils;
import com.berry.hotelbooking.dto.GenericResponse;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HotelBookingService {

  private static Logger logger = Logger.getLogger(HotelBookingService.class.getName());
  private final Hotel hotel;

  public HotelBookingService(Hotel hotel) {
    this.hotel = hotel;
  }

  /**
   * This method checks for a valid booking info, then makes a reservation.
   * @param booking The booking info
   * @return A generic response with fail status and a meaningful message.
   * Or a successful status with a corresponding data
   */
  public GenericResponse<Booking> reserveRoom(Booking booking) {
    GenericResponse genericResponse = HotelBookingValidation.validateBookingInfo(logger, hotel, booking);
    if (genericResponse != null) {
      return genericResponse;
    }

    logger.info(booking.toString());

    if (hotel.getBookings().putIfAbsent(booking.getBookingRoom(), booking.getUser()) == null) {
      return GenericResponseUtils.generateFromSuccessfulData(booking);
    }

    logger.info("Oops. Please try again.");
    return GenericResponseUtils.generateFromErrorMessage("Oops. Please try again.");
  }

  /**
   * This method will retrieve all available rooms in a giving date.
   * @param bookingDate The giving date
   * @return A generic response with fail status and a meaningful message.
   * Or a successful status with a corresponding data
   */
  public GenericResponse<Set<Room>> findAvailableRooms(LocalDate bookingDate) {
    logger.info(String.format("Booking date: %s", bookingDate));

    GenericResponse genericResponse = HotelBookingValidation.validateBookingDate(logger, bookingDate);
    if (genericResponse != null) {
      return genericResponse;
    }

    Set<Room> availableRooms =
        hotel.getRooms()
            .stream()
            .filter(
                room -> !hotel.getBookings().containsKey(
                    BookingRoom.NewBuilder().withRoom(room).withBookingDate(bookingDate).build()
                )
            )
            .collect(Collectors.toSet());
    return GenericResponseUtils.generateFromSuccessfulData(availableRooms);
  }

  /**
   * This method will return all existed booking of a user.
   * @param user The user
   * @return A generic response with fail status and a meaningful message.
   * Or a successful status with a corresponding data
   */
  public GenericResponse<Set<BookingRoom>> findExistedBookings(User user) {
    logger.info(user.toString());

    GenericResponse genericResponse = HotelBookingValidation.validateUserInfo(logger, user);
    if (genericResponse != null) {
      return genericResponse;
    }

    Set<BookingRoom> existedBookings = hotel.getBookings().entrySet()
        .stream()
        .filter(booking -> booking.getValue().equals(user))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
    return GenericResponseUtils.generateFromSuccessfulData(existedBookings);
  }

}
