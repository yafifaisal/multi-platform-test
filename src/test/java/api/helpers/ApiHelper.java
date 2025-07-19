package api.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiHelper {

    public static Response sendRequest(
            String method,
            String baseUri,
            String path,
            Map<String, String> headers,
            String body,
            Map<String, String> queryParams) {
        String resolvedPath = resolvePlaceholders(path);
        String resolvedBody = resolvePlaceholders(body);

        System.out.println("\n=== REQUEST INFO ===");
        System.out.println("Method : " + method.toUpperCase());
        System.out.println("URL    : " + baseUri + resolvedPath);

        if (queryParams != null && !queryParams.isEmpty()) {
            System.out.println("Query Params:");
            queryParams.forEach((k, v) -> System.out.println("  " + k + ": " + v));
        }

        System.out.println("Headers:");
        headers.forEach((k, v) -> System.out.println("  " + k + ": " + v));

        if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("DELETE") && resolvedBody != null) {
            System.out.println("Body:\n" + resolvedBody);
        }

        RequestSpecification request = RestAssured.given()
                .baseUri(baseUri)
                .basePath(resolvedPath)
                .headers(headers)
                .contentType("application/json");

        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("DELETE")) {
            if (body != null && !body.isBlank()) {
                request.body(resolvedBody);
            }
        }

        Response response = switch (method.toUpperCase()) {
            case "GET" -> request.get();
            case "POST" -> request.post();
            case "PUT" -> request.put();
            case "DELETE" -> request.delete();
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };

        System.out.println("\n=== RESPONSE INFO ===");
        System.out.println("Status Code : " + response.getStatusCode());
        System.out.println("Headers     :");
        response.getHeaders().forEach(h -> System.out.println("  " + h.getName() + ": " + h.getValue()));
        System.out.println("Body:");
        System.out.println(prettyJson(response.getBody().asString()));

        return response;
    }

    public static String resolvePlaceholders(String input) {
        if (input == null)
            return null;

        Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = ContextStore.getAsString(key);
            if (value == null) {
                System.err.println("⚠️  WARNING: Context key not found for ${" + key + "}");
                value = "";
            }
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String prettyJson(String json) {
        try {
            if (json.trim().startsWith("[")) {
                return new org.json.JSONArray(json).toString(2);
            } else {
                return new org.json.JSONObject(json).toString(2);
            }
        } catch (Exception e) {
            return json; // Fallback: return as-is if not valid JSON
        }
    }
}
