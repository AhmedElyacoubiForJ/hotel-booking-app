import React from "react";
import { Link, NavLink } from "react-router-dom";

const NavBar = () => {
  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary px-5 shadow mt-5 sticky-top">
      <div className="container-fluid">
        <Link to={"/"} className="navbar-brand">
          <span className="hotel-color">Anonym Hotel</span>
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav navbar-nav-scroll me-auto my-2 my-lg-0">
            <li className="nav-item">
              <NavLink
                to={"/browse-all-rooms"}
                className="nav-link"
                aria-current="page"
              >
                Browse all rooms
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to={"/admin"} className="nav-link" aria-current="page">
                Admin
              </NavLink>
            </li>
          </ul>
          <ul className="navbar-nav d-flex">
            <li className="nav-item">
              <NavLink className="nav-link" to={"/find-booking"}>
                Find My Booking
              </NavLink>
            </li>
            <li className="nav-item dropdown">
              <a
                className={`nav-link dropdown-toggle`}
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Account
              </a>
              <ul className="dropdown-menu show" aria-labelledby="navbarDropdown">
                <li className="dropdown-item">
                  <Link to={"/login"} className="dropdown-item">
                    Login
                  </Link>
                </li>
                <li className="dropdown-item">
                  <Link to={"/profile"} className="dropdown-item">
                    Profile
                  </Link>
                </li>
                <li className="dropdown-item">
                  <Link to={"/logout"} className="dropdown-item">
                    Logout
                  </Link>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
