package com.example.shapes.integration;

import com.example.shapes.ShapeApplication;
import com.example.shapes.TestUtils;
import com.example.shapes.model.Circle;
import com.example.shapes.model.Rectangle;
import com.example.shapes.repository.ShapeRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = {ShapeApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShapeIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "shape";
    private static String RECTANGLE_ID = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
    private static String CIRCLE_ID = "fc35ad28-91fc-449b-af3e-918417266f9d";

    @Autowired
    private ShapeRepository shapeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        shapeRepository.save(new Circle(UUID.fromString(CIRCLE_ID), 10.0, 314.16, 62.832));
        shapeRepository.save(new Rectangle(UUID.fromString(RECTANGLE_ID), 10.0, 10.0, 100.0, 40.0));
    }

    @AfterEach
    void cleanUp() {
        shapeRepository.deleteAll();
    }

    @Test
    void should_get_circle_shapes_only() {
        given().contentType(JSON)
                .when().get("/api/v1/shapes?type=CIRCLE")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("content[0].id", equalTo(CIRCLE_ID))
                .body("content[0].type", equalTo("CIRCLE"))
                .body("content[0].radius", equalTo(10.0F))
                .body("content[0].perimeter", equalTo(62.832F))
                .body("content[0].area", equalTo(314.16F))
                .body("content[0].links[0].rel", is("calculate-perimeter"))
                .body("content[0].links[0].href", stringContainsInOrder("/api/v1/shapes", "/perimeter"))
                .body("content[0].links[1].rel", is("calculate-area"))
                .body("content[0].links[1].href", stringContainsInOrder("/api/v1/shapes", "/area"))
                .body("content.size()", is(1));
    }

    @Test
    void should_get_all_shapes() {
        given().contentType(JSON)
                .when().get("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("content[0].id", equalTo(CIRCLE_ID))
                .body("content[0].type", equalTo("CIRCLE"))
                .body("content[0].radius", equalTo(10.0F))
                .body("content[0].perimeter", equalTo(62.832F))
                .body("content[0].area", equalTo(314.16F))
                .body("content[0].links[0].rel", is("calculate-perimeter"))
                .body("content[0].links[0].href", stringContainsInOrder("/api/v1/shapes", "/perimeter"))
                .body("content[0].links[1].rel", is("calculate-area"))
                .body("content[0].links[1].href", stringContainsInOrder("/api/v1/shapes", "/area"))
                .body("content[1].id", equalTo(RECTANGLE_ID))
                .body("content[1].type", equalTo("RECTANGLE"))
                .body("content[1].width", equalTo(10.0F))
                .body("content[1].height", equalTo(10.0F))
                .body("content[1].perimeter", equalTo(40.0F))
                .body("content[1].area", equalTo(100.0F))
                .body("content[1].links[0].rel", is("calculate-perimeter"))
                .body("content[1].links[0].href", stringContainsInOrder("/api/v1/shapes", "/perimeter"))
                .body("content[1].links[1].rel", is("calculate-area"))
                .body("content[1].links[1].href", stringContainsInOrder("/api/v1/shapes", "/area"))
                .body("content.size()", is(2));
    }


    @Test
    void should_get_one_shape_when_pagination_used() {
        // given
        shapeRepository.save(new Circle(UUID.randomUUID(), 10.0, 314.16, 62.832));
        shapeRepository.save(new Circle(UUID.randomUUID(), 10.0, 314.16, 62.832));

        // when
        given().contentType(JSON)
                .when().get("/api/v1/shapes?page=3&size=1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("content.size()", is(1));
    }

    @Test
    void should_add_new_shape_when_circle() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-circle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("type", equalTo("CIRCLE"))
                .body("radius", equalTo(100.0F))
                .body("perimeter", equalTo(628.3185307179587F))
                .body("area", equalTo(31415.926535897932F))
                .body("links[0].rel", is("calculate-perimeter"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shapes", "/perimeter"))
                .body("links[1].rel", is("calculate-area"))
                .body("links[1].href", stringContainsInOrder("/api/v1/shapes", "/area"));
    }

    @Test
    void should_add_new_shape_when_rectangle() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-rectangle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("type", equalTo("RECTANGLE"))
                .body("width", equalTo(100.0F))
                .body("height", equalTo(100.0F))
                .body("perimeter", equalTo(400.0F))
                .body("area", equalTo(10000.0F))
                .body("links[0].rel", is("calculate-perimeter"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shapes", "/perimeter"))
                .body("links[1].rel", is("calculate-area"))
                .body("links[1].href", stringContainsInOrder("/api/v1/shapes", "/area"));
    }


    @Test
    void should_return_invalid_parameters_exception_message_when_rectangle_has_no_height() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-invalid-params-rectangle-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("INVALID_PARAMETERS"));
    }

    @Test
    void should_return_unknown_type_exception_message_when_invalid_shape() throws IOException {
        given().contentType(JSON)
                .body(TestUtils.getRequestBodyFromFile("request/post-invalid-shape-request.json", CONTEXT))
                .when().post("/api/v1/shapes")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("UNKNOWN_TYPE"));
    }

    @Test
    void should_get_perimeter_when_circle() {
        var id = CIRCLE_ID;
        given()
                .when().get(String.format("api/v1/shapes/%s/perimeter", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", equalTo(CIRCLE_ID))
                .body("perimeter", equalTo(62.832F))
                .body("links[0].rel", is("shape-details"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shape/"));
    }

    @Test
    void should_get_perimeter_when_rectangle() {
        var id = RECTANGLE_ID;
        given()
                .when().get(String.format("api/v1/shapes/%s/perimeter", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", equalTo(RECTANGLE_ID))
                .body("perimeter", equalTo(40.0F))
                .body("links[0].rel", is("shape-details"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shape/"));
    }

    @Test
    void should_get_area_when_circle() {
        var id = CIRCLE_ID;
        given()
                .when().get(String.format("api/v1/shapes/%s/area", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", equalTo(CIRCLE_ID))
                .body("area", equalTo(314.16F))
                .body("links[0].rel", is("shape-details"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shape/"));
    }

    @Test
    void should_get_area_when_rectangle() {
        var id = RECTANGLE_ID;
        given()
                .when().get(String.format("api/v1/shapes/%s/area", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", equalTo(RECTANGLE_ID))
                .body("area", equalTo(100.0F))
                .body("links[0].rel", is("shape-details"))
                .body("links[0].href", stringContainsInOrder("/api/v1/shape/"));
    }
}