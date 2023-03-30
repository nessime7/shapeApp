package com.example.shapeApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shape {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ShapeType type;
    private JSONObject parameters;
    private double perimeter;
    private double area;


    public Shape(UUID id, ShapeType type, JSONObject parameters) {
        this.id = id;
        this.type = type;
        this.parameters = parameters;
    }

}