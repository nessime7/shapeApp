package com.example.shapeApp.model;

public record ShapeRequest(
        String type,
        ShapeParametersRequest parameters) {
}
