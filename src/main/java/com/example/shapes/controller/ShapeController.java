package com.example.shapes.controller;

import com.example.shapes.model.Circle;
import com.example.shapes.model.Rectangle;
import com.example.shapes.model.Shape;
import com.example.shapes.model.ShapeType;
import com.example.shapes.model.dto.AreaResponse;
import com.example.shapes.model.dto.PerimeterResponse;
import com.example.shapes.model.dto.ShapeRequest;
import com.example.shapes.model.dto.ShapeResponse;
import com.example.shapes.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;

@RestController
@RequestMapping("/api/v1")
public class ShapeController {

    private final ShapeService shapeService;
    private final ShapeRequestValidator requestValidator;

    @Autowired
    public ShapeController(ShapeService shapeService, ShapeRequestValidator validator) {
        this.shapeService = shapeService;
        this.requestValidator = validator;
    }

    @GetMapping("/shapes")
    public ResponseEntity<Page<ShapeResponse>> getShapes(@RequestParam Optional<String> type, Pageable pageable) {
        final var shapes = type
                .map(t -> getShapesByType(pageable, t))
                .orElseGet(() -> shapeService.getShapes(pageable));
        return ResponseEntity.ok(shapes.map(shape -> shapeWithMetadata(shape, createAreaAndPerimeterLinks(shape))));
    }

    @GetMapping("/shape/{id}")
    public ResponseEntity<ShapeResponse> getShape(@PathVariable UUID id) {
        final var shape = shapeService.getShape(id);
        return ResponseEntity.ok(shapeWithMetadata(shape, createAreaAndPerimeterLinks(shape)));
    }

    @PostMapping("/shapes")
    public ResponseEntity<ShapeResponse> addShape(@RequestBody ShapeRequest shapeRequest) {
        requestValidator.validate(shapeRequest);
        final var type = ShapeType.valueOf(shapeRequest.type());
        final var shape = shapeService.addShape(type, shapeRequest.parameters());
        return ResponseEntity.status(201)
                .body(shapeWithMetadata(shape, createAreaAndPerimeterLinks(shape)));
    }

    @GetMapping("/shapes/{id}/perimeter")
    public ResponseEntity<PerimeterResponse> getPerimeter(@PathVariable UUID id) {
        final var perimeter = shapeService.getShapePerimeter(id);
        return ResponseEntity.ok(new PerimeterResponse(id, createDetailsLinks(id), perimeter));
    }

    @GetMapping("/shapes/{id}/area")
    public ResponseEntity<AreaResponse> getArea(@PathVariable UUID id) {
        final var area = shapeService.getShapeArea(id);
        return ResponseEntity.ok(new AreaResponse(id, createDetailsLinks(id), area));
    }

    private Page<Shape> getShapesByType(Pageable pageable, String t) {
        requestValidator.validateShapeType(t);
        return shapeService.getShapesByType(ShapeType.valueOf(t), pageable);
    }

    public ShapeResponse shapeWithMetadata(Shape shape, List<Link> links) {
        if (shape instanceof Circle) {
            return new ShapeResponse(shape.getId(),
                    shape.getArea(),
                    shape.getPerimeter(),
                    ShapeType.CIRCLE.name(),
                    empty(),
                    empty(),
                    Optional.of((((Circle) shape).getRadius())),
                    links);
        } else if (shape instanceof Rectangle) {
            return new ShapeResponse(shape.getId(),
                    shape.getArea(),
                    shape.getPerimeter(),
                    ShapeType.RECTANGLE.name(),
                    Optional.of((((Rectangle) shape).getWidth())),
                    Optional.of((((Rectangle) shape).getHeight())),
                    empty(),
                    links
            );
        }
        throw new IllegalStateException("Processing error, program shouldn't reach this line");
    }

    private List<Link> createAreaAndPerimeterLinks(Shape shape) {
        final var perimeterLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getPerimeter(shape.getId())).withRel("calculate-perimeter");
        final var areaLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getArea(shape.getId())).withRel("calculate-area");
        return List.of(perimeterLink, areaLink);
    }

    private List<Link> createDetailsLinks(UUID id) {
        final var detailsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getShape(id)).withRel("shape-details");
        return List.of(detailsLink);
    }
}
