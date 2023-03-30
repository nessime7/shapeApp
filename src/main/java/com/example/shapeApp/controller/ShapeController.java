package com.example.shapeApp.controller;

import com.example.shapeApp.model.Shape;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.service.ShapeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ShapeController {

    private final ShapeService shapeService;

    @Autowired
    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping("/shapes")
    public ResponseEntity<JSONObject> addShape(@RequestBody JSONObject shapeRequest) {
        final var type = ShapeType.valueOf(shapeRequest.getString("type"));
        final var parameters = shapeRequest.getJSONObject("parameters");
        final var shape = shapeService.addShape(type, parameters);
        final var response = switch (shape.getType()) {
            case CIRCLE -> circleResponse(shape);
            case RECTANGLE -> rectangleResponse(shape);
        };

//        response.addLink("calculate-perimeter", "/api/v1/shapes/" + response.getId() + "/perimeter");
//        response.addLink("calculate-area", "/api/v1/shapes/" + response.getId() + "/area");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/shapes/{id}/perimeter")
    public ResponseEntity<JSONObject> getPerimeter(@PathVariable UUID id) {
        // add logic to handle perimeter, calculation should be done in service
        return new JSONObject().put("perimeter", perimeter);
    }

    @GetMapping("/shapes/{id}/area")
    public ResponseEntity<JSONObject> getPerimeter(@PathVariable UUID id) {
        // add logic to handle area, calculation should be done in service
        return new JSONObject().put("area", area);
    }

    private JSONObject circleResponse(Shape shape) {
        return new JSONObject().put("type", shape.getType())
                .put("radius", shape.getParameters().getDouble("radius"));
    }

    private JSONObject rectangleResponse(Shape shape) {
        return new JSONObject().put("type", shape.getType())
                .put("height", shape.getParameters().getDouble("height"))
                .put("width", shape.getParameters().getDouble("width"));
    }

//    @GetMapping("/{id}/perimeter")
//    public ResponseEntity<Double> calculatePerimeter(@PathVariable UUID id) {
//        double perimeter = shapeService.calculatePerimeter(id);
//        return ResponseEntity.ok(perimeter);
//    }
//
//    @GetMapping("/{id}/area")
//    public ResponseEntity<Double> calculateArea(@PathVariable UUID id) {
//        double area = shapeService.calculateArea(id);
//        return ResponseEntity.ok(area);
//    }
//
//    @GetMapping("/shapes?type=")
//    public List<ShapeResponse> getAllShapes() {
//        return map(shapeService.getAllShapes());
//    }

}
