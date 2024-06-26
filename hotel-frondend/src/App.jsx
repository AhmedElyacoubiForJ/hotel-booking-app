import { useState, useEffect } from 'react'
import './App.css'

import { getAllRooms } from "./components/utils/ApiFunctions";

import AddRoom from './components/room/AddRoom'

function App() {

useEffect(() =>{
  getAllRooms().then(rooms => {
    console.log(rooms)
  })
}, []);

  return (
    <>
      <AddRoom />
    </>
  )
}

export default App
