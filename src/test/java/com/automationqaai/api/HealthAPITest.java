package com.automationqaai.api;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import com.automationqaai.api.config.ConfigReader;
import com.automationqaai.api.spec.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class HealthAPITest {

  @Test
  public void apiHealthCheck() {

    ConfigReader configReader = new ConfigReader();

    String baseUrl = configReader.getBaseUrl();

    RequestSpecFactory reqFactory = new RequestSpecFactory();
    RequestSpecification reqSpec = reqFactory.createRequestSpec(baseUrl);

    Response response = given().spec(reqSpec).when().get("/health");

    if (response.statusCode() != 200) {
      System.err.println("Health API validation failed!");
      System.err.println("Status Code: " + response.statusCode());
      System.err.println("Response Body: " + response.getBody().asPrettyString());
    }

    assertEquals(response.statusCode(), 200, "Expected HTTP status code 200");

    assertFalse(response.getBody().asString().isBlank(), "Response body should not be empty");
  }
}
