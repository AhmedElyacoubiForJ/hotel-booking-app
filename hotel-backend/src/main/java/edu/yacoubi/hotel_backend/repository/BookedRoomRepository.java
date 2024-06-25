package edu.yacoubi.hotel_backend.repository;

import edu.yacoubi.hotel_backend.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
}
