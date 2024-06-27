package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.model.Booking;
import edu.yacoubi.hotel_backend.service.IBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {
    @Override
    public List<Booking> getAllBookingsByRoomId(Long roomId) {
        return List.of();
    }
}
