package edu.yacoubi.hotel_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

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
    private List<BookedRoom> bookedRooms; // historical

    public Room() {
        this.bookedRooms = new ArrayList<>();
    }
    public void addBooking(BookedRoom bookedRoom){
        if (bookedRooms == null){
            bookedRooms = new ArrayList<>();
        }
        bookedRooms.add(bookedRoom);
        bookedRoom.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        bookedRoom.setBookingConfirmationCode(bookingCode);
    }
}
