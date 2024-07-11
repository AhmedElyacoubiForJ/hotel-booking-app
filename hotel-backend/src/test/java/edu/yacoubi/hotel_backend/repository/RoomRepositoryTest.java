package edu.yacoubi.hotel_backend.repository;

import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@AutoConfigureTestDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RoomRepositoryTest {

    @Autowired
    private RoomRepository underTest;

    @Test
    void shouldFindFindDistinctRoomTypes() {
        // given
        Room room1 = new Room();
        room1.setRoomType("Standard Room");
        room1.setBookings(Collections.emptyList());
        room1.setPhoto(null);
        room1.setRoomPrice(new BigDecimal(100));
        underTest.save(room1);
       /* Room room2 = new Room();
        room2.setRoomType("Standard Room");
        Room room3 = new Room();
        room3.setRoomType("Deluxe Room");
        underTest.saveAll(List.of(room1, room2, room3));

        // when
        List<String> result = underTest.findDistinctRoomTypes();

        // then
        assertEquals(2, result.size());
        assertEquals(List.of("Standard Room", "Deluxe Room"), result);
        assertTrue(result.contains("Standard Room"));
        assertTrue(result.contains("Deluxe Room"));*/
    }
}