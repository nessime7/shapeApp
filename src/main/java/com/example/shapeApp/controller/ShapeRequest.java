package com.example.shapeApp.controller;

import com.example.shapeApp.model.ShapeType;

public record ShapeRequest(
        ShapeType type,
        ShapeParametersRequest parameters) {
}
