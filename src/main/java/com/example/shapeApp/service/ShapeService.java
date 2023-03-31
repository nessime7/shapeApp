package com.example.shapeApp.service;

import com.example.shapeApp.controller.ShapeRequest;
import com.example.shapeApp.model.Circle;
import com.example.shapeApp.model.Rectangle;
import com.example.shapeApp.model.Shape;
import com.example.shapeApp.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.shapeApp.model.ShapeType.CIRCLE;
import static com.example.shapeApp.model.ShapeType.RECTANGLE;
import static java.util.UUID.randomUUID;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;

    @Autowired
    public ShapeService(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    public Shape addShape(ShapeRequest request) {
        final var id = randomUUID();
        return switch (request.type()) {
            case CIRCLE -> createCircle(request.parameters().getRadius().get(), id);
            case RECTANGLE -> createRectangle(request.parameters().getHeight().get(), request.parameters().getWidth().get(), id);
        };
    }

    public double getShapePerimeter(UUID id) {
        final var getShape = shapeRepository.getById(id);
        return getShape.perimeter();
    }

    public double getShapeArea(UUID id) {
        final var getShape = shapeRepository.getById(id);
        return getShape.area();
    }


    private Shape createRectangle(double width, double height, UUID id) {
        final var rectangleArea = width * height;
        final var rectanglePerimeter = 2 * (width + height);
        final var rectangle = new Rectangle(id, RECTANGLE, width, height, rectanglePerimeter, rectangleArea);
        return shapeRepository.save(rectangle);
    }

    private Shape createCircle(double radius, UUID id) {
        final var circleArea = Math.PI * radius * radius;
        final var circlePerimeter = 2 * Math.PI * radius;
        final var circle = new Circle(id, CIRCLE, radius, circlePerimeter, circleArea);
        return shapeRepository.save(circle);
    }
}