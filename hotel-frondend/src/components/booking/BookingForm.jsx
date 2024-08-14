import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { moment } from "moment";

import "bootstrap/dist/css/bootstrap.min.css";
import { Form, FormControl, Button, Alert } from "react-bootstrap";

import { getRoomById, bookRoom } from "../utils/ApiFunctions";

const BookingForm = () => {
  const { roomId } = useParams();
  const [validated, setValidated] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [roomPrice, setRoomPrice] = useState(0);
  const [booking, setBooking] = useState({
    guestFullName: "",
    guestEmail: "",
    checkInDate: "",
    checkOutDate: "",
    numberOfAdults: 0,
    numberOfChildren: 0,
  });
  const [roomInfo, setRoomInfo] = useState({
    photo: "",
    roomType: "",
    roomPrice: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBooking({ ...booking, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    try {
      // Submit booking data to the server

      const response = await bookRoom(roomId, booking);

      setIsSubmitted(true);
      setErrorMessage("");
    } catch (error) {
      setIsSubmitted(false);
      setErrorMessage("Failed to submit booking. Please try again.");
    }
  };

  const getRoomPriceById = async (roomId) => {
    try {
      const response = await getRoomById(roomId);
      setRoomPrice(response.roomPrice);
      //setRoomInfo({...response, roomPrice });
    } catch (error) {
      throw new Error(error);
    }
  };

  const calculatePayment = () => {
    const checkInDate = moment(booking.checkInDate);
    const checkOutDate = moment(booking.checkOutDate);
    const durationInDays = checkOutDate.diff(checkInDate, "days");
    const totalPrice = roomPrice * durationInDays;
    return totalPrice;
  };

  // validate guest it must at least one adult
  const isGuestCountValid = () => {
    const adultCount = parseIn(booking.numberOfAdults);
    const childrenCount = parseIn(booking.numberOfChildren);
    const totalCount = adultCount + childrenCount;
    return totalCount >= 1 && adultCount >= 1;
  };

  const isCheckOutDateValid = () => {
    const checkInDate = moment(booking.checkInDate);
    const checkOutDate = moment(booking.checkOutDate);
    return checkOutDate.isSameOrAfter(checkInDate);
  };

  const validateForm = () => {
    if (
      !booking.guestName ||
      !booking.guestEmail ||
      !booking.checkInDate ||
      !booking.checkOutDate ||
      !isGuestCountValid() ||
      !isCheckOutDateValid()
    ) {
      setErrorMessage(
        "Please fill in all required fields and ensure the check-out date is after the check-in date."
      );
      setValidated(false);
      return false;
    }

    setValidated(true);
    return true;
  };

  useEffect(() => {
    getRoomPriceById(roomId);
  }, [roomId]);

  return (
    <div className="container">
      <h1 className="mt-5">Booking Form</h1>
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
      {isSubmitted && (
        <Alert variant="success">Booking submitted successfully!</Alert>
      )}
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <Form.Group>
          <Form.Label>Fullname:</Form.Label>
          <FormControl
            type="text"
            name="guestName"
            value={booking.guestFullName}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Guest Email:</Form.Label>
          <FormControl
            type="email"
            name="guestEmail"
            value={booking.guestEmail}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Check-in Date:</Form.Label>
          <FormControl
            type="date"
            name="checkInDate"
            value={booking.checkInDate}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Check-out Date:</Form.Label>
          <FormControl
            type="date"
            name="checkOutDate"
            value={booking.checkOutDate}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Number of Adults:</Form.Label>
          <FormControl
            type="number"
            name="numberOfAdults"
            value={booking.numberOfAdults}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Number of Children:</Form.Label>
          <FormControl
            type="number"
            name="numberOfChildren"
            value={booking.numberOfChildren}
            onChange={handleInputChange}
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Total Price:</Form.Label>
          <FormControl plaintext readOnly value={calculatePayment()} />
        </Form.Group>
        <Button type="submit" variant="primary">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default BookingForm;