package com.example.shapes.controller;

import com.example.shapes.exception.InvalidParametersException;
import com.example.shapes.exception.UnknownShapeException;
import com.example.shapes.model.dto.ExceptionMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UnknownShapeException.class})
    public ResponseEntity<ExceptionMessageResponse> unknownShapeException(
            Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ExceptionMessageResponse("UNKNOWN_TYPE"));
    }

    @ExceptionHandler({InvalidParametersException.class})
    public ResponseEntity<ExceptionMessageResponse> invalidParametersException(
            Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ExceptionMessageResponse("INVALID_PARAMETERS"));

    }
}
