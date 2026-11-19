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

public class LoginAPITest {

  @Test
  public void userLogin() {

    String userEmail = "tUI1795079063680@example.com";
    String password = "nobody@26";

    Map<String, String> reqBody = new HashMap<>();
    reqBody.put("email", userEmail);
    reqBody.put("password", password);

    ConfigReader configReader = new ConfigReader();
    String baseUrl = configReader.getBaseUrl();

    RequestSpecFactory reqFactory = new RequestSpecFactory();
    RequestSpecification reqSpec = reqFactory.createRequestSpec(baseUrl);

    Response response = given().spec(reqSpec).body(reqBody).when().post("/auth/login");

    assertEquals(response.statusCode(), 200, "Login failed: " + response.asString());

    JsonPath responseJsonPath = response.jsonPath();
    assertTrue(responseJsonPath.getBoolean("success"));
    String token = responseJsonPath.getString("token");
    assertNotNull(token, "Authentication Token should not be null");
    assertFalse(token.isBlank(), "Authentication Token should not be an empty string");
    Object userId = responseJsonPath.get("user.id");
    assertNotNull(userId);
    assertTrue(userId instanceof Number, "User ID should be a number");
    assertEquals(responseJsonPath.get("user.email"), userEmail);
  }
}
