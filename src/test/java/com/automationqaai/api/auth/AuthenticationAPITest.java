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

public class AuthenticationAPITest {

  @Test
  public void authenticateMe() {

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

    Response response1 =
        given().spec(reqSpec).header("Authorization", "Bearer " + token).when().get("/auth/me");

    assertEquals(
        response1.statusCode(), 200, "Authentication check failed : " + response1.asString());
    JsonPath responseJsonPath2 = response1.jsonPath();
    assertTrue(responseJsonPath2.getBoolean("success"));
    assertEquals(responseJsonPath2.get("user.userId"), userId, "User id mismatched.");
    assertEquals(responseJsonPath2.getString("user.email"), userEmail, "User Email mismatched.");
  }
}
