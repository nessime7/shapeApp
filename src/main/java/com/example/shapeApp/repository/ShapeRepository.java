package com.example.shapeApp.repository;

import com.example.shapeApp.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, UUID> {
}

