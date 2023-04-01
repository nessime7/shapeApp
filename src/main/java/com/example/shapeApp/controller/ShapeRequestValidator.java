package com.example.shapeApp.controller;

import com.example.shapeApp.exception.InvalidParametersException;
import com.example.shapeApp.exception.UnknownShapeException;
import com.example.shapeApp.model.*;
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
        if (shapeRequest.parameters().getHeight().isEmpty() || shapeRequest.parameters().getWidth().isEmpty()) {
            throw new InvalidParametersException();
        }
    }

    private void validateCircleParams(ShapeRequest shapeRequest) {
        if (shapeRequest.parameters().getRadius().isEmpty()) {
            throw new InvalidParametersException();
        }
    }
}
