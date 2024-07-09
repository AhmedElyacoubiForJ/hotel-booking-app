package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.model.Room;
import edu.yacoubi.hotel_backend.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository;

    @Captor
    private ArgumentCaptor<Room> roomArgumentCaptor;

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

    @Test
    void shouldAddNewRoom() {
        // given
        MultipartFile file = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "photo content".getBytes());
        String roomType = "Single Room";
        BigDecimal roomPrice = BigDecimal.valueOf(100);

        // when
        Room result = underTest.addNewRoom(file, roomType, roomPrice);

        // then
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room capturedRoom = roomArgumentCaptor.getValue();
        assertEquals(roomType, capturedRoom.getRoomType());
        assertEquals(roomPrice, capturedRoom.getRoomPrice());
        assertNotNull(capturedRoom.getPhoto());
    }

    @Test
    void shouldGetAllRoomTypes() {
        // given
        List<String> roomTypes = Arrays.asList("Single Room", "Double Room", "Lodge room", "Luxury room");
        when(roomRepository.findDistinctRoomTypes()).thenReturn(roomTypes);

        // when
        List<String> result = underTest.getAllRoomTypes();

        // then
        assertEquals(roomTypes, result);
        verify(roomRepository).findDistinctRoomTypes();
    }
}