package com.example.shapeApp.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
public class Circle implements Shape {

    private UUID id;
    private ShapeType type;
    private double radius;
    private double perimeter;
    private double area;


    public Circle(UUID id, ShapeType type, double radius, double perimeter, double area) {
        this.id = id;
        this.type = type;
        this.radius = radius;
        this.perimeter = perimeter;
        this.area = area;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public double area() {
        return area;
    }

    @Override
    public double perimeter() {
        return perimeter;
    }
}