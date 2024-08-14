import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Footer from "./components/layout/Footer";
import NavBar from "./components/layout/NavBar";
import RoomsCRUD from "./components/room/RoomsCRUD";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import AddRoom from "./components/room/AddRoom";
import RoomsListWrapper from "./components/room/RoomsListWrapper";
import Admin from "./components/admin/Admin";

function App() {
  return (
    <>
      <main>
        <Router>
          <NavBar />
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/add-room" element={<AddRoom />} />
            <Route exact path="/rooms-crud" element={<RoomsCRUD />} />
            <Route exact path="/edit-room/:roomId" element={<EditRoom />} />
            <Route
              exact
              path="/browse-all-rooms"
              element={<RoomsListWrapper />}
            />
            <Route exact path="/admin" element={<Admin />} />
            {/* <Route path="*" element={<NotFound />} /> */}
          </Routes>
        </Router>
        <Footer />
      </main>
    </>
  );
}

export default App;
