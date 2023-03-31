package com.example.shapeApp.model;

import java.util.UUID;

public interface Shape {

    UUID id();
    double area();
    double perimeter();
}