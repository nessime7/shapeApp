package com.example.shapeApp.service;

import com.example.shapeApp.model.Shape;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.repository.ShapeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;

    @Autowired
    public ShapeService(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    public Shape addShape(ShapeType type, JSONObject parameters) {
        final var id = UUID.randomUUID();
        Shape shape = null;
        switch (type) {
            case CIRCLE -> {
                final var radius = parameters.getDouble("radius");
                final var circleArea = Math.PI * radius * radius;
                final var circlePerimeter = 2 * Math.PI * radius;
                shape = new Shape(id, ShapeType.CIRCLE, parameters, getPerimeter(id), getArea(id));
                shape.setPerimeter(circlePerimeter);
                shape.setArea(circleArea);
                return shapeRepository.save(shape);
            }
            case RECTANGLE -> {
                final var width = parameters.getDouble("width");
                final var height = parameters.getDouble("height");
                final var rectangleArea = width * height;
                final var rectanglePerimeter = 2 * (width + height);
                shape = new Shape(id, ShapeType.RECTANGLE, parameters, getPerimeter(id), getArea(id));
                shape.setArea(rectangleArea);
                shape.setPerimeter(rectanglePerimeter);
                return shapeRepository.save(shape);
            }
        }
        return shapeRepository.save(shape);
    }

    public double getPerimeter(UUID id) {
        final var getShape = shapeRepository.findById(id);
        return getShape.get().getPerimeter();
    }

    public double getArea(UUID id) {
        final var getShape = shapeRepository.findById(id);
        return getShape.get().getArea();
    }

}