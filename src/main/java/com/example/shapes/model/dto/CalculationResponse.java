package com.example.shapes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResponse {

    private UUID id;
    private List<Link> links;
}
