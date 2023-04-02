package com.example.shapes.service;

import com.example.shapes.model.Circle;
import com.example.shapes.model.Rectangle;
import com.example.shapes.model.Shape;
import com.example.shapes.model.ShapeType;
import com.example.shapes.model.dto.ShapeParametersRequest;
import com.example.shapes.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;

    @Autowired
    public ShapeService(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    public Shape addShape(ShapeType type, ShapeParametersRequest parameters) {
        final var id = randomUUID();
        return switch (type) {
            case CIRCLE -> createCircle(parameters.getRadius().get(), id);
            case RECTANGLE -> createRectangle(parameters.getHeight().get(), parameters.getWidth().get(), id);
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


    private Shape createRectangle(double width, double height, UUID id) {
        final var rectangleArea = width * height;
        final var rectanglePerimeter = 2 * (width + height);
        final var rectangle = new Rectangle(id, width, height, rectangleArea, rectanglePerimeter);
        return shapeRepository.save(rectangle);
    }

    private Shape createCircle(double radius, UUID id) {
        final var circleArea = Math.PI * radius * radius;
        final var circlePerimeter = 2 * Math.PI * radius;
        final var circle = new Circle(id, radius, circleArea, circlePerimeter);
        return shapeRepository.save(circle);
    }

    public Page<Shape> getShapesByType(ShapeType type, Pageable pageable) {
        return switch (type) {
            case CIRCLE -> shapeRepository.findCircles(pageable).map(circle -> (Shape) circle);
            case RECTANGLE -> shapeRepository.findRectangles(pageable).map(rectangle -> (Shape) rectangle);
        };
    }

    public Page<Shape> getShapes(Pageable pageable) {
        return shapeRepository.findAll(pageable);
    }

    public Shape getShape(UUID id) {
        return shapeRepository.getReferenceById(id);
    }
}