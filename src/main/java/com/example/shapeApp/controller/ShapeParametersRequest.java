package com.example.shapeApp.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShapeParametersRequest {
    public Optional<Double> radius;
    public Optional<Double> width;
    public Optional<Double> height;
}
