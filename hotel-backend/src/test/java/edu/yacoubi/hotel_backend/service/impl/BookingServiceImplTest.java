package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.exception.BookingNotFoundException;
import edu.yacoubi.hotel_backend.exception.InvalidBookingRequestException;
import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.BookingRepository;
import edu.yacoubi.hotel_backend.service.IRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private IRoomService roomService;

    @InjectMocks
    private BookingServiceImpl underTest;

    private Room room;
    private BookedRoom booking;

    @BeforeEach
    public void setUp() {
        room = new Room();
        booking = new BookedRoom();
        booking.setCheckInDate(LocalDate.of(2022, 1, 1));
        booking.setCheckOutDate(LocalDate.of(2022, 1, 3));
        room.setBookings(Collections.singleton(booking));
    }

    @Test
    public void getAllBookings_shouldReturnAllBookings() {
        // Given
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        // When
        List<BookedRoom> result = underTest.getAllBookings();

        // Then
        assertEquals(List.of(booking), result);
    }

    @Test
    public void getAllBookingsByRoomId_shouldReturnBookingsForGivenRoomId() {
        // Given
        Long roomId = 1L;
        when(bookingRepository.findAllByRoomId(roomId)).thenReturn(Optional.of(List.of(booking)));

        // When
        List<BookedRoom> result = underTest.getAllBookingsByRoomId(roomId);

        // Then
        assertEquals(List.of(booking), result);
    }

    @Test
    public void getAllBookingsByRoomId_shouldThrowBookingNotFoundException_whenNoBookingsFoundForGivenRoomId() {
        // Given
        Long roomId = 1L;
        when(bookingRepository.findAllByRoomId(roomId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookingNotFoundException.class, () -> underTest.getAllBookingsByRoomId(roomId));
    }

    // TODO: more tests but later
}