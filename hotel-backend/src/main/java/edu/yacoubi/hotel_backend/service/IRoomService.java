package edu.yacoubi.hotel_backend.service;

import edu.yacoubi.hotel_backend.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing hotel rooms.
 */
public interface IRoomService {

    /**
     * Adds a new room to the hotel.
     *
     * @param file       The photo of the room.
     * @param roomType   The type of the room.
     * @param roomPrice  The price of the room.
     * @return The added room.
     */
    Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice);

    /**
     * Retrieves all available room types.
     *
     * @return A list of room types.
     */
    List<String> getAllRoomTypes();

    /**
     * Retrieves all rooms in the hotel.
     *
     * @return A list of rooms.
     */
    List<Room> getAllRooms();

    /**
     * Retrieves the photo of a room by its ID.
     *
     * @param id The ID of the room.
     * @return The photo of the room as a byte array.
     */
    byte[] getPhotoByRoomId(Long id);

    /**
     * Deletes a room from the hotel by its ID.
     *
     * @param id The ID of the room.
     */
    void deleteRoom(Long id);

    /**
     * Retrieves a room by its ID.
     *
     * @param roomId The ID of the room.
     * @return The room.
     */
    Room getRoomById(Long roomId);

    /**
     * Updates a room's information.
     *
     * @param roomId     The ID of the room.
     * @param roomType   The updated type of the room.
     * @param roomPrice  The updated price of the room.
     * @param file       The updated photo of the room.
     * @return The updated room.
     */
    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile file);
}