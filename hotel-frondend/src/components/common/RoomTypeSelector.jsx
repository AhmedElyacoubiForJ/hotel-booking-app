import React, { useState, useEffect } from "react";

import { getRoomTypes } from "../utils/ApiFunctions";

const RoomTypeSelector = ({ newRoom, handleOnChange }) => {
  // roomType from db
  const [roomTypes, setRoomTypes] = useState([]);
  // show input field to specify a new room type, if is not already defined
  const [showNewRoomTypeInput, setShowNewRoomTypeInput] = useState(false);
  const [newRoomType, setNewRoomType] = useState("");

  const handleAddNewRoomType = () => {
    if (newRoomType !== "") {
      setRoomTypes([...roomTypes, newRoomType]);
      setNewRoomType("");
    }
    setShowNewRoomTypeInput(false);
  };

  useEffect(() => {
    getRoomTypes().then((data) => {
      setRoomTypes(data);
    });
  }, []);

  return (
    <>
      {roomTypes.length > 0 && (
        <div>
          <select
            className="form-select"
            id="roomType"
            name="roomType"
            value={newRoom.roomType}
            onChange={(e) => {
              if (e.target.value === "Add New") {
                setShowNewRoomTypeInput(true);
              } else {
                handleOnChange(e);
              }
            }}
          >
            <option value={""}>Select a room type</option>
            <option value={"Add New"}>Add New</option>
            {roomTypes.map((roomType, index) => (
              <option key={index} value={roomType}>
                {roomType}
              </option>
            ))}
          </select>
          {showNewRoomTypeInput && (
            <div className="input-group">
              <input
                type="text"
                className="form-control"
                placeholder="Enter a new Room Type"
                id="newRoomType"
                name="newRoomType"
                value={newRoomType}
                onChange={(e) => setNewRoomType(e.target.value)}
              />
              <button
                onClick={handleAddNewRoomType}
                className="btn btn-hotel"
                type="button"
              >
                Add
              </button>
            </div>
          )}
        </div>
      )}
    </>
  );
};

export default RoomTypeSelector;
