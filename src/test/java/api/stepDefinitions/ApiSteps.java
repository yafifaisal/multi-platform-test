package api.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import api.helpers.ApiHelper;
import api.helpers.ContextStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ApiSteps {

    private String requestBody;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> queryParams = new HashMap<>();
    private Response response;
    private final String BASE_URL = "https://petstore.swagger.io/v2"; // ← change to your API

    @Given("set request body")
    public void setRequestBodyFromTable(DataTable table) {
        JSONObject root = new JSONObject();
        for (Map<String, String> row : table.asMaps()) {
            setNestedValue(root, row.get("key"), ApiHelper.resolvePlaceholders(row.get("value")));
        }
        this.requestBody = root.toString(2);
    }

    @Given("set request body using {string}")
    public void setRequestBodyUsingTemplate(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/api/requestBodies/" + fileName)));
        JSONObject root = new JSONObject(ApiHelper.resolvePlaceholders(json));
        this.requestBody = root.toString(2);
    }

    @Given("set request body using {string} with values")
    public void setRequestBodyUsingTemplate(String fileName, DataTable table) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/api/requestBodies/" + fileName)));
        JSONObject root = new JSONObject(ApiHelper.resolvePlaceholders(json));
        for (Map<String, String> row : table.asMaps()) {
            setNestedValue(root, row.get("key"), ApiHelper.resolvePlaceholders(row.get("value")));
        }
        this.requestBody = root.toString(2);
    }

    @Given("set request headers using {string}")
    public void setRequestHeaderUsingTemplate(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/api/requestHeaders/" + fileName)));
        JSONObject baseHeaders = new JSONObject(ApiHelper.resolvePlaceholders(json));
        for (String key : baseHeaders.keySet()) {
            headers.put(key, baseHeaders.getString(key));
        }
    }

    @Given("set request headers using {string} with values")
    public void setRequestHeadersFromFile(String fileName, DataTable table) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/api/requestHeaders/" + fileName)));
        JSONObject baseHeaders = new JSONObject(ApiHelper.resolvePlaceholders(json));
        for (String key : baseHeaders.keySet()) {
            headers.put(key, baseHeaders.getString(key));
        }
        for (Map<String, String> row : table.asMaps()) {
            headers.put(row.get("key"), ApiHelper.resolvePlaceholders(row.get("value")));
        }
    }

    @Given("set request headers")
    public void setRequestHeaders(DataTable table) throws IOException {
        for (Map<String, String> row : table.asMaps()) {
            headers.put(row.get("key"), ApiHelper.resolvePlaceholders(row.get("value")));
        }
    }

    @Given("set query parameters")
    public void setQueryParams(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            queryParams.put(row.get("key"), ApiHelper.resolvePlaceholders(row.get("value")));
        }
    }

    @When("I send a {string} request to {string}")
    public void sendRequest(String method, String path) {
        response = ApiHelper.sendRequest(method, BASE_URL, path, headers, requestBody, queryParams);
        ContextStore.save("lastResponse", response);
    }

    @Then("match response.{word} {word} {string}")
    public void matchResponseField(String path, String operator, String expectedValue) {
        Object actual = response.jsonPath().get(path);
        switch (operator) {
            case "==":
                Assert.assertEquals(String.valueOf(actual), expectedValue);
                break;
            case "!=":
                Assert.assertNotEquals(String.valueOf(actual), expectedValue);
                break;
            case ">=":
                Assert.assertTrue(Double.parseDouble(String.valueOf(actual)) >= Double.parseDouble(expectedValue));
                break;
            case "<=":
                Assert.assertTrue(Double.parseDouble(String.valueOf(actual)) <= Double.parseDouble(expectedValue));
                break;
            case "contains":
                Assert.assertTrue(String.valueOf(actual).contains(expectedValue));
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    @Then("save response.{string} as {string}")
    public void saveField(String jsonPath, String variableName) {
        Object value = response.jsonPath().get(jsonPath);
        if (value != null) {
            ContextStore.save(variableName, value.toString());
        } else {
            System.err.println("❌ Failed to save value from response for path: " + jsonPath);
        }
    }

    @Then("response should match schema {string}")
    public void validateSchema(String fileName) throws Exception {
        String schemaText = new String(Files.readAllBytes(Paths.get("src/test/resources/api/schemas/" + fileName)));
        org.everit.json.schema.Schema schema = org.everit.json.schema.loader.SchemaLoader
                .load(new JSONObject(schemaText));
        String responseBody = response.getBody().asString();
        try {
            if (responseBody.trim().startsWith("[")) {
                schema.validate(new JSONArray(responseBody));
            } else {
                schema.validate(new JSONObject(responseBody));
            }
        } catch (ValidationException e) {
            System.err.println("❌ Schema validation failed:");
            e.getAllMessages().forEach(msg -> System.err.println("  - " + msg));
            throw e; // Rethrow to fail the test
        }
    }

    @Then("response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code: " + expectedStatusCode + ", but got: " + actualStatusCode);
    }

    @Then("all values of {string} in response array should equal {string}")
    public void allValuesInResponseShouldEqual(String field, String expectedValue) {
        List<Map<String, Object>> items = response.jsonPath().getList("$");

        if (items == null || items.isEmpty()) {
            Assert.fail("Response is empty or not a JSON array");
        }

        for (int i = 0; i < items.size(); i++) {
            Object actual = items.get(i).get(field);
            Assert.assertEquals(
                    String.valueOf(actual),
                    expectedValue,
                    String.format("❌ Mismatch at index %d: expected [%s] but got [%s]", i, expectedValue, actual));
        }

    }

    private void setNestedValue(JSONObject root, String path, Object value) {
        String[] parts = path.split("\\.");
        Object current = root;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.matches(".*\\[\\d+]")) {
                String key = part.substring(0, part.indexOf("["));
                int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));
                JSONArray array = ((JSONObject) current).optJSONArray(key);
                if (array == null) {
                    array = new JSONArray();
                    ((JSONObject) current).put(key, array);
                }
                while (array.length() <= index) {
                    array.put(new JSONObject());
                }
                if (i == parts.length - 1) {
                    array.put(index, value);
                } else {
                    current = array.getJSONObject(index);
                }
            } else {
                if (i == parts.length - 1) {
                    ((JSONObject) current).put(part, value);
                } else {
                    if (!((JSONObject) current).has(part)) {
                        ((JSONObject) current).put(part, new JSONObject());
                    }
                    current = ((JSONObject) current).get(part);
                }
            }
        }
    }
}
