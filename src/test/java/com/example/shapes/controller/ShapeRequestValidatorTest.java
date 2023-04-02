package com.example.shapes.controller;

import com.example.shapes.exception.InvalidParametersException;
import com.example.shapes.exception.UnknownShapeException;
import com.example.shapes.model.dto.ShapeParametersRequest;
import com.example.shapes.model.dto.ShapeRequest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShapeRequestValidatorTest {

    private final ShapeRequestValidator validator = new ShapeRequestValidator();

    @Test
    void should_validate_correct_request() {
        // given
        var request = new ShapeRequest("CIRCLE", new ShapeParametersRequest(Optional.of(10.0), empty(), empty()));

        // then
        assertThatCode(() -> validator.validate(request)).doesNotThrowAnyException();
    }

    @Test
    void should_throw_when_unknown_shape() {
        // given
        var request = new ShapeRequest("TRIANGLE", new ShapeParametersRequest(empty(), empty(), empty()));

        // then
        assertThrows(UnknownShapeException.class, () -> validator.validate(request));
    }

    @Test
    void should_throw_when_no_radius_in_circle_request() {
        // given
        var request = new ShapeRequest("CIRCLE", new ShapeParametersRequest(empty(), empty(), empty()));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }

    @Test
    void should_throw_when_minus_radius_in_circle_request() {
        // given
        var request = new ShapeRequest("CIRCLE", new ShapeParametersRequest(Optional.of((-10.0)), empty(), empty()));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }


    @Test
    void should_throw_when_no_height_in_rectangle_request() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), Optional.of(10.0), empty()));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }

    @Test
    void should_throw_when_minus_height_in_rectangle_request() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), Optional.of(10.0), Optional.of((-10.0))));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }


    @Test
    void should_throw_when_no_height_and_width_in_rectangle_request() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), empty(), empty()));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }

    @Test
    void should_throw_when_minus_width_in_rectangle_request() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), Optional.of(-10.0), Optional.of((10.0))));

        // then
        assertThrows(InvalidParametersException.class, () -> validator.validate(request));
    }

    @Test
    void should_validate_type() {
        // given
        var circle = "CIRCLE";

        // then
        assertThatCode(() -> validator.validateShapeType(circle)).doesNotThrowAnyException();
    }
}