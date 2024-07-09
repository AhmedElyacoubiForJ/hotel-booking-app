import React, { useState, useEffect } from "react";

import { getAllRooms } from "../utils/ApiFunctions";

const RoomsList = () => {
  const [rooms, setRooms] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(6);
  const [filteredRooms, setFilteredRooms] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    getAllRooms()
      .then((data) => {
        setRooms(data);
        setFilteredRooms(data);
        setIsLoading(false);
      })
      .catch((error) => {
        setError(error.message);
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <div>Loading rooms...</div>;
  }
  if (error) {
    return <div className="text-danger">Error: {error}</div>;
  }

  return (
    <>
      <div>
        <h1>ddsd</h1>
      </div>
    </>
  );
};

export default RoomsList;
