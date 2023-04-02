package com.example.shapes.repository;

import com.example.shapes.model.Circle;
import com.example.shapes.model.Rectangle;
import com.example.shapes.model.Shape;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, UUID> {

    @Query("from Circle")
    Page<Circle> findCircles(Pageable pageable);

    @Query("from Rectangle")
    Page<Rectangle> findRectangles(Pageable pageable);

}

