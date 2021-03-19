# Simple Hotel Booking Manager
This application provides a [HotelBookingService](https://github.com/nhattqs/simplehotelbooking/blob/master/src/main/java/com/berry/hotelbooking/service/HotelBookingService.java) exposed three simple APIs.
Each API needs to satisfy a list of basic validations ([HotelBookingValidation](https://github.com/nhattqs/simplehotelbooking/blob/master/src/main/java/com/berry/hotelbooking/service/HotelBookingValidation.java)) before proceeding the main logic.
* reserveRoom
```
This method checks for a valid booking info, then makes a reservation.
```
* findAvailableRooms
```
This method will retrieve all available rooms in a giving date.
```
* findExistedBookings
```
This method will return all existed booking of a user.
```

## Local Setup
### Dependencies
* Java 8
* Git

### Commands
* Default command to clean and run the test
```sh
./mvnw
```

* Run without default log from maven
```sh
./mvnw -q
```

## Achievement
* Unit tests for all 3 APIs and other related models / utilities
* Cover concurrency with 500 CRs under 300ms for each APIs
