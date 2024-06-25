import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Create a new room
export async function addRoom(photo, roomType, roomtPrice) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomtPrice", roomtPrice);
  const res = await api.post("/rooma/add", formData);
  return (res.status = 201 ? true : false);
}

// get all room types
export async function getRoomTypes() {
  try {
    const res = await api.get("/rooms/types");
    return res.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}
