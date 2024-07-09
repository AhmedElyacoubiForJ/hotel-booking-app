package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository;

    private RoomServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new RoomServiceImpl(roomRepository);
    }
    
    @Test
    void shouldGetAllRooms() {
        // given
        List<Room> rooms = Arrays.asList(new Room(), new Room());
        when(roomRepository.findAllRoomsIdSorted()).thenReturn(rooms);

        // when
        List<Room> result = underTest.getAllRooms();

        // then
        assertEquals(rooms, result);
        verify(roomRepository).findAllRoomsIdSorted();
    }
}