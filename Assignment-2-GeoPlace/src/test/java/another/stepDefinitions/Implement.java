package another.stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Implement {

	private RequestSpecification request;
	private Response response;
	private String baseUrl = "https://api.genderize.io";
//    private final String API_KEY = "a6256247498338c24953fed643c3503d";

	@Given("I have access to the gender predict API")
	public void setApiEndpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
//        request.queryParam("apikey", API_KEY);
//        request.header("apikey",  API_KEY);
	}

	@And("I set the query parameter {string} to {string}")
	public void setQueryParameter(String key, String value) {
		request.queryParam(key, value);
	}

	@When("I send a GET request")
	public void sendGetRequest() {
		response = request.get();
	}

	@Then("The response status code should be {string}")
	public void verifyStatusCode(String expectedStatusCode) {
		int expectedStatusCodeInt = Integer.parseInt(expectedStatusCode);
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCodeInt,
				"Expected status code to be " + expectedStatusCodeInt + " but found " + actualStatusCode);

		System.out.println("Assertion Passed: Status code is " + actualStatusCode + " as expected.");
	}

	@And("the response should contain correct name {string}")
	public void verifyResponseContains(String expectedValue) {
		String responseBody = response.getBody().asString();
		System.out.println("API Response: " + responseBody); // For debugging

		JsonPath jsonPath = response.jsonPath();

		String expectedName = null;
		if (!"NULL".equalsIgnoreCase(expectedValue)) {
			// If the Gherkin value is NOT "NULL" (case-insensitive),
			// then we expect that literal string.
			expectedName = expectedValue;
		}

		String actualName = jsonPath.getString("name");
		Assert.assertEquals(actualName, expectedName, "Mismatch for 'name' field. Expected: '" + expectedValue
				+ "' (resolved to Java: " + expectedName + "), Actual: '" + actualName + "'");

		System.out.println("Assertion Passed: Gender is '" + (actualName == null ? "null" : actualName)
				+ "' as expected (" + expectedValue + ").");
	}

	@Then("the response should contain valid gender {string}")
	public void theResponseShouldContainGender(String expectedGender) {
		JsonPath jsonPath = response.jsonPath();
		String expectedGenderLiteral = null;
		if (!"NULL".equalsIgnoreCase(expectedGender)) {
			// If the Gherkin value is NOT "NULL" (case-insensitive),
			// then we expect that literal string.
			expectedGenderLiteral = expectedGender;
		}

		String actualGender = jsonPath.getString("gender");
		Assert.assertEquals(actualGender, expectedGenderLiteral,
				"Mismatch for 'gender' field. Expected: '" + expectedGender + "' (resolved to Java: "
						+ expectedGenderLiteral + "), Actual: '" + actualGender + "'");
	}

	@And("I set the query parameter {string} to {string} and {string} to {string}")
	public void setQueryParameter(String key1, String value1, String key2, String value2) {
		request.queryParam(key1, value1);
		request.queryParam(key2, value2);
	}

	@And("the response should contain valid countryname {string}")
	public void theResponseShouldContainCountry(String expectedCountry) {
		JsonPath jsonPath = response.jsonPath();
		// Determine the expected value as a Java String (or null)
		String expectedCountryLiteral = null;
		if (!"NULL".equalsIgnoreCase(expectedCountry)) {
			// If the Gherkin value is NOT "NULL" (case-insensitive),
			// then we expect that literal string.
			expectedCountryLiteral = expectedCountry;
		}

		String actualCountry = jsonPath.getString("country_id");
		Assert.assertEquals(actualCountry, expectedCountryLiteral,
				"Expected Country to be " + expectedCountry + " but found " + actualCountry);
	}

	@And("the response should be a JSON array of {int} objects")
	public void theResponseShouldBeAJSONArrayOfObjects(int expectedSize) {
		List<Map<String, Object>> jsonResponse = response.jsonPath().getList("$");
		Assert.assertNotNull(jsonResponse);
		Assert.assertEquals(expectedSize, jsonResponse.size());
		System.out.println("Asserted JSON array size: " + expectedSize);
	}

	@And("the response for {string} should have gender {string}")
	public void theResponseForShouldHaveGender(String name, String expectedGender) {
		List<Map<String, Object>> jsonResponse = response.jsonPath().getList("$");
		Map<String, Object> nameEntry = jsonResponse.stream().filter(entry -> name.equals(entry.get("name")))
				.findFirst().orElse(null);
		Assert.assertEquals(expectedGender, nameEntry.get("gender"));

	}

	@And("I set multiple names query parameter {string}, {string}, {string}")
	public void setQueryParameter(String name1, String name2, String name3) {
		request.queryParam("name[]", name1);
		request.queryParam("name[]", name2);
		request.queryParam("name[]", name3);
	}
}
