package com.example.shapes.model.dto;

public record ShapeRequest(
        String type,
        ShapeParametersRequest parameters) {
}
