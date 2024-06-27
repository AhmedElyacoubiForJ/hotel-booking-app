package edu.yacoubi.hotel_backend.service;

import edu.yacoubi.hotel_backend.model.Booking;

import java.util.List;

public interface IBookingService {
    List<Booking> getAllBookingsByRoomId(Long roomId);
}
