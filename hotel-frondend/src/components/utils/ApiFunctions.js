import axios from "axios";

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
