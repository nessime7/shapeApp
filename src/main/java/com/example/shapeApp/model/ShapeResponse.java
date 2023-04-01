package com.example.shapeApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShapeResponse {

    private UUID id;
    private double area;
    private double perimeter;
    private String type;
    private Optional<Double> width;
    private Optional<Double> height;
    private Optional<Double> radius;
}