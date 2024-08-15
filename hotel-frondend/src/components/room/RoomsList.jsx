import React, { useState, useEffect } from "react";

import { Col, Container, Row } from "react-bootstrap";

import { getAllRooms } from "../utils/ApiFunctions";
import RoomCard from "./RoomCard";
import RoomFilter from "../common/RoomFilter";
import RoomPaginator from "../common/RoomPaginator";

const RoomsList = () => {
  const [rooms, setRooms] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(6);
  const [filteredRooms, setFilteredRooms] = useState([]);
  const [selectedRoomType, setSelectedRoomType] = useState("");

  useEffect(() => {
    setIsLoading(true);
    getAllRooms()
      .then((data) => {
        setRooms(data);
        setFilteredRooms(data);
        setIsLoading(false);
      })
      .catch((error) => {
        setErrorMessage(error.message);
        setIsLoading(false);
      });
  }, []);

  const handlePageChange = (page) => setCurrentPage(page);
  

  const totalPages = Math.ceil(filteredRooms.length / roomsPerPage);

  const renderRoomsAsCardFiltered = () => {
    const startIndex = (currentPage - 1) * roomsPerPage;
    const endIndex = startIndex + roomsPerPage;
    return filteredRooms.slice(startIndex, endIndex).map((room) => {
      return <RoomCard key={room.id} room={room} />;
    });
  };

  const filterRoomsByRoomType = (keyword) => {
    if (keyword === "" || keyword === "All") {
      setFilteredRooms(rooms);
    } else {
      const roomsToKeyword = rooms.filter((room) => room.roomType === keyword);
      setFilteredRooms(roomsToKeyword);
    }
    setCurrentPage(1);
  };

  useEffect(() => {
    filterRoomsByRoomType(selectedRoomType);
  }, [rooms, selectedRoomType]);

  if (isLoading) {
    return <div>Loading rooms...</div>;
  }
  if (errorMessage) {
    return <div className="text-danger">Error: {error}</div>;
  }

  return (
    <Container>
      <Row>
        <Col md={6} className="mb-3 mb-md-0">
          <RoomFilter
            allRooms={rooms}
            setSelectedRoomType={setSelectedRoomType}
          />
        </Col>
        <Col md={6} className="d-flex align-items-center justify-content-end">
          <RoomPaginator
            totalPages={totalPages}
            currentPage={currentPage}
            handleGoToPage={handlePageChange}
          />
        </Col>
      </Row>
      <Row>{renderRoomsAsCardFiltered()}</Row>
      <Row>
        <Col md={6} className="d-flex align-items-center justify-content-end">
          <RoomPaginator
            totalPages={totalPages}
            currentPage={currentPage}
            handleGoToPage={handlePageChange}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default RoomsList;
