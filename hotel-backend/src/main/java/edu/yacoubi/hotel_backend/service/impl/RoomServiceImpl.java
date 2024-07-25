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

    private static final String ROOM_NOT_FOUND_MESSAGE = "Room for ID: %d does not exist";
    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        log.debug("Getting all rooms");
        return roomRepository.findAllRoomsIdSorted();
    }

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) {
        // validate input parameters
        validateRoomTypeAndPrice(roomType, roomPrice);
        validateFile(file);

        try {
            Room room = createRoom(file, roomType, roomPrice);
            log.info("Room added successfully: {}", room);
            return roomRepository.save(room);
        } catch (IOException | SQLException e) {
            throw new InternalServerException("Error processing room photo: " + e.getMessage());

        }
    }

    @Override
    public List<String> getAllRoomTypes() {
        log.debug("Getting all room types");
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public byte[] getPhotoByRoomId(Long id) {
        log.debug("Getting photo for room ID: {}", id);
        validateRoomId(id);
        return roomRepository.findPhotoByRoomId(id);
    }

    @Override
    public void deleteRoom(Long id) {
        log.debug("Deleting room for ID: {}", id);
        validateRoomId(id);
        roomRepository.deleteById(id);
        log.info("Room deleted successfully: {}", id);
    }

    @Override
    public Room getRoomById(Long id) {
        log.debug("Getting room for ID: {}", id);
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(String.format(ROOM_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile file) {
        log.debug("Updating room for ID: {}", roomId);
        validateRoomId(roomId);
        validateRoomTypeAndPrice(roomType, roomPrice);
        validateFile(file);

        Room room = getRoomById(roomId);
        updateRoomProperties(room, roomType, roomPrice, file);
        return roomRepository.save(room);
    }

    private void updateRoomProperties(Room room, String roomType, BigDecimal roomPrice, MultipartFile file) {
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        try {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        } catch (IOException | SQLException e) {
            throw new InternalServerException("Error processing room photo: " + e.getMessage());
        }
    }

    private Room createRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }

        return room;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Room photo cannot be null or empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Invalid room photo format. Only JPEG and PNG are supported");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("Room photo size exceeds the maximum allowed size of 10MB");
        }
    }

    private void validateRoomTypeAndPrice(String roomType, BigDecimal roomPrice) {
        if (roomType == null || roomType.isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (roomPrice == null) {
            throw new IllegalArgumentException("Room price cannot be null");
        }
    }

    private void validateRoomId(Long id) {
        if (!roomRepository.existsById(id))
            throw new RoomNotFoundException(String.format(ROOM_NOT_FOUND_MESSAGE, id));
    }
}
