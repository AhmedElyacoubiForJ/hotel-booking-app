package edu.yacoubi.hotel_backend.repository;

import edu.yacoubi.hotel_backend.model.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT DISTINCT r.roomType from Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r.photo FROM Room r WHERE r.id = :id")
    byte[] findPhotoByRoomId(@Param(value = "id") Long id);

    @Query("SELECT r FROM Room r ORDER BY r.id ASC")
    List<Room> findAllRoomsIdSorted();
}
