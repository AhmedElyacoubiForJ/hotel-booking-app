import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { addRoom } from "../utils/ApiFunctions";
import RoomTypeSelector from "../common/RoomTypeSelector";

const AddRoom = () => {
  const defaultRoomState = { photo: null, roomType: "", roomPrice: 0 };
  const [room, setRoom] = useState(defaultRoomState);
  const { photo, roomType, roomPrice } = room;

  const [imagePreview, setImagePreview] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setRoom({ ...room, [name]: value });
  };

  const handleImageChange = (e) => {
    const fileName = e.target.files[0];
    setRoom({ ...room, photo: fileName });
    setImagePreview(URL.createObjectURL(fileName));
  };

  const handleAddRoomSubmit = (e) => {
    e.preventDefault();
    addRoom(photo, roomType, roomPrice)
      .then((res) => {
        setSuccessMessage("Room added successfully");
        setRoom(defaultRoomState);
        setImagePreview("");
      })
      .catch((err) => {
        // err.response.data.message
        setErrorMessage("Error adding new room");
        setRoom(defaultRoomState);
        setImagePreview("");
        console.log(err.response.data.message);
      });
  };

  return (
    <>
      <section className="container mt-5 mb-5">
        <div className="room justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 mb-2">Add a New Room</h2>
            <form className="form" onSubmit={handleAddRoomSubmit}>
              <div className="mb-3">
                <label className="form-label" htmlFor="roomType">
                  Room Type
                </label>
                <div>
                  <RoomTypeSelector
                    room={room}
                    handleOnChange={handleOnChange}
                  />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label" htmlFor="roomType">
                  Room Price
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="roomPrice"
                  name="roomPrice"
                  value={roomPrice}
                  onChange={handleOnChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label className="form-label" htmlFor="photo">
                  Room Photo
                </label>
                <input
                  type="file"
                  className="form-control"
                  id="photo"
                  name="photo"
                  onChange={handleImageChange}
                  required
                />
                {imagePreview && (
                  <img
                    src={imagePreview}
                    className="mb-3"
                    alt="Preview room photo"
                    style={{ maxWidth: "400px", maxHeight: "400px" }}
                  />
                )}
              </div>
              <div className="d-grid d-md-flex mt-2">
                <button type="submit" className="btn btn-outline-primary ml-5">
                  Add Room
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};

export default AddRoom;
