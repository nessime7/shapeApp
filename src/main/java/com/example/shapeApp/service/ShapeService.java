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
        return switch (type) {
            case CIRCLE -> createCircle(parameters, id);
            case RECTANGLE -> createRectangle(parameters, id);
        };
    }

    public double getShapePerimeter(UUID id) {
        final var getShape = shapeRepository.getReferenceById(id);
        return getShape.getPerimeter();
    }

    public double getShapeArea(UUID id) {
        final var getShape = shapeRepository.getReferenceById(id);
        return getShape.getArea();
    }


    private Shape createRectangle(JSONObject parameters, UUID id) {
        final var width = parameters.getDouble("width");
        final var height = parameters.getDouble("height");
        final var rectangleArea = width * height;
        final var rectanglePerimeter = 2 * (width + height);
        final var shape = new Shape(id, ShapeType.RECTANGLE, parameters, rectanglePerimeter, rectangleArea);
        return shapeRepository.save(shape);
    }

    private Shape createCircle(JSONObject parameters, UUID id) {
        final var radius = parameters.getDouble("radius");
        final var circleArea = Math.PI * radius * radius;
        final var circlePerimeter = 2 * Math.PI * radius;
        final var shape = new Shape(id, ShapeType.CIRCLE, parameters, circlePerimeter, circleArea);
        return shapeRepository.save(shape);
    }
}