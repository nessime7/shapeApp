package com.example.shapes.service;

import com.example.shapes.model.Circle;
import com.example.shapes.model.Rectangle;
import com.example.shapes.model.dto.ShapeParametersRequest;
import com.example.shapes.model.dto.ShapeRequest;
import com.example.shapes.repository.ShapeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.shapes.model.ShapeType.CIRCLE;
import static com.example.shapes.model.ShapeType.RECTANGLE;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;


class ShapeServiceTest {

    private final ShapeRepository shapeRepository = mock(ShapeRepository.class);
    private final ShapeService shapeService = new ShapeService(shapeRepository);

    @Test
    void should_add_circle_shape() {
        // given
        var request = new ShapeRequest("CIRCLE", new ShapeParametersRequest(Optional.of(10.0), empty(), empty()));
        var expectedShape = new Circle(randomUUID(), 5, 2 * Math.PI * 5, Math.PI * 5 * 5);
        given(shapeRepository.save(any(Circle.class))).willReturn(expectedShape);

        // when
        var response = shapeService.addShape(CIRCLE, request.parameters());

        // then
        assertThat(response).isEqualTo(expectedShape);
        then(shapeRepository).should().save(any(Circle.class));
        then(shapeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_add_rectangle_shape() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), Optional.of(3.0), Optional.of(4.0)));
        var expectedShape = new Rectangle(randomUUID(), 3, 4, 14, 12);
        given(shapeRepository.save(any(Rectangle.class))).willReturn(expectedShape);

        // when
        var response = shapeService.addShape(RECTANGLE, request.parameters());

        // then
        assertThat(response).isEqualTo(expectedShape);
        then(shapeRepository).should().save(any(Rectangle.class));
        then(shapeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_get_perimeter() {
        // given
        final var shapeId = randomUUID();
        final var expectedPerimeter = 2 * Math.PI * 5;
        final var circle = new Circle(shapeId, 5, Math.PI * 5 * 5, expectedPerimeter);
        given(shapeRepository.getReferenceById(shapeId)).willReturn(circle);

        // when
        final var actualPerimeter = shapeService.getShapePerimeter(shapeId);

        // then
        assertThat(actualPerimeter).isEqualTo(expectedPerimeter);
    }

    @Test
    void should_get_area() {
        // given
        final var shapeId = randomUUID();
        final var expectedArea = Math.PI * 5 * 5;
        final var circle = new Circle(shapeId, 5, expectedArea, 2 * Math.PI * 5);
        given(shapeRepository.getReferenceById(shapeId)).willReturn(circle);

        // when
        final var actualArea = shapeService.getShapeArea(shapeId);

        // then
        assertThat(actualArea).isEqualTo(expectedArea);
    }

    @Test
    void should_get_perimeter_when_shape_does_not_exist() {
        // given
        final var shapeId = randomUUID();
        given(shapeRepository.getReferenceById(shapeId)).willThrow(IllegalStateException.class);

        // then
        assertThrows(IllegalStateException.class, () -> shapeService.getShapePerimeter(shapeId));
    }

    @Test
    void should_get_shapes() {
        // given
        var pageable = Pageable.ofSize(10);
        var circle = new Circle(randomUUID(), 10.0, 314.16, 62.832);
        var rectangle = new Rectangle(randomUUID(), 10.0, 10.0, 100.0, 40.0);
        given(shapeRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(circle, rectangle), pageable, 2));

        // when
        var results = shapeService.getShapes(pageable);

        // then
        assertThat(results.getContent().size()).isEqualTo(2);
    }

    @Test
    void should_return_circles_shapes_when_type_is_circle() {
        // given
        var pageable = Pageable.ofSize(1);
        var circle = new Circle(randomUUID(), 10.0, 314.16, 62.832);
        given(shapeRepository.findCircles(pageable)).willReturn(new PageImpl<>(List.of(circle), pageable, 2));

        // when
        var results = shapeService.getShapesByType(CIRCLE, pageable);

        // then
        assertThat(results.getContent().size()).isEqualTo(1);
    }
}