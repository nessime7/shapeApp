package com.example.shapes.controller;

import com.example.shapes.exception.InvalidParametersException;
import com.example.shapes.exception.UnknownShapeException;
import com.example.shapes.model.ShapeType;
import com.example.shapes.model.dto.ShapeRequest;
import org.springframework.stereotype.Component;

@Component
public class ShapeRequestValidator {

    public void validate(ShapeRequest shapeRequest) {
        ShapeType type = validateShapeType(shapeRequest.type());
        switch (type) {
            case CIRCLE -> validateCircleParams(shapeRequest);
            case RECTANGLE -> validateRectangleParams(shapeRequest);
        }
    }

    public ShapeType validateShapeType(String requestType) {
        ShapeType type;
        try {
            type = ShapeType.valueOf(requestType);
        } catch (IllegalArgumentException e) {
            throw new UnknownShapeException();
        }
        return type;
    }

    private void validateRectangleParams(ShapeRequest shapeRequest) {
        if (shapeRequest.parameters().getHeight().filter(h -> h > 0).isEmpty() ||
                shapeRequest.parameters().getWidth().filter(w -> w > 0).isEmpty()) {
            throw new InvalidParametersException();
        }
    }

    private void validateCircleParams(ShapeRequest shapeRequest) {
        if (shapeRequest.parameters().getRadius().filter(r -> r > 0).isEmpty()) {
            throw new InvalidParametersException();
        }
    }
}
