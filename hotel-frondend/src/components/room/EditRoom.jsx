import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

import { getRoomById, updateRoom } from "../utils/ApiFunctions";

const EditRoom = () => {
  const { roomId } = useParams();

  const initialState = { photo: null, roomType: "", roomPrice: 0 };
  const [room, setRoom] = useState(initialState);
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateRoom(roomId, room);
      if (response.status === 200) {
        setSuccessMessage("Room updated successfully");
        const updatedRoom = await getRoomById(room.id);
        setRoom(updatedRoom);
        setImagePreview(updatedRoom.photo);
        setErrorMessage("");
      } else {
        setErrorMessage("Failed to update room");
      }
    } catch (e) {
      console.log(e);
      setErrorMessage(`Error updating room : ${e.message}`);
    }
  };

  const fetchRoom = async () => {
    try {
      const response = await getRoomById(roomId);
      console.log(response);
      setRoom(response);
      setImagePreview(response.photo);
    } catch (e) {
      setErrorMessage(e.message);
    }
  };

  useEffect(() => {
    fetchRoom();
  }, [roomId]);

  return (
    <>
      <section className="container mt-5 mb-5">
        <div className="room justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 mb-2">Edit Room</h2>
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

            <form className="form" onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label hotel-color" htmlFor="roomType">
                  Room Type
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="roomType"
                  name="roomType"
                  value={roomType}
                  onChange={handleOnChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label hotel-color" htmlFor="roomType">
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
                <label className="form-label hotel-color" htmlFor="photo">
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
                    src={`data:image/jpeg;base64,${imagePreview}`}
                    className="mb-3"
                    alt="Room preview"
                    style={{ maxWidth: "400px", maxHeight: "400px" }}
                  />
                )}
              </div>
              <div className="d-grid gap-2 d-md-flex mt-2">
                <Link to={"/rooms-list"} className="btn btn-outline-info ml-5">
                  Back
                </Link>
                <button type="submit" className="btn btn-outline-warning">
                  Edit Room
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};

export default EditRoom;
