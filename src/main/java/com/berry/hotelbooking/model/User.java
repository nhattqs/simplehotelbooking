package com.berry.hotelbooking.model;

import java.util.Objects;

public class User {

  private String name;

  public User(Builder userBuilder) {
    this.name = userBuilder.name;
  }

  public String getName() {
    return this.name;
  }

  public static Builder NewBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return name.equals(user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        '}';
  }

  public static final class Builder {
    private String name;

    public Builder withName(String name) {
      if (name == null || "".equals(name)) {
        throw new NullPointerException("Name is required");
      }

      this.name = name;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

}
