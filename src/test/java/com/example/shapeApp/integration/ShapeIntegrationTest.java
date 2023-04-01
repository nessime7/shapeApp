package com.example.shapeApp.integration;

import com.example.shapeApp.ShapeApplication;
import com.example.shapeApp.TestUtils;
import com.example.shapeApp.model.Circle;
import com.example.shapeApp.model.Rectangle;
import com.example.shapeApp.model.ShapeType;
import com.example.shapeApp.repository.ShapeRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {ShapeApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShapeIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "shape";

    @Autowired
    private ShapeRepository shapeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        shapeRepository.save(new Circle(UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), ShapeType.CIRCLE, 10.0, 62.832, 314.16));
        shapeRepository.save(new Rectangle(UUID.fromString("5fd82e4e-c0ae-4771-a9d5-e18e3df32d65"), ShapeType.RECTANGLE, 10.0, 10.0, 40.0, 100.0));
    }

    @Test
    void should_add_new_shape_when_circle() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-circle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("type", equalTo("CIRCLE"))
                .body("radius", equalTo(100.0F))
                .body("perimeter", equalTo(628.3185307179587F))
                .body("area", equalTo(31415.926535897932F));
    }

    @Test
    void should_add_new_shape_when_rectangle() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-rectangle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("type", equalTo("RECTANGLE"))
                .body("width", equalTo(100.0F))
                .body("height", equalTo(100.0F))
                .body("perimeter", equalTo(400.0F))
                .body("area", equalTo(10000.0F));
    }


    @Test
    void should_get_perimeter_when_circle() {
        var id = "fc35ad28-91fc-449b-af3e-918417266f9d";
        given()
                .when().get(String.format("api/v1/shapes/%s/perimeter", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("perimeter", equalTo(62.832F));
    }

    @Test
    void should_get_perimeter_when_rectangle() {
        var id = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
        given()
                .when().get(String.format("api/v1/shapes/%s/perimeter", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("perimeter", equalTo(40.0F));
    }

    @Test
    void should_get_area_when_circle() {
        var id = "fc35ad28-91fc-449b-af3e-918417266f9d";
        given()
                .when().get(String.format("api/v1/shapes/%s/area", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("area", equalTo(314.16F));
    }

    @Test
    void should_get_area_when_rectangle() {
        var id = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
        given()
                .when().get(String.format("api/v1/shapes/%s/area", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("area", equalTo(100.0F));
    }

}