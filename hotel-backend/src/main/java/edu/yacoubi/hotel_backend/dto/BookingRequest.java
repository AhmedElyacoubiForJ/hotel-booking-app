package edu.yacoubi.hotel_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalNumberOfGuest;

    public BookingRequest(String guestFullName,
                          String guestEmail,
                          LocalDate checkInDate,
                          LocalDate checkOutDate,
                          int numberOfAdults,
                          int numberOfChildren) {
        this.guestFullName = guestFullName;
        this.guestEmail = guestEmail;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.totalNumberOfGuest = calculateTotalNumberOfGuest();
    }

    private int calculateTotalNumberOfGuest() {
        return this.numberOfAdults + this.numberOfChildren;
    }
}
