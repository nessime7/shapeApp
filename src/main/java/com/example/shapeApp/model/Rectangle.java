package com.example.shapeApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("RECTANGLE")
public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(UUID id, double width, double height, double area, double perimeter) {
        super(id, area, perimeter);
        this.width = width;
        this.height = height;
    }
}