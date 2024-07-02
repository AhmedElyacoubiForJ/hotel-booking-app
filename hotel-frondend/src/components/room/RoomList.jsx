import React, { useState, useEffect } from "react";

import RoomPaginator from "../common/RoomPaginator";
import RoomFilter from "../common/RoomFilter";

import { getAllRooms } from "../utils/ApiFunctions";

const RoomList = () => {
  const [rooms, setRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const [selectedRoomType, setSelectedRoomType] = useState("")
  const [roomsFiltered, setRoomsFiltered] = useState([]);

  // pagination
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(8);
  const [numberOfPages, setNumberOfPages] = useState(0);

  const handleGoToPage = (page) => {
    setCurrentPage(page);
  };

  const calculateTotalPages = () => {
    let numberOfPages = rooms.length / roomsPerPage;
    if (numberOfPages % 2 !== 0) {
      numberOfPages = int(numberOfPages + 1);
    }
    return numberOfPages;
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

  const fetchRooms = async () => {
    setIsLoading(true);
    try {
      const result = await getAllRooms();
      setRooms(result);
      setIsLoading(false);
      //setNumberOfPages(calculateTotalPages())
      setSuccessMessage("Rooms fetched successfully");
    } catch (error) {
      setErrorMessage("Error fetching rooms");
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchRooms();
  }, []);

   useEffect(() => {
    doFilterRoomsByRoomType(selectedRoomType)
  }, [rooms, selectedRoomType]);
  
  return (
    <>
      <div>
        <RoomFilter allRooms={rooms} setSelectedRoomType={setSelectedRoomType}/>
        {isLoading ? (
          <p>Loading rooms...</p>
        ) : (
          roomsFiltered.map((room) => (
            <div key={room.id}>
              <h3>{room.roomType}</h3>
              <p>{room.roomPrice}</p>
              {/* <img src={`data:image/png;base64,${room.photo}`} ></img> */}
            </div>
          ))
        )}
        {/*  <RoomPaginator
            totalPages={numberOfPages}
            currentPage={currentPage}
            handleGoToPage={handleGoToPage}
        />
 */}
      </div>
    </>
  );
};

export default RoomList;
