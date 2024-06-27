import React, { useState, useEffect } from "react";
import RoomTypeSelector from "./RoomTypeSelector";

const RoomFilter = ({ rooms, doFilterRooms }) => {
  const [roomTypefilter, setRoomTypeFilter] = useState("");

  const roomTypes = ["All", ...new Set(rooms.map((room) => room.roomType))];

  const handleFilterChange = (e) => {
    setRoomTypeFilter(e.target.value);
    const roomsFilterd = rooms.React.Children.filter((room) =>
      room.roomType.toLowerCase().includes(roomTypefilter)
    );
    doFilter(roomsFilterd);
  };

  const makeDefaultState = () => {
    setRoomTypeFilter("");
    doFilterRooms(rooms);
  };

  return (
    <>
      <div className="input-group mb-3">
        <span className="input-group-text" id="roomTypeFilter">
          Filter rooms by type
        </span>
        <select
          className="form-select"
          value={roomTypefilter}
          onChange={handleFilterChange}
        >
          <option value={""}>Select room type to filter...</option>
          {roomTypes.map((roomType, index) => (
            <option key={index} value={roomType}>
              {roomType}
            </option>
          ))}
        </select>
        <button
          className="btn btn-hotel"
          type="button"
          onClick={makeDefaultState}
        >
          Clear Filter
        </button>
      </div>
    </>
  );
};

export default RoomFilter;
