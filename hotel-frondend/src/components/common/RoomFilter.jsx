import React, { useState } from "react";

// Room Types Chooser
const RoomFilter = ({ allRooms, setSelectedRoomType }) => {
  const HEADER_OPTION = "All";
  const [filter, setFilter] = useState(HEADER_OPTION);
  const roomTypeOptions = new Set(allRooms.map((room) => room.roomType));

  const roomTypes = [HEADER_OPTION, ...roomTypeOptions];

  const handleSelectedChange = (e) => {
    const selectedRoomType = e.target.value;
    setFilter(selectedRoomType);
    setSelectedRoomType(selectedRoomType);
  };

  const clearFilter = () => {
    setFilter(HEADER_OPTION);
    setSelectedRoomType(HEADER_OPTION);
  };

  return (
    <>
      <div className="input-group mb-3">
        <span className="input-group-text" id="roomTypeFilter">
          Room types
        </span>
        <select
          className="form-select"
          arial-label="room type filter"
          value={filter}
          onChange={handleSelectedChange}
        >
          {roomTypes.map((type, index) => (
            <option key={index} value={type}>
              {type}
            </option>
          ))}
        </select>
        <button className="btn btn-hotel" type="button" onClick={clearFilter}>
          Clear Filter
        </button>
      </div>
    </>
  );
};

export default RoomFilter;
