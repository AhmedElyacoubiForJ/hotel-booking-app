package edu.yacoubi.hotel_backend.repository;

import edu.yacoubi.hotel_backend.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @SpringBootTest: This annotation is used to bootstrap the entire application context for integration tests.
 * It loads the complete application, including all the configuration classes, beans, and dependencies.

 * @AutoConfigureTestDatabase: This annotation is used to automatically configure the test database.
 * By default, it uses an in-memory H2 database,
 * but you can customize it by providing a different database configuration.

 * @DataJpaTest: This annotation is used to test Spring Data JPA repositories.
 * It provides a subset of the application context, including the JPA infrastructure,
 * and automatically configures the test database. It also includes the @AutoConfigureTestDatabase annotation.

 * @DirtiesContext: This annotation is used to clean up the test database after each test method.
 * By default, it uses the AFTER_CLASS class mode,
 * which means that the test database will be cleaned up after each test class.
 * */
//@AutoConfigureTestDatabase
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RoomRepositoryTest {

    @Autowired
    private RoomRepository underTest;

    @Test
    void shouldFindFindDistinctRoomTypes() {
        // given
        Room room1 = new Room();
        room1.setRoomType("Standard Room");
        Room room2 = new Room();
        room2.setRoomType("Standard Room");
        Room room3 = new Room();
        room3.setRoomType("Deluxe Room");

        underTest.saveAll(List.of(room1, room2, room3));

        // when
        List<String> result = underTest.findDistinctRoomTypes();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains("Standard Room"));
        assertTrue(result.contains("Deluxe Room"));
    }
}