import React, { useState, useEffect } from "react";

import RoomPaginator from "../components/common/RoomPaginator";
import RoomFilter from "../components/common/RoomFilter";

import { getAllRooms } from "../utils/ApiFunctions";


const RoomList = () => {
  // Fetch rooms from API and render them here
  const [rooms, setRooms] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(8);
  const [numberOfPages, setNumberOfPages] = useState(0)

  const [isLoading, setIsLoading] = useState(false);

  const handleGoToPage = (page) => {
    setCurrentPage(page);
  };

  const calculateTotalPages = () => {
    let numberOfPages = rooms.length / roomsPerPage;
    if (numberOfPages % 2 !== 0) {
        numberOfPages = numberOfPages + 1;
    }
    return numberOfPages;
  }

  const doFilterRooms = () => {
    // Implement your filtering logic here
    // For example, filter rooms by selected room types and prices
    // const filteredRooms = rooms.filter((room) => room.roomType === selectedRoomType && room.roomPrice >= selectedMinPrice && room.roomPrice <= selectedMaxPrice);
    // setFilteredRooms(filteredRooms);
    // setCurrentPage(1); // Reset current page to first page when filtering
  }
  
  const fetchRooms = async () => {
    setIsLoading(true);
    const response = await getAllRooms();
    const data = await response.json();
    setRooms(data);
  };

  useEffect(() => {
    fetchRooms();
    setNumberOfPages(calculateTotalPages())
  }, []);

  return (
    <>
      <div>
        <RoomFilter rooms={rooms} doFilterRooms={doFilterRooms} />
        
        {isLoading? (
          <p>Loading...</p>
        ) : (
          rooms.map((room) => (
            <div key={room.id}>
              <h3>{room.roomType}</h3>
              <p>{room.roomPrice}</p>
              <img src={`data:image/png;${room.photo}`} ></img>
            </div>
          ))
        )}
        <RoomPaginator
            totalPages={numberOfPages}
            currentPage={currentPage}
            handleGoToPage={handleGoToPage}
        />
      </div>
    </>
  );
};

export default RoomList;
