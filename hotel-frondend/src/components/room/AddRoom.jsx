import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { addRoom } from "../utils/ApiFunctions";

const AddRoom = () => {
  const defaultRoomState = { photo: null, roomType: "", roomPrice: 0 };
  const [room, setRoom] = useState(defaultRoomState);
  const { photo, roomType, roomPrice } = room;

  const [imagePreview, setImagePreview] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRoom({ ...room, [name]: value });
  };

  const handleAddRoomSubmit = (e) => {
    e.preventDefault();
    //const formData = new FormData();
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
            <form className="form" onSubmit={handleAddRoomSubmit} >
              <div className="mb-3">
                <label className="form-label" htmlFor="roomType">Room Type</label>
                <input
                  type="text"
                  className="form-control"
                  id="roomType"
                  name="roomType"
                  value={roomType}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label" htmlFor="roomType">Room Price</label>
                <input
                  type="number"
                  className="form-control"
                  id="roomPrice"
                  name="roomPrice"
                  value={roomPrice}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label" htmlFor="photo">Photo</label>
                <input
                  type="file"
                  className="form-control"
                  id="photo"
                  name="photo"
                  onChange={(e) => {
                    setRoom({ ...room, photo: e.target.files[0] });
                    setImagePreview(URL.createObjectURL(e.target.files[0]));
                  }}
                />
              </div>
              <button type="submit" className="btn btn-success">
                Add Room
              </button>
            </form>
          </div>

        </div>
      </section>
    </>
  );
};

export default AddRoom;
