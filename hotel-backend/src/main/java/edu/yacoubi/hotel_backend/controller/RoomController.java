package edu.yacoubi.hotel_backend.controller;

import edu.yacoubi.hotel_backend.dto.RoomResponseDto;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173/")
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/add")
    public ResponseEntity<RoomResponseDto> addNewRoom(
            @RequestParam("photo") final MultipartFile photo,
            @RequestParam("roomType") final String roomType,
            @RequestParam("roomPrice") final BigDecimal roomPrice
    ) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        return ResponseEntity.ok(new RoomResponseDto(
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
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponseDto> roomResponseDtos = rooms.stream().map(room -> {
            return new RoomResponseDto(
                    room.getId(),
                    room.getRoomType(),
                    room.getRoomPrice()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(roomResponseDtos);
    }
}
