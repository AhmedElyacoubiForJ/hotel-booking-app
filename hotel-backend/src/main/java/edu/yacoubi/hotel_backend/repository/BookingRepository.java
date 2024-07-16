package edu.yacoubi.hotel_backend.repository;

import edu.yacoubi.hotel_backend.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    Optional<BookedRoom> findBookedRoomByBookingConfirmationCode(String code);
    Optional<List<BookedRoom>> findAllByRoomId(Long roomId);
}