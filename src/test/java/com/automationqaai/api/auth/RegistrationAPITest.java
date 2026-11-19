package com.automationqaai.api.auth;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import com.automationqaai.api.config.ConfigReader;
import com.automationqaai.api.spec.RequestSpecFactory;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class RegistrationAPITest {

  @Test
  public void userRegistration() {

    String userName = "tUI" + System.currentTimeMillis() + "@example.com";
    String password = "nobody@26";

    Map<String, String> reqBody = new HashMap<>();
    reqBody.put("email", userName);
    reqBody.put("password", password);

    ConfigReader configReader = new ConfigReader();

    String baseUrl = configReader.getBaseUrl();

    RequestSpecFactory reqFactory = new RequestSpecFactory();
    RequestSpecification reqSpec = reqFactory.createRequestSpec(baseUrl);

    Response response = given().spec(reqSpec).body(reqBody).when().post("/auth/register");

    assertEquals(response.statusCode(), 201, "Registration failed: " + response.asString());

    JsonPath responseJsonPath = response.jsonPath();
    assertTrue(responseJsonPath.getBoolean("success"));
    assertNotNull(responseJsonPath.get("token"));
    assertNotNull(responseJsonPath.get("user.id"));
    assertEquals(responseJsonPath.get("user.email"), userName);

    response.getBody().prettyPrint();
  }
}
