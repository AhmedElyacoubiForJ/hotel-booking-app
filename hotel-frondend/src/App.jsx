import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "/node_modules/bootstrap/dist/js/bootstrap.min.js"
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import RoomList from "./components/room/RoomList";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";

function App() {
  return (
    <>
    <main>
      <Router>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/edit-room/:roomId" element={<EditRoom />} />
          <Route exact path="/rooms-list" element={<RoomList />} />
        </Routes>
      </Router>
    </main>
    </>
  );
}

export default App;
