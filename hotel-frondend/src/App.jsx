import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Footer from "./components/layout/Footer";
import NavBar from "./components/layout/NavBar";
import RoomList from "./components/room/RoomList";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import AddRoom from "./components/room/AddRoom";
function App() {
  return (
    <>
      <main>
        <Router>
          <NavBar />
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/add-room" element={<AddRoom />} />
            <Route exact path="/rooms-list" element={<RoomList />} />
            <Route exact path="/edit-room/:roomId" element={<EditRoom />} />
          </Routes>
        </Router>
        <Footer />
      </main>
    </>
  );
}

export default App;
