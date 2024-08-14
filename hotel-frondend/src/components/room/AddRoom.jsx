import React, { useState } from "react";
import { Link } from "react-router-dom";

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
      });
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000); // after 3 s. clean messages
  };

  return (
    <>
      <section className="container mt-5 mb-5">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 mb-2">Add a New Room</h2>
            {successMessage && (
              <div className="alert alert-success fade show" role="alert">
                {successMessage}
              </div>
            )}

            {errorMessage && (
              <div className="alert alert-danger fade show" role="alert">
                {errorMessage}
              </div>
            )}

            <form className="form" onSubmit={handleAddRoomSubmit}>
              <div className="mb-3">
                <label className="form-label" htmlFor="roomType">
                  Room Type
                </label>
                <div>
                  <RoomTypeSelector
                    newRoom={room}
                    handleOnChange={handleOnChange}
                  />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label" htmlFor="roomPrice">
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
              <div className="d-grid gap-2 d-md-flex mt-2">
                <Link to="/rooms-crud" className="btn btn-outline-info">
                  Back to rooms
                </Link>
                <button type="submit" className="btn btn-outline-primary ml-5">
                  Save Room
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
