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

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
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
}
