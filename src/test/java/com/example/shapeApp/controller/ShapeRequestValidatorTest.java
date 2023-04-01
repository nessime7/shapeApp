package com.example.shapeApp.controller;

import com.example.shapeApp.exception.InvalidParametersException;
import com.example.shapeApp.exception.UnknownShapeException;
import com.example.shapeApp.model.ShapeParametersRequest;
import com.example.shapeApp.model.ShapeRequest;
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
    void should_throw_when_no_height_in_rectangle_request() {
        // given
        var request = new ShapeRequest("RECTANGLE", new ShapeParametersRequest(empty(), Optional.of(10.0), empty()));

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
}