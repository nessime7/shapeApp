package com.example.shapeApp.controller;

import com.example.shapeApp.model.Shape;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.service.ShapeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.shapeApp.model.ShapeType.CIRCLE;
import static com.example.shapeApp.model.ShapeType.RECTANGLE;

@RestController
@RequestMapping("/api/v1")
public class ShapeController {

    private final ShapeService shapeService;

    @Autowired
    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping("/shapes")
    public EntityModel<Shape> addShape(@RequestBody ShapeRequest shapeRequest) {
        final var shape = shapeService.addShape(shapeRequest);
        final var perimeterLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getArea(shape.id())).withRel("calculate-perimeter");
        final var areaLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getPerimeter(shape.id())).withRel("calculate-area");
        return EntityModel.of(shape, perimeterLink, areaLink);
    }

    @GetMapping("/shapes/{id}/perimeter")
    public ResponseEntity<JSONObject> getPerimeter(@PathVariable UUID id) {
        final var perimeter = shapeService.getShapePerimeter(id);
        final var response = new JSONObject().put("perimeter", perimeter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/shapes/{id}/area")
    public ResponseEntity<JSONObject> getArea(@PathVariable UUID id) {
        final var area = shapeService.getShapeArea(id);
        final var response = new JSONObject().put("area", area);
        return ResponseEntity.ok(response);
    }
}
