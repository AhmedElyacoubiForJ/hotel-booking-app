package edu.yacoubi.hotel_backend.controller;

import edu.yacoubi.hotel_backend.dto.BookingResponse;
import edu.yacoubi.hotel_backend.dto.RoomResponse;
import edu.yacoubi.hotel_backend.model.BookedRoom;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.service.IBookingService;
import edu.yacoubi.hotel_backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173/")
@CacheConfig(cacheNames = "roomPhotos")
@Slf4j
public class RoomController {

    private final IRoomService roomService;
    private final IBookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponseList = getAllRoomResponse(rooms);
        return ResponseEntity.ok(roomResponseList);
    }

    @PostMapping("/add")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") final MultipartFile photo,
                                                   @RequestParam("roomType") final String roomType,
                                                   @RequestParam("roomPrice") final BigDecimal roomPrice) {

        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse roomResponse = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),
                savedRoom.getRoomPrice());

        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @GetMapping(
            value = "/test/photo/{roomId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    //@Cacheable("roomPhotos")
    public ResponseEntity<byte[]> getRoomPhotoByRoomId(@PathVariable("roomId") Long roomId) {
        byte[] photoBytes = roomService.getPhotoByRoomId(roomId);
        return ResponseEntity.ok().body(photoBytes);
    }

    @GetMapping(value = "/test/photo/encoded/{roomId}")
    public ResponseEntity<String> getRoomPhotoEncodedByRoomId(@PathVariable("roomId") Long roomId) {
        byte[] photoBytes = roomService.getPhotoByRoomId(roomId);

        String photoBase64 =  photoBytes != null
                ? new String(new Base64().encode(photoBytes))
                : null;

        return ResponseEntity.ok().body(photoBase64);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable("roomId") Long id) {
        Room room = roomService.getRoomById(id);
        RoomResponse roomResponse = roomToRoomResponse(room);
        return ResponseEntity.ok().body(roomResponse);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable("roomId") Long roomId,
                                                   @RequestParam(value = "roomType") final String roomType,
                                                   @RequestParam(value = "roomPrice") final BigDecimal roomPrice,
                                                   @RequestParam(value = "photo") final MultipartFile photoFile) {

        Room updatedRoom = roomService.updateRoom(roomId, roomType, roomPrice, photoFile);
        RoomResponse roomResponse = roomToRoomResponse(updatedRoom);
        return ResponseEntity.ok().body(roomResponse);
    }

    private List<RoomResponse> getAllRoomResponse(List<Room> rooms) {
        return rooms.stream()
                .map(this::roomToRoomResponse)
                .collect(Collectors.toList());
    }

    private RoomResponse roomToRoomResponse(Room room) {
        RoomResponse roomResponse = new RoomResponse();
        byte[] photoBytes = roomService.getPhotoByRoomId(room.getId());

        String photoBase64 =  photoBytes != null
                ? new String(new Base64().encode(photoBytes))
                : null;

        roomResponse.setId(room.getId());
        roomResponse.setRoomType(room.getRoomType());
        roomResponse.setRoomPrice(room.getRoomPrice());
        roomResponse.setPhoto(photoBase64);
        // getting the history for each room
        //List<BookingResponse> bookingResponseList = getBookingResponseList(room);
        roomResponse.setBookings(Collections.EMPTY_LIST);

        return roomResponse;
    }

    private List<BookingResponse> getBookingResponseList(Room room) {
        List<BookedRoom> bookedRooms = getAllBookingsByRoomId(room.getId());
        // BookedRoom list to response dto list
        List<BookingResponse> bookingResponseList = bookedRooms.stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode())
                ).collect(Collectors.toList());
        return bookingResponseList;
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }
}
