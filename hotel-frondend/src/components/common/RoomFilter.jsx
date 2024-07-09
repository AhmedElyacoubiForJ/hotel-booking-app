import React, { useState } from "react";

// Room Types Chooser
const RoomFilter = ({ allRooms, setSelectedRoomType }) => {
  const headerOption = "All";
  const [selectedOption, setSelectedOption] = useState(headerOption);
  const roomTypeOptions = new Set(allRooms.map((room) => room.roomType));

  const roomTypes = [headerOption, ...roomTypeOptions];

  const onSelectedChange = (e) => {
    const selected = e.target.value;
    setSelectedOption(selected);
    setSelectedRoomType(selected);
  };

  const makeDefaultOption = () => {
    setSelectedOption(headerOption);
    setSelectedRoomType(headerOption);
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
          value={selectedOption}
          onChange={onSelectedChange}
        >
          {roomTypes.map((type, index) => (
            <option key={index} value={type}>
              {type}
            </option>
          ))}
        </select>
        <button
          className="btn btn-hotel"
          type="button"
          onClick={makeDefaultOption}
        >
          Clear Filter
        </button>
      </div>
    </>
  );
};

export default RoomFilter;
