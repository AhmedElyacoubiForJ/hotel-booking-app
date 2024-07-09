package edu.yacoubi.hotel_backend.service.impl;

import edu.yacoubi.hotel_backend.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

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
    void shouldCallRepoGetAllRoomsMethod() {
        // given
        // when
        underTest.getAllRooms();
        // then
        verify(roomRepository).findAll();
    }
}