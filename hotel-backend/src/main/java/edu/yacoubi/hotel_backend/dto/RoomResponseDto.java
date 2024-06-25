package edu.yacoubi.hotel_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
public class RoomResponseDto {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    List<BookingResponseDto> bookings;

    public RoomResponseDto(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponseDto(Long id, String roomType, BigDecimal roomPrice, boolean isBooked,
                           byte[] photoAsBytes, List<BookingResponseDto> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoAsBytes != null ? Base64.encodeBase64String(photoAsBytes) : null;
        this.bookings = bookings;
    }
}
