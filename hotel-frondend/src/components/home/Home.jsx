import React from "react";

import MainHeader from "../layout/MainHeader";
import HotelService from "../common/HotelService";
import Parallax from "../common/Parallax";

const Home = () => {
  return (
    <section>
      <MainHeader />
      <div className="container">
        <Parallax />
        <HotelService />
      </div>
    </section>
  );
};

export default Home;
