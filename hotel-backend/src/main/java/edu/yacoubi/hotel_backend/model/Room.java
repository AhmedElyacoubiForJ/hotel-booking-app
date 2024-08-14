package edu.yacoubi.hotel_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room", fetch = LAZY, cascade = ALL)
    private Set<BookedRoom> bookings;

    public Room() {
        this.bookings = new HashSet<>();
    }
    public void addBooking(BookedRoom booking){
        if (bookings == null){
            bookings = new HashSet<>();
        }
        this.bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        booking.setBookingConfirmationCode(RandomStringUtils.randomNumeric(10));
    }

    public void removeBooking(BookedRoom booking){
        this.bookings.remove(booking);
        booking.setRoom(null);
        isBooked = false;
    }
}
