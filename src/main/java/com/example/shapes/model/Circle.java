package com.example.shapes.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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