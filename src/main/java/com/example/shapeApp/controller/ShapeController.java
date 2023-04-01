package com.example.shapeApp.controller;

import com.example.shapeApp.model.*;
import com.example.shapeApp.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
mamy endpoint do dodawania figur geometrycznych
@POST /api/v1/shapes
body: { type: CIRCLE, parameters: {"radius": 10.0})
body: { type: RECTANGLE, parameters: {"width": 10.0, "height": 20.0})

w response:
201 created: { id, type, radius, links: {calculate-perimeter, calculate-area}}
201 created: { id, type, width, height, links: {calculate-perimeter, calculate-area}}

ale tez response moze byc:
400 bad request: { message: "UNKNOWN_TYPE" | "INVALID_PARAMETERS" }
jesli np: poda sie jakis nieznany typ, lub zle parametry dla typu danej figury

jesli chodzi o linki w odpowiedzi to one maja uruchamiac jakis dodatkowy endpoint do
wyliczenia obwodu albo pola danej figury
propozycje kontraktu daj sama, ale body musi byc takie:

200OK
body: { shapeId: ..., area: ... links: {shape-details}}
body: { shapeId: ..., perimeter: ..., links: {shape-details}}

czy moga byc inne body? jakies wyjatki?

@GET /api/v1/shapes?type=*
- jesli type nie podano - to zwracamy wszystkie figury
- jesli type podano to zwracamy figury tylko danego typu
- apply pagination
zwraca page czegos co extends ShapeDto

calosc pokryc testami jednostkowymi i integracyjnymi.
 */
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

    @PostMapping("/shapes")
    public ResponseEntity<EntityModel<Shape>> addShape(@RequestBody ShapeRequest shapeRequest) {
        requestValidator.validate(shapeRequest);
        final var type = ShapeType.valueOf(shapeRequest.type());
        final var shape = shapeService.addShape(type, shapeRequest.parameters());
        final var perimeterLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getArea(shape.id())).withRel("calculate-perimeter");
        final var areaLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShapeController.class).getPerimeter(shape.id())).withRel("calculate-area");
        return ResponseEntity.status(201)
                .body(EntityModel.of(shape, perimeterLink, areaLink));
    }

    @GetMapping("/shapes/{id}/perimeter")
    public ResponseEntity<PerimeterResponse> getPerimeter(@PathVariable UUID id) {
        final var perimeter = shapeService.getShapePerimeter(id);
        return ResponseEntity.ok(new PerimeterResponse(perimeter));
    }

    @GetMapping("/shapes/{id}/area")
    public ResponseEntity<AreaResponse> getArea(@PathVariable UUID id) {
        final var area = shapeService.getShapeArea(id);
        return ResponseEntity.ok(new AreaResponse(area));
    }
}
