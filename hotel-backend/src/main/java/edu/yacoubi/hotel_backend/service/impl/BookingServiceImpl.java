package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.repository.BookingRepository;
import edu.yacoubi.hotel_backend.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {
    private BookingRepository bookingRepository;
    @Override
    public List<BookedRoom> getAllBookings() {
        return Collections.unmodifiableList(emptyList());
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return List.of();
    }

    @Override
    public BookedRoom getBookingByConfirmationCode(String code) {
        // TODO: Implement logic to find booking by confirmation code
        return new BookedRoom();
    }
}
