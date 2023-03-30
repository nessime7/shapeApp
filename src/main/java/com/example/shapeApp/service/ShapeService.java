package com.example.shapeApp.service;

import com.example.shapeApp.model.Shape;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.repository.ShapeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Shape addShape(ShapeType type, JSONObject parameters) {
        // add logic to calculate area and perimeter for every new shape and add it to repo
        return shapeRepository.save(new Shape(randomUUID(), type, parameters));
    }

    public double getPerimeter(UUID id) {
        return null;
    }

    public double getArea(UUID id) {
        return null;
    }
}

//    public ShapeRequest createShape(ShapeRequest shapeRequest) throws InvalidShapeParametersException {
//        ShapeType type = shapeRequest.getType();
//        Map<String, Double> parameters = shapeRequest.getParameters();
//        switch (type) {
//            case CIRCLE:
//                Double radius = parameters.get("radius");
//                if (radius == null || radius <= 0) {
//                    throw new InvalidShapeParametersException("Invalid radius: " + radius);
//                }
//                Double circleArea = Math.PI * radius * radius;
//                Double circlePerimeter = 2 * Math.PI * radius;
//                shapeRequest.setArea(circleArea);
//                shapeRequest.setPerimeter(circlePerimeter);
//                break;
//            case RECTANGLE:
//                Double width = parameters.get("width");
//                Double height = parameters.get("height");
//                if (width == null || width <= 0) {
//                    throw new InvalidShapeParametersException("Invalid width: " + width);
//                }
//                if (height == null || height <= 0) {
//                    throw new InvalidShapeParametersException("Invalid height: " + height);
//                }
//                Double rectangleArea = width * height;
//                Double rectanglePerimeter = 2 * (width + height);
//                shapeRequest.setArea(rectangleArea);
//                shapeRequest.setPerimeter(rectanglePerimeter);
//                break;
//        }
//        return shapeRequest;
//    }
//
//    public List<Shape> getAllShapes() {
//        return shapeRepository.findAll();
//    }

