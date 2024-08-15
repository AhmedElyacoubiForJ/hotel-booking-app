import React from "react";

// RoomPaginator component that displays pagination for rooms
const RoomPaginator = ({ totalPages, currentPage, handleGoToPage }) => {
  // Creating an array of page numbers from 1 to totalPages
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);
  return (
    <nav>
      <ul className="pagination justify-content-center mt-2">
        {pageNumbers.map((pageNumber) => (
          // Each page number is rendered as a list item
          <li
            key={pageNumber}
            className={`page-item ${
              pageNumber === currentPage ? "active" : ""
            }`}
          >
            <button
              onClick={() => handleGoToPage(pageNumber)}
              className="page-link"
            >
              {pageNumber}
            </button>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default RoomPaginator;
