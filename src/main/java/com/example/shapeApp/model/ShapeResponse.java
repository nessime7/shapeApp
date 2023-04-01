package com.example.shapeApp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShapeResponse {

    private UUID id;
    private double area;
    private double perimeter;
    private String type;
    private Optional<Double> width;
    private Optional<Double> height;
    private Optional<Double> radius;
    private List<Link> links;
}