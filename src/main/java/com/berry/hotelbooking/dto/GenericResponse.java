package com.berry.hotelbooking.dto;

import java.util.List;
import java.util.Objects;

public class GenericResponse<T> {

  private final boolean success;
  private final List<HotelError> errors;
  private final T data;

  public GenericResponse(Builder<T> builder) {
    this.success = builder.success;
    this.errors = builder.errors;
    this.data = builder.data;
  }

  public boolean isSuccess() {
    return success;
  }

  public List<HotelError> getErrors() {
    return errors;
  }

  public T getData() {
    return data;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GenericResponse<?> that = (GenericResponse<?>) o;
    return success == that.success && Objects.equals(errors, that.errors) && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, errors, data);
  }

  @Override
  public String toString() {
    return "GenericResponse{" +
        "success=" + success +
        ", errors=" + errors +
        ", data=" + data +
        '}';
  }

  public static final class Builder<T> {
    private boolean success;
    private List<HotelError> errors;
    private T data;

    public Builder setSuccess(boolean success) {
      this.success = success;
      return this;
    }

    public Builder setErrors(List<HotelError> errors) {
      this.errors = errors;
      return this;
    }

    public Builder setData(T data) {
      this.data = data;
      return this;
    }

    public GenericResponse build() {
      return new GenericResponse(this);
    }
  }

}
