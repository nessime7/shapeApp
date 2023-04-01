package com.example.shapes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "shape_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Shape {

    @Id
    private UUID id;
    private double area;
    private double perimeter;
}