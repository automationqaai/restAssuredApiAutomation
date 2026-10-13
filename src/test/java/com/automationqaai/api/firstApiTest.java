
package com.automationqaai.api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class firstApiTest {

    @Test
    public void shouldRetrievePostSuccessfully() {
        Response response =
                given()
                        .baseUri("https://jsonplaceholder.typicode.com")
                        .accept("application/json")
                        .when()
                        .get("/posts/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        assertEquals(response.jsonPath().getInt("id"), 1);
        assertNotNull(response.jsonPath().getString("title"));
    }
}
