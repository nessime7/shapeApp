package com.example.shapeApp.service;

import com.example.shapeApp.model.Circle;
import com.example.shapeApp.model.Rectangle;
import com.example.shapeApp.model.ShapeParametersRequest;
import com.example.shapeApp.model.ShapeRequest;
import com.example.shapeApp.repository.ShapeRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.shapeApp.model.ShapeType.CIRCLE;
import static com.example.shapeApp.model.ShapeType.RECTANGLE;
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
    public void should_get_perimeter_when_shape_does_not_exist() {
        // given
        final var shapeId = randomUUID();
        given(shapeRepository.getReferenceById(shapeId)).willThrow(IllegalStateException.class);

        // then
        assertThrows(IllegalStateException.class, () -> shapeService.getShapePerimeter(shapeId));
    }
}