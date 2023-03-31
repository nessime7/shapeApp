package com.example.shapeApp.integration;

import com.example.shapeApp.ShapeApplication;
import com.example.shapeApp.TestUtils;
import com.example.shapeApp.model.Circle;
import com.example.shapeApp.model.Rectangle;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.repository.ShapeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {ShapeApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShapeIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "shape";

    @Autowired
    ShapeRepository shapeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        shapeRepository.save(new Circle(UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), ShapeType.CIRCLE, 10.0, 62.832, 314.16));
        shapeRepository.save(new Rectangle(UUID.fromString("5fd82e4e-c0ae-4771-a9d5-e18e3df32d65"), ShapeType.RECTANGLE, 10.0, 10.0, 40.0, 100.0));
    }

    @Test
    void should() throws IOException {
       Response response =  given().contentType(ContentType.JSON).body(TestUtils.getRequestBodyFromFile("request/post-circle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        String jsonResponse = response.getBody().asString();
        Map<String, Object> responseMap = new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {});

        assertThat(responseMap.get("type"), equalTo("CIRCLE"));
        assertThat(responseMap.get("radius"), equalTo(100.0));
        assertThat(responseMap.get("perimeter"), equalTo(628.3185307179587));
        assertThat(responseMap.get("area"), equalTo(31415.926535897932));
    }



}
