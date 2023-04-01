package com.example.shapeApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.example.shapeApp.model.ShapeType.CIRCLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("CIRCLE")
public class Circle extends Shape {

    private double radius;

    public Circle(UUID id, double radius, double area, double perimeter) {
        super(id, area, perimeter);
        this.radius = radius;
    }
}