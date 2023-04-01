package com.example.shapes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class PerimeterResponse extends CalculationResponse {
    public double perimeter;

    public PerimeterResponse(UUID id, List<Link> links, double perimeter) {
        super(id, links);
        this.perimeter = perimeter;
    }
}
