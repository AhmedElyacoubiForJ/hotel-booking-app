package edu.yacoubi.hotel_backend.controller;

import edu.yacoubi.hotel_backend.dto.BookingResponse;
import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.service.IBookingService;
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

    private List<BookingResponse> getBookingResponses(List<BookedRoom> bookings) {
        return bookings.stream()
                .map(bookedRoom -> getBookingResponse(bookedRoom)).collect(toList());
    }

    private BookingResponse getBookingResponse(BookedRoom bookedRoom) {
        // TODO: Implement logic to map BookedRoom to BookingResponse
        return new BookingResponse();
    }

//    @RequestMapping("/{roomId}")
//    public ResponseEntity<List<BookingResponse>> getAllBookingsByRoomId(@PathVariable("roomId") Long roomId) {
//        //List<BookingResponse> bookingResponseList = bookingService.getAllBookingsByRoomId(roomId);
//        return ResponseEntity.ok(List.of());
//    }
}
