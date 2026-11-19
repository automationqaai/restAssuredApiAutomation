package com.automationqaai.api.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

  public RequestSpecification createRequestSpec(String baseUrl) {

    RequestSpecBuilder requestBuilder = new RequestSpecBuilder();

    requestBuilder.setBaseUri(baseUrl);
    requestBuilder.setAccept("application/json");
    requestBuilder.setContentType("application/json");

    return requestBuilder.build();
  }
}
