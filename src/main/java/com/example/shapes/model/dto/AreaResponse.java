package com.example.shapes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class AreaResponse extends CalculationResponse {
    private double area;

    public AreaResponse(UUID id, List<Link> links, double area) {
        super(id, links);
        this.area = area;
    }
}
