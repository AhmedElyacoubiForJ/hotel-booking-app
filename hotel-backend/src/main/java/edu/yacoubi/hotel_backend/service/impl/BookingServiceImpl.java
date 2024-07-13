package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.service.IBookingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class BookingServiceImpl implements IBookingService {
    @Override
    public List<BookedRoom> getAllBookings() {
        return Collections.unmodifiableList(emptyList());
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return List.of();
    }
}
