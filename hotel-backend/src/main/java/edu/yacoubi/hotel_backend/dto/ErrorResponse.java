package edu.yacoubi.hotel_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
}
