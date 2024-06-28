package edu.yacoubi.hotel_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    List<BookingResponse> bookings;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked,
                        byte[] photoAsBytes, List<BookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoAsBytes != null ? new String(new Base64().encode(photoAsBytes)) : null;
        this.bookings = bookings;
    }
}
