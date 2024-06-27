import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const RoomPaginator = ({ totalPages, currentPage, handleGoToPage }) => {
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);
  return (
    <ul className="nav nav-pills">
      {pageNumbers.map((pageNumber) => (
        <li key={pageNumber}>
          <button
            key={pageNumber}
            onClick={() => handleGoToPage(pageNumber)}
            className={`${
              pageNumber === currentPage
                ? "btn btn-info ms-1"
                : "btn btn-outline-info ms-1"
            }`}
          >
            {pageNumber}
          </button>
        </li>
      ))}
    </ul>
  );
};

export default RoomPaginator;
