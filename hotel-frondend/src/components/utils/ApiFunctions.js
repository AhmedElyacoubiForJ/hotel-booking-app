import axios from "axios";
import ApiError from "../utils/exceptions/ApiError";

export const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Create a new room
export async function addRoom(photo, roomType, roomPrice) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);
  const res = await api.post("/rooms/add", formData);
  return (res.status = 201 ? true : false);
}

// get all room types
export async function getRoomTypes() {
  try {
    const res = await api.get("/rooms/room/types");
    return res.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

// get all rooms
export async function getAllRooms() {
  try {
    const res = await api.get("/rooms/all");
    return res.data;
  } catch (error) {
    throw new Error("Error fetching rooms");
  }
}

// delete room by id
export async function deleteRoom(roomId) {
  try {
    const result = await api.delete(`/rooms/delete/${roomId}`);
    return result.data;
  } catch (error) {
    throw new Error(
      `Error deleting room with ID : ${roomId} : ${error.message}`
    );
  }
}

export async function updateRoom(roomId, roomData) {
  console.log("Updating room");
  const formData = new FormData();
  formData.append("roomType", roomData.roomType);
  formData.append("roomPrice", roomData.roomPrice);
  formData.append("photo", roomData.photo);
  try {
    const response = await api.put(`/rooms/update/${roomId}`, formData
      //, {headers: {"Content-Type": "multipart/form-data",},}
  );
    return response;
  } catch (error) {
    throw new Error(
      `Error updating room with ID : ${roomId} : ${error.message}`
    );
  }
}

export async function getRoomById(roomId) {
  try {
    const result = await api.get(`/rooms/${roomId}`);
    return result.data;
  } catch (error) {
    //console.log(error.response.data.statusCode)
    //console.log(error.response.data.message);
    throw new Error(`Fetching error: ${error.response.data.message}`);
  }
}

export async function bookRoom(roomId, bookingData) {
  try {
    const response = await api.post(`/bookings/room/${roomId}`, bookingData);
    return response.data;
  } catch (error) {
    throw new Error(`Booking error: ${error.response.data.message}`);
  }
}

export async function getAllBookings() {
  try {
    const response = await api.get("/bookings");
    return response.data;
  } catch (error) {
    throw new Error(`Error fetching bookings : ${error.message}`);
  }
}

export async function getBookingByConfirmationCode(confirmationCode) {
  try {
    const response = await api.get(`/bookings/confirmation/${confirmationCode}`);
    return response.data;
  } catch (error) {
    throw new Error(`Error fetching booking by code : ${error.message}`);
  }
}

export async function cancelBooking(bookingId) {
  try {
    const response = await api.delete(`/bookings/cancel/${bookingId}`);
    return response.data;
  } catch (error) {
    throw new Error(`Error canceling booking : ${error.message}`);
  }
}
