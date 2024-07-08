package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.exception.RoomNotFoundException;
import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.RoomRepository;
import edu.yacoubi.hotel_backend.service.IRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private String NOT_FOUND_MESSAGE = "Room with ID=%d does not exist";

    @Override
    public Room addNewRoom(MultipartFile file, String roomType,
                           BigDecimal roomPrice) {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = null;
            try {
                photoBytes = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Blob photoBlob = null;
            try {
                photoBlob = new SerialBlob(photoBytes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getPhotoByRoomId(Long id) {
        if (!roomRepository.existsById(id))
            throw new RoomNotFoundException(String.format(NOT_FOUND_MESSAGE, id));

        return roomRepository.findPhotoByRoomId(id);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id))
            throw new RoomNotFoundException(String.format(NOT_FOUND_MESSAGE, id));

        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Room with ID=%d does not exist", id);
                    log.error(message);
                    return new RoomNotFoundException(message);
                });
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, String roomPrice, byte[] photoBytes) {
        return null;
    }
}
