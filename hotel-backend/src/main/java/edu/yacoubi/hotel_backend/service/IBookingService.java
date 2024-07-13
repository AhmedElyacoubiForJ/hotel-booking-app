package edu.yacoubi.hotel_backend.service;

import edu.yacoubi.hotel_backend.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    BookedRoom getBookingByConfirmationCode(String code);
}
