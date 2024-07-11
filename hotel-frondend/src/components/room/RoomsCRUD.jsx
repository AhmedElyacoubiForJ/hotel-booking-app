import React, { useState, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { FaEdit, FaEye, FaPlus, FaTrashAlt } from "react-icons/fa";
import { Link } from "react-router-dom";

import RoomPaginator from "../common/RoomPaginator";
import RoomFilter from "../common/RoomFilter";

import { getAllRooms, deleteRoom } from "../utils/ApiFunctions";

const RoomsCRUD = () => {
  const [rooms, setRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const [selectedRoomType, setSelectedRoomType] = useState("");
  const [roomsFiltered, setRoomsFiltered] = useState([]);

  // pagination
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(2);
  const [numberOfPages, setNumberOfPages] = useState(0);

  const handleGoToPage = (page) => {
    setCurrentPage(page);
  };

  const calculateTotalPages = (rooms, roomsFiltered, roomsPerPage) => {
    console.log("Calculating total pages");
    const totalRooms =
      roomsFiltered.length > 0 ? roomsFiltered.length : rooms.length;
    return Math.ceil(totalRooms / roomsPerPage);
  };

  const doFilterRoomsByRoomType = (keyword) => {
    if (keyword === "" || keyword === "All") {
      setRoomsFiltered(rooms);
    } else {
      const roomsToKeyword = rooms.filter((room) => room.roomType === keyword);
      setRoomsFiltered(roomsToKeyword);
    }
    setCurrentPage(1);
  };

  const handleDelete = async (roomId) => {
    try {
      const result = await deleteRoom(roomId);
      if (result === "") {
        const updatedRooms = rooms.filter((room) => room.id !== roomId);
        setRooms(updatedRooms);
        setSuccessMessage(`Room No.: ${roomId} was deleted`);
        //fetchRooms();
      } else {
        console.log(`Error deleting room : ${roomId} : ${result.message}`);
      }
    } catch (error) {
      setErrorMessage("Error deleting room");
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000); // after 3 s. clean messages
  };

  const fetchRooms = async () => {
    setIsLoading(true);
    try {
      const result = await getAllRooms();
      setRooms(result);
      setIsLoading(false);
      setSuccessMessage("Rooms fetched successfully");
    } catch (error) {
      setErrorMessage("Error fetching rooms");
      setIsLoading(false);
    }
    // after 3 s. clean messages
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000); // after 3 s. clean messages
  };

  useEffect(() => {
    fetchRooms();
  }, []);

  useEffect(() => {
    doFilterRoomsByRoomType(selectedRoomType);
  }, [rooms, selectedRoomType]);

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMessage && (
          <p className="alert alert-success mt-5 fade show" role="alert">
            {successMessage}
          </p>
        )}
        {errorMessage && (
          <p className="alert alert-danger mt-5 fade show" role="alert">
            {errorMessage}
          </p>
        )}
      </div>
      {isLoading ? (
        <p>Loading rooms...</p>
      ) : (
        <>
          <section className="container mt-5 mb-5">
            <div className="d-flex justify-content-center mt-5 mb-3">
              <h2>Existing Rooms</h2>
            </div>
            <Row>
              <Col md={6} className="mb-2 md-mb-0">
                <RoomFilter
                  allRooms={rooms}
                  setSelectedRoomType={setSelectedRoomType}
                />
              </Col>
              <Col md={6}>
                <div className="d-flex justify-content-end">
                  <Link to="/add-room" className="btn btn-primary">
                    <FaPlus /> Add Room
                  </Link>
                </div>
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Room Type</th>
                  <th>Room Price</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {roomsFiltered
                  .slice(
                    (currentPage - 1) * roomsPerPage,
                    currentPage * roomsPerPage
                  )
                  .map((room) => (
                    <tr key={room.id} className="text-center">
                      <td>{room.id}</td>
                      <td>{room.roomType}</td>
                      <td>{room.roomPrice}</td>
                      <td className="gap-2">
                        <Link to={`/edit-room/${room.id}`} className="gap-2">
                          <span className="btn btn-info btn-sm">
                            <FaEye />
                          </span>
                          <span className="btn btn-warning btn-sm ml-5">
                            <FaEdit />
                          </span>
                        </Link>
                        <button
                          className="btn btn-danger btn-sm ml-5"
                          onClick={() => handleDelete(room.id)}
                        >
                          <FaTrashAlt />
                        </button>
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
            <RoomPaginator
              totalPages={calculateTotalPages(
                rooms,
                roomsFiltered,
                roomsPerPage
              )}
              currentPage={currentPage}
              handleGoToPage={handleGoToPage}
            />
          </section>
        </>
      )}
    </>
  );
};

export default RoomsCRUD;

// roomsFiltered.map((room) => (
//   <div key={room.id}>
//     <h3>{room.roomType}</h3>
//     <p>{room.roomPrice}</p>
//     {/* <img src={`data:image/png;base64,${room.photo}`} ></img> */}
//   </div>
// ))
