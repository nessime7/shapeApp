package com.example.shapeApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static java.util.Optional.empty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShapeParametersRequest {

    public Optional<Double> radius = empty();
    public Optional<Double> width = empty();
    public Optional<Double> height = empty();

}
