package com.example.shapeApp.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
public class Rectangle implements Shape {

    private UUID id;
    private ShapeType type;
    private double width;
    private double height;
    private double perimeter;
    private double area;


    public Rectangle(UUID id, ShapeType type, double width, double height, double perimeter, double area) {
        this.id = id;
        this.type = type;
        this.width = width;
        this.height = height;
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