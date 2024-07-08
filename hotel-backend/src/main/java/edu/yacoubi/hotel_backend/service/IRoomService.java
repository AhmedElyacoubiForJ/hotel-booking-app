package edu.yacoubi.hotel_backend.service;

import edu.yacoubi.hotel_backend.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IRoomService {
    Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice);
    List<String> getAllRoomTypes();
    List<Room> getAllRooms();
    byte[] getPhotoByRoomId(Long id);
    void deleteRoom(Long id);
    Room getRoomById(Long roomId);
    Room updateRoom(Long roomId, String roomType, String roomPrice, byte[] photoBytes);
}
