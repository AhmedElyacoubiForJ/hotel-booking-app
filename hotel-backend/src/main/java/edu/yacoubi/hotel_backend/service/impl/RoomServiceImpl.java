package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.exception.InternalServerException;
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
    private String NOT_FOUND_MESSAGE = "Room with ID(%d) does not exist";

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAllRoomsIdSorted();
        //return roomRepository.findAll();
    }

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) {
        if (roomType == null || roomType.isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (roomPrice == null) {
            throw new IllegalArgumentException("Room price cannot be null");
        }

        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        try {
            if (!file.isEmpty()) {
                byte[] photoBytes = file.getBytes();
                Blob photoBlob = new SerialBlob(photoBytes);
                room.setPhoto(photoBlob);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Error processing room photo", e);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
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
                    String message = String.format(NOT_FOUND_MESSAGE, id);
                    log.error(message);
                    return new RoomNotFoundException(message);
                });
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile file) {
        // 1. parameters check
        Room room = getRoomById(roomId);

        if (roomType == null || roomType.isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (roomPrice == null) {
            throw new IllegalArgumentException("Room price cannot be null");
        }
        if (file == null ) {
            throw new IllegalArgumentException("Photo cannot be null or empty");
        }

        // 2. update room properties
        // 2.1 update room type
        room.setRoomType(roomType);
        // 2.2 update room price
        room.setRoomPrice(roomPrice);

        byte[] photoBytes = null;
        try {
            photoBytes = file.getBytes();
        } catch (IOException ex) {
            log.error("Error processing room photo", ex);
            throw new InternalServerException("Error processing room photo");
        }

        try {
            Blob photoBlob = new SerialBlob(photoBytes);
            // 2.3 update photo
            room.setPhoto(photoBlob);
        } catch (SQLException ex) {
            log.error("Error processing room photo", ex);
            throw new InternalServerException("Error processing room photo");
        }

        return roomRepository.save(room);
    }
}
