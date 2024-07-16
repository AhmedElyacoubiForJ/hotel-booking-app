package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.exception.BookingNotFoundException;
import edu.yacoubi.hotel_backend.exception.InvalidBookingRequestException;
import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.BookingRepository;
import edu.yacoubi.hotel_backend.service.IBookingService;
import edu.yacoubi.hotel_backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookings() {
        log.debug("Getting all bookings");
        return Collections.unmodifiableList(bookingRepository.findAll());
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        log.debug("Getting all bookings for room ID: {}", roomId);
        return bookingRepository.findAllByRoomId(roomId)
                .orElseThrow(() -> new BookingNotFoundException("No bookings found for the given room ID"));
    }

    @Override
    public BookedRoom getBookingByConfirmationCode(String confirmationCode) {
        log.debug("Getting booking for confirmation code: {}", confirmationCode);
        return bookingRepository.findBookedRoomByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new BookingNotFoundException("No booking found for the given confirmation code"));
    }

    @Override
    public String makeBooking(Long roomId, BookedRoom bookingRequest) {
        log.debug("Making booking for room ID: {}, booking request: {}", roomId, bookingRequest);
        validateBookingRequest(roomId, bookingRequest);

        Room room = roomService.getRoomById(roomId);
        List<BookedRoom> existingBookings = room.getBookings();
        boolean isRoomAvailable = isRoomAvailableForBooking(
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                existingBookings
        );

        if (!isRoomAvailable) {
            throw new InvalidBookingRequestException("Sorry, this room is not available for the requested dates");
        }

        room.addBooking(bookingRequest);
        BookedRoom savedBooking = bookingRepository.save(bookingRequest);

        log.info("Booking made successfully: {}", savedBooking);
        return savedBooking.getBookingConfirmationCode();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        log.debug("Cancelling booking for ID: {}", bookingId);
        BookedRoom booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("No booking found for the given ID"));

        bookingRepository.delete(booking);
        log.info("Booking cancelled successfully: {}", booking);
    }

    private void validateBookingRequest(Long roomId, BookedRoom bookingRequest) {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }

        if (bookingRequest == null) {
            throw new IllegalArgumentException("Booking request cannot be null");
        }

        if (!isBookingDateValid(bookingRequest)) {
            throw new InvalidBookingRequestException("Check-in date must be before check-out date");
        }
    }

    private boolean isBookingDateValid(BookedRoom bookingRequest) {
        return bookingRequest.getCheckOutDate().isAfter(bookingRequest.getCheckInDate());
    }

    private boolean isRoomAvailableForBooking(LocalDate requestedCheckInDate,
                                              LocalDate requestedCheckOutDate,
                                              List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking -> {
                    LocalDate existingBookingCheckInDate = existingBooking.getCheckInDate();
                    LocalDate existingBookingCheckOutDate = existingBooking.getCheckOutDate();

                    boolean isRequestedCheckInDateBetweenExistingBookings =
                            requestedCheckInDate.isBefore(existingBookingCheckOutDate) ||
                                    requestedCheckInDate.isEqual(existingBookingCheckOutDate);
                    boolean isRequestedCheckOutDateBetweenExistingBookings =
                            requestedCheckOutDate.isAfter(existingBookingCheckInDate) ||
                                    requestedCheckOutDate.isEqual(existingBookingCheckInDate);

                    return isRequestedCheckInDateBetweenExistingBookings &&
                            isRequestedCheckOutDateBetweenExistingBookings;
                });
    }
}