package edu.yacoubi.hotel_backend.controller;

import edu.yacoubi.hotel_backend.dto.BookingResponse;
import edu.yacoubi.hotel_backend.dto.RoomResponse;
import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.service.IBookingService;
import edu.yacoubi.hotel_backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping()
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = getBookingResponses(bookings);
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{code}")
    public ResponseEntity<BookedRoom> getBookingByConfirmationCode(@PathVariable("code") String code) {
        BookedRoom booking = bookingService.getBookingByConfirmationCode(code);
        return ResponseEntity.ok(booking); // Dummy
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<?> makeBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest) {
        String confirmationCode = bookingService.makeBooking(roomId, bookingRequest);
        String messageResponse =
                String.format("Room booked successfully, Your confirmation code is : %s", confirmationCode);

        return ResponseEntity.ok(messageResponse);
    }

    // endpoint cancel booking
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

    private List<BookingResponse> getBookingResponses(List<BookedRoom> bookings) {
        return bookings.stream()
                .map(bookedRoom -> getBookingResponse(bookedRoom))
                .collect(toList());
    }

    private BookingResponse getBookingResponse(BookedRoom bookedRoom) {
        Room theRoom = roomService.getRoomById(bookedRoom.getRoom().getId());
        RoomResponse roomResponse = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());

        BookingResponse bookingResponse = new BookingResponse();

        bookingResponse.setRoom(roomResponse);
        bookingResponse.setBookingId(bookedRoom.getBookingId());
        bookingResponse.setCheckInDate(bookedRoom.getCheckInDate());
        bookingResponse.setCheckOutDate(bookedRoom.getCheckOutDate());
        bookingResponse.setGuestFullName(bookedRoom.getGuestFullName());
        bookingResponse.setGuestEmail(bookedRoom.getGuestEmail());
        bookingResponse.setNumberOfAdults(bookedRoom.getNumberOfAdults());
        bookingResponse.setNumberOfChildren(bookedRoom.getNumberOfChildren());
        bookingResponse.setTotalNumberOfGuest(bookedRoom.getTotalNumberOfGuest());
        bookingResponse.setBookingConfirmationCode(bookedRoom.getBookingConfirmationCode());

        return bookingResponse;
    }

//    @RequestMapping("/{roomId}")
//    public ResponseEntity<List<BookingResponse>> getAllBookingsByRoomId(@PathVariable("roomId") Long roomId) {
//        //List<BookingResponse> bookingResponseList = bookingService.getAllBookingsByRoomId(roomId);
//        return ResponseEntity.ok(List.of());
//    }
}
