package edu.yacoubi.hotel_backend.controller;

import edu.yacoubi.hotel_backend.dto.BookingResponse;
import edu.yacoubi.hotel_backend.dto.RoomResponse;
import edu.yacoubi.hotel_backend.model.Booking;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.service.IBookingService;
import edu.yacoubi.hotel_backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173/")
public class RoomController {

    private final IRoomService roomService;
    private final IBookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") final MultipartFile photo,
            @RequestParam("roomType") final String roomType,
            @RequestParam("roomPrice") final BigDecimal roomPrice
    ) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        return ResponseEntity.ok(new RoomResponse(
                savedRoom.getId(),
                savedRoom.getRoomType(),
                savedRoom.getRoomPrice()
        ));
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
        //return Arrays.asList("Single Room", "Multiple Room", "Double Room");
    }

    // get All Rooms endpoint
    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponseList = getAllRoomResponse(rooms);
        return ResponseEntity.ok(roomResponseList);
    }

    private List<RoomResponse> getAllRoomResponse(List<Room> rooms) throws SQLException {
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room, photoBase64);
                roomResponseList.add(roomResponse);
            }
        }
        return roomResponseList;
    }

    private RoomResponse getRoomResponse(Room room, String photoBase64) {
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setRoomType(room.getRoomType());
        roomResponse.setRoomPrice(room.getRoomPrice());
        roomResponse.setPhoto(photoBase64);

        // getting the history for each room
        List<BookingResponse> bookingResponseList =
                getBookingResponseList(room);

        roomResponse.setBookings(bookingResponseList);

        return roomResponse;
    }

    private List<BookingResponse> getBookingResponseList(Room room) {
        List<Booking> bookings = getAllBookingsByRoomId(room.getId());
        // BookedRoom list to response dto list
        List<BookingResponse> bookingResponseList = bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode())
                ).collect(Collectors.toList());
        return bookingResponseList;
    }

    private List<Booking> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }
}
