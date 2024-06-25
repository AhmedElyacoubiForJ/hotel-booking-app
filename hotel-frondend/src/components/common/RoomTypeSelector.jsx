import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { getRoomTypes } from "../utils/ApiFunctions";

const RoomTypeSelector = ({handleRoomInputChange, newRoom}) => {
  const [roomTypes, setRoomTypes] = useState([]);
  const [enableAddingNewRoomType, setEnableAddingNewRoomType] = useState(false);
  const [newRoomType, setNewRoomType] = useState("");

  const handleEnableAddingNewRoomType = () => {
    setEnableAddingNewRoomType(ture);
  };

  const handleNewRoomTypeChange = (e) => {
    const { name, value } = e.target;
    setNewRoomType({ ...newRoomType, [name]: value });
  };

  const handleAddNewRoomType = () => {
    if (newRoomType !== "") {
      setRoomTypes([...roomTypes, newRoomType]);
      setNewRoomType("");
      setEnableAddingNewRoomType(false);
    }
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
                        setEnableAddingNewRoomType(true);
                    } else {
                        handleRoomInputChange(e);
                    }
                }}
            >
                <option value={""}>Select a room type</option>
                <option value={"Add New"}>Add New</option>
                {roomTypes.map((roomType) => (
                    <option key={roomType} value={roomType}>
                        {roomType}
                    </option>
                ))}
            </select>
        </div>
    )}
  </>
);
};

export default RoomTypeSelector;
