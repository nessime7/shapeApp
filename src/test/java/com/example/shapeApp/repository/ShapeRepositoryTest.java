package com.example.shapeApp.repository;

import com.example.shapeApp.model.Circle;
import com.example.shapeApp.model.ShapeType;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShapeRepositoryTest {

    private final ShapeRepository shapeRepository = new ShapeRepository();

    @Test
    void should_get_shape_when_exist() {
        // given
        var circle = new Circle(randomUUID(), ShapeType.CIRCLE, 10.0, 100.0, 100.0);
        shapeRepository.save(circle);

        // when
        var shape = shapeRepository.getById(circle.id());

        // then
        assertThat(shape).isEqualTo(circle);
    }


    @Test
    void should_throw_when_shape_does_not_exist() {
        // given
        var circle = new Circle(randomUUID(), ShapeType.CIRCLE, 10.0, 100.0, 100.0);

        // then
        assertThrows(IllegalStateException.class, () -> shapeRepository.getById(circle.id()));
    }
}