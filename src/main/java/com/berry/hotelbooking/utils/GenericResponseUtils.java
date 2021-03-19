package com.berry.hotelbooking.utils;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.dto.HotelError;

import java.util.Collections;

public class GenericResponseUtils {

  private GenericResponseUtils() {
    // Prevent default initialization
  }

  /**
   * This method helps to generate the response in error format with error message.
   * @param errorMessage The error message
   * @return The response in error format
   */
  public static GenericResponse generateFromErrorMessage(String errorMessage) {
    return GenericResponse.NewBuilder()
        .setSuccess(Boolean.FALSE)
        .setErrors(
            Collections.singletonList(
                HotelError.NewBuilder().withMessage(errorMessage).build()
            )
        )
        .build();
  }

  /**
   * This method helps to generate the response in successful format with corresponding data.
   * @param data The expected response data
   * @param <T> The data type
   * @return The response in successful format
   */
  public static <T> GenericResponse<T> generateFromSuccessfulData(T data) {
    return GenericResponse.NewBuilder()
        .setSuccess(Boolean.TRUE)
        .setData(data)
        .build();
  }

}
