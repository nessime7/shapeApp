package com.example.shapeApp.repository;

import com.example.shapeApp.model.Shape;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ShapeRepository {

    private final Map<UUID, Shape> shapes = new HashMap<>();

    public Shape getById(UUID id) {
        final var shape = shapes.get(id);
        if (shape == null) {
            throw new IllegalStateException("Shape not found");
        }
        return shapes.get(id);
    }

    public Shape save(Shape shape) {
        this.shapes.put(shape.id(), shape);
        return shape;
    }
}

