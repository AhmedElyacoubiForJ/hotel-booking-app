package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.exception.RoomNotFoundException;
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
import java.util.Optional;

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

    @Test
    void shouldGetPhotoByRoomId() {
        // given
        Long id = 1L;
        byte[] photoBytes = "photo content".getBytes();
        when(roomRepository.existsById(id)).thenReturn(true);
        when(roomRepository.findPhotoByRoomId(id)).thenReturn(photoBytes);

        // when
        byte[] result = underTest.getPhotoByRoomId(id);

        // then
        assertEquals(photoBytes, result);
        verify(roomRepository).existsById(eq(id));
        verify(roomRepository).findPhotoByRoomId(eq(id));
    }

    @Test
    void shouldThrownRoomNotFoundExceptionWhenGettingPhotoByNonExistingRoomId() {
        // given
        Long roomId = 1L;
        when(roomRepository.existsById(roomId)).thenReturn(false);

        // when
        assertThrows(RoomNotFoundException.class, () -> underTest.getPhotoByRoomId(roomId));

        // then
        verify(roomRepository).existsById(eq(roomId));
        verify(roomRepository, never()).findPhotoByRoomId(anyLong());
    }

    @Test
    void shouldDeleteRoom() {
        // given
        Long roomId = 1L;
        when(roomRepository.existsById(roomId)).thenReturn(true);

        // when
        underTest.deleteRoom(roomId);

        // then
        verify(roomRepository).existsById(eq(roomId));
        verify(roomRepository).deleteById(eq(roomId));
    }

    @Test
    void shouldThrownRoomNotFoundExceptionWhenDeletingNonExistingRoom() {
        // given
        Long roomId = 1L;
        when(roomRepository.existsById(roomId)).thenReturn(false);

        // when
        assertThrows(RoomNotFoundException.class, () -> underTest.deleteRoom(roomId));

        // then
        verify(roomRepository).existsById(eq(roomId));
        verify(roomRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldGetRoomById() {
        // given
        Long roomId = 1L;
        Room room = new Room();
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // when
        Room result = underTest.getRoomById(roomId);

        // then
        assertEquals(room, result);
        verify(roomRepository).findById(eq(roomId));
    }

    @Test
    void shouldThrownRoomNotFoundExceptionWhenGettingNonExistingRoomById() {
        // given
        Long roomId = 1L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // when
        assertThrows(RoomNotFoundException.class, () -> underTest.getRoomById(roomId));

        // then
        verify(roomRepository).findById(eq(roomId));
    }

    @Test
    void shouldUpdateRoom() {
        // given
        Long roomId = 1L;
        MultipartFile file = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "photo content".getBytes());
        String roomType = "Single Room";
        BigDecimal roomPrice = new BigDecimal(100);
        Room existingRoom = new Room();
        existingRoom.setId(roomId);
        when(roomRepository.existsById(roomId)).thenReturn(true);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));

        // when
        Room result = underTest.updateRoom(roomId, roomType, roomPrice, file);

        // then
        verify(roomRepository).findById(eq(roomId));
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room capturedRoom = roomArgumentCaptor.getValue();
        assertEquals(roomType, capturedRoom.getRoomType());
        assertEquals(roomPrice, capturedRoom.getRoomPrice());
        assertNotNull(capturedRoom.getPhoto());
    }

    @Test
    void shouldThrownIllegalArgumentExceptionWhenUpdatingRoomWithNullRoomType() {
        // given
        Long roomId = 1L;
        String roomType = null;
        BigDecimal roomPrice = BigDecimal.valueOf(100);
        MultipartFile file = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "photo content".getBytes());
        when(roomRepository.findById(eq(roomId))).thenReturn(Optional.of(new Room()));

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.updateRoom(roomId, roomType, roomPrice, file));

        // then
        verify(roomRepository).findById(eq(roomId));
        verify(roomRepository, never()).save(any());
    }

    @Test
    void shouldThrownIllegalArgumentExceptionWhenUpdatingRoomWithNullRoomPrice() {
        // given
        Long roomId = 1L;
        String roomType = "Single Room";
        BigDecimal roomPrice = null;
        MultipartFile file = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "photo content".getBytes());
        when(roomRepository.findById(eq(roomId))).thenReturn(Optional.of(new Room()));

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.updateRoom(roomId, roomType, roomPrice, file));

        // then
        verify(roomRepository).findById(eq(roomId));
        verify(roomRepository, never()).save(any());
    }

    @Test
    void shouldThrownIllegalArgumentExceptionWhenUpdatingRoomWithNullFile() {
        // given
        Long roomId = 1L;
        String roomType = "Single Room";
        BigDecimal roomPrice = BigDecimal.valueOf(100);
        MultipartFile file = null;
        when(roomRepository.findById(eq(roomId))).thenReturn(Optional.of(new Room()));

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.updateRoom(roomId, roomType, roomPrice, file));

        // then
        verify(roomRepository).findById(eq(roomId));
        verify(roomRepository, never()).save(any());
    }
}