package com.example.shapeApp.controller;


import com.example.shapeApp.exception.InvalidParametersException;
import com.example.shapeApp.exception.UnknownShapeException;
import com.example.shapeApp.model.ExceptionMessageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

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
