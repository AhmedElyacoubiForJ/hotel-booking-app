import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "/node_modules/bootstrap/dist/js/bootstrap.min.js"
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import Footer from "./components/layout/Footer"
import RoomList from "./components/room/RoomList";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import AddRoom from "./components/room/AddRoom";
function App() {
  return (
    <>
    <main>
      <Router>
        <Routes>
          {/* <Route exact path="/" element={<Home />} /> */}
          <Route exact path="/" element={<RoomList />} />
          <Route exact path="/edit-room/:roomId" element={<EditRoom />} />
          <Route exact path="/rooms-list" element={<RoomList />} />
          <Route exact path="/add-room" element={<AddRoom />} />
        </Routes>
        <Footer />
      </Router>
    </main>
    </>
  );
}

export default App;
