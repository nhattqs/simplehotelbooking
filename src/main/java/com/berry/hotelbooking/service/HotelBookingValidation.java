package com.berry.hotelbooking.service;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.model.*;
import com.berry.hotelbooking.utils.GenericResponseUtils;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class HotelBookingValidation {

  private HotelBookingValidation() {
    // Prevent init default constructor.
  }

  /**
   * This method validates whether the booking input is valid or not.
   * Then print out helpful message to identify the problem.
   * @param logger A Logger instance of an invoker which needs the validation
   * @param booking The booking info
   * @return A generic response with fail status and a meaningful message
   */
  public static GenericResponse validateBookingInfo(Logger logger, Hotel hotel, Booking booking) {
    if (logger == null) {
      logger = Logger.getLogger(HotelBookingValidation.class.getName());
    }

    if (hotel == null || hotel.getRooms().size() <= 0) {
      logger.severe("Invalid hotel.");
      return GenericResponseUtils.generateFromErrorMessage("Invalid hotel.");
    }

    if (booking == null) {
      logger.severe("Booking info is required.");
      return GenericResponseUtils.generateFromErrorMessage("Booking info is required.");
    }

    GenericResponse genericResponse = validateUserInfo(logger, booking.getUser());
    if (genericResponse != null) {
      return genericResponse;
    }

    genericResponse = validateRoomInfo(logger, hotel.getRooms(), booking.getBookingRoom().getRoom());
    if (genericResponse != null) {
      return genericResponse;
    }

    genericResponse = validateBookingDate(logger, booking.getBookingRoom().getBookingDate());
    if (genericResponse != null) {
      return genericResponse;
    }

    return searchForExistedBooking(logger, hotel.getBookings(), booking);
  }

  /**
   * This method validates whether the user input is valid or not.
   * Then print out helpful message to identify the problem.
   * @param logger A Logger instance of an invoker which needs the validation
   * @param user The user info
   * @return A generic response with fail status and a meaningful message
   */
  public static GenericResponse validateUserInfo(Logger logger, User user) {
    if (logger == null) {
      logger = Logger.getLogger(HotelBookingValidation.class.getName());
    }

    if (user == null
        || (user.getName() == null || "".equals(user.getName()))) {
      logger.severe("Please give a valid user.");
      return GenericResponseUtils.generateFromErrorMessage("Please give a valid user.");
    }

    return null;
  }

  /**
   * This method validates whether the room input is valid or not.
   * Then print out helpful message to identify the problem.
   * @param logger A Logger instance of an invoker which needs the validation
   * @param room The room info
   * @return A generic response with fail status and a meaningful message
   */
  public static GenericResponse validateRoomInfo(Logger logger, Set<Room> existedRooms, Room room) {
    if (logger == null) {
      logger = Logger.getLogger(HotelBookingValidation.class.getName());
    }

    if (existedRooms == null || existedRooms.size() == 0) {
      logger.severe("Invalid existed rooms.");
      return GenericResponseUtils.generateFromErrorMessage("Invalid existed rooms.");
    }

    if (room == null || !existedRooms.contains(room)) {
      logger.severe("Please give a valid room.");
      return GenericResponseUtils.generateFromErrorMessage("Please give a valid room.");
    }

    return null;
  }

  /**
   * This method validates whether the booking date input is valid or not.
   * Then print out helpful message to identify the problem.
   * @param logger A Logger instance of an invoker which needs the validation
   * @param bookingDate The date of booking
   * @return A generic response with fail status and a meaningful message
   */
  public static GenericResponse validateBookingDate(Logger logger, LocalDate bookingDate) {
    if (logger == null) {
      logger = Logger.getLogger(HotelBookingValidation.class.getName());
    }

    if (bookingDate == null || bookingDate.compareTo(LocalDate.now()) < 0) {
      logger.severe("Please give a valid booking date.");
      return GenericResponseUtils.generateFromErrorMessage("Please give a valid booking date.");
    }

    return null;
  }

  public static GenericResponse searchForExistedBooking(
      Logger logger, ConcurrentHashMap<BookingRoom, User> bookings, Booking booking
  ) {
    if (logger == null) {
      logger = Logger.getLogger(HotelBookingValidation.class.getName());
    }

    if (bookings == null || bookings.size() == 0) {
      return null;
    }

    boolean existedBooking = bookings.containsKey(booking.getBookingRoom());
    if (existedBooking) {
      logger.info("There's no available room at this time.");
      return GenericResponseUtils.generateFromErrorMessage("There's no available room at this time.");
    }

    return null;
  }

}
