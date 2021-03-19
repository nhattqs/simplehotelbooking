package com.berry.hotelbooking.utils;

import com.berry.hotelbooking.dto.GenericResponse;
import com.berry.hotelbooking.dto.HotelError;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class TestGenericResponseUtils {

  private static final String DEFAULT_ERROR_MESSAGE = "Default Error Message.";
  private static final Object DEFAULT_OBJECT = new Object();

  @Test
  public void shouldGenerateFromErrorMessageCorrectly() {
    GenericResponse expectedResponse =
        GenericResponse
            .NewBuilder()
            .setSuccess(Boolean.FALSE)
            .setErrors(
                Collections.singletonList(
                    HotelError.NewBuilder().withMessage(DEFAULT_ERROR_MESSAGE).build()
                )
            )
            .build();

    GenericResponse actualResponse = GenericResponseUtils.generateFromErrorMessage(DEFAULT_ERROR_MESSAGE);
    assertEquals(expectedResponse, actualResponse);
    assertFalse(actualResponse.isSuccess());
    assertEquals(expectedResponse.getErrors(), actualResponse.getErrors());
    assertNull(actualResponse.getData());
  }

  @Test
  public void shouldGenerateFromSuccessfulData() {
    GenericResponse expectedResponse =
        GenericResponse
            .NewBuilder()
            .setSuccess(Boolean.TRUE)
            .setData(DEFAULT_OBJECT)
            .build();

    GenericResponse actualResponse = GenericResponseUtils.generateFromSuccessfulData(DEFAULT_OBJECT);
    assertEquals(expectedResponse, actualResponse);
    assertTrue(actualResponse.isSuccess());
    assertNull(actualResponse.getErrors());
    assertEquals(expectedResponse.getData(), actualResponse.getData());
  }

}
