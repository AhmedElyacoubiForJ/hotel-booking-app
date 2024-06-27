import { useState, useEffect } from "react";
import "./App.css";

import { getAllRooms } from "./components/utils/ApiFunctions";

import AddRoom from "./components/room/AddRoom";
import RoomPaginator from "./components/common/RoomPaginator";

function App() {
  const [currentPage, setCurrentPage] = useState(1);

  const handleGoToPage = (page) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    // getAllRooms().then(rooms => {
    //   console.log(rooms)
    // })
  }, []);

  return (
    <>
      {/* <AddRoom /> */}
      <RoomPaginator
        totalPages={10}
        currentPage={currentPage}
        handleGoToPage={handleGoToPage}
      />{" "}
      {/* pagination component */}
    </>
  );
}

export default App;
