package com.automationqaai.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

  private final Properties properties;
  private final String environment;

  public ConfigReader() {

    properties = new Properties();

    environment = System.getProperty("env", "qa");

    String configFile = "config/" + environment + ".properties";

    try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {

      if (input == null) {
        throw new IllegalStateException("Configuration file not found: " + configFile);
      }

      properties.load(input);

    } catch (IOException e) {
      throw new IllegalStateException("Failed to load configuration: " + configFile, e);
    }

    String baseUrl = properties.getProperty("base.url");

    if (baseUrl == null || baseUrl.isBlank()) {
      throw new IllegalStateException("Missing or empty base.url in " + configFile);
    }
  }

  public String getBaseUrl() {
    return properties.getProperty("base.url");
  }

  public String getEnvironment() {
    return environment;
  }
}
