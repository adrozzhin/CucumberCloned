package APISteps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {

    public static String token;

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        RequestSpecification generateTokenRequest = given().header("Content-type", "application/json").body("{\n" +
                "  \"email\": \"and4645@abc.com\",\n" +
                "  \"password\": \"test123\"\n" +
                "}");

        Response generatedTokenResponse = generateTokenRequest.when().post("/generateToken.php");
        generatedTokenResponse.prettyPrint();

        token = "Bearer " + generatedTokenResponse.jsonPath().getString("token");
    }
}
