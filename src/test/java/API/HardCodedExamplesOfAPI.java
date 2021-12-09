package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class HardCodedExamplesOfAPI {

    /*
Given - pre condition - prepare the request
When - Action - sending the request/hitting the endpoint
Then - result - verify response
     */

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzkwMTE3ODUsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYzOTA1NDk4NSwidXNlcklkIjoiMzIyNiJ9.udA4XN9Wjvr_0vy3fy87LQ4g9AcqCF3Uk-YK4ZGPhAg";
static String employee_id;

@Test
public void getDetailsOfOneEmployee(){
    //rest assured consider baseurl as baseuri
    //given
   RequestSpecification preparedRequest = given().header("Authorization", token)
            .header("Content-Type", "application/json").queryParam("employee_id", "25598A");

   //when - hitting the endpoint
   Response response = preparedRequest.when().get("/getOneEmployee.php");
   System.out.println(response.asString());

   //then - result/assertion
    response.then().assertThat().statusCode(200);
}

@Test
public void createEmployee(){

    //given
   RequestSpecification preparedRequest = given().header("Authorization", token)
            .header("Content-Type", "application/json").body("{\n" +
                    "  \"emp_firstname\": \"msthegreat123\",\n" +
                    "  \"emp_lastname\": \"Andru2\",\n" +
                    "  \"emp_middle_name\": \"elenam123\",\n" +
                    "  \"emp_gender\": \"M\",\n" +
                    "  \"emp_birthday\": \"1999-01-12\",\n" +
                    "  \"emp_status\": \"Employee\",\n" +
                    "  \"emp_job_title\": \"API Tester\"\n" +
                    "}");


   //when
    Response response = preparedRequest.when().post("/createEmployee.php");
    response.prettyPrint();
    //this pretty print does the same job as syso. //   System.out.println(response.asString());

    //jsonPath() we use this to get specific information from the json object
    employee_id = response.jsonPath().getString("Employee.employee_id");
    System.out.println(employee_id);

    //then
    response.then().assertThat().statusCode(201);
    response.then().assertThat().body("Employee.emp_firstname", equalTo("msthegreat123"));
    response.then().assertThat().body("Message", equalTo("Employee Created"));
    response.then().assertThat().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18");

}

    @Test
public void getCreatedEmployee() {
    RequestSpecification preparedRequest = given().header("Authorization", token)
            .header("Content-Type", "application/json").queryParam("employee_id", employee_id);
    Response response = preparedRequest.when().get("/getOneEmployee.php");

    String empID = response.body().jsonPath().getString("employee.employee_id");

    boolean comparingEmpID = empID.contentEquals(employee_id);
    Assert.assertTrue(comparingEmpID);

    String firstName = response.jsonPath().getString("employee.emp_firstname");
    Assert.assertTrue(firstName.contentEquals("msthegreat123"));

    String lastName = response.jsonPath().getString("employee.emp_lastname");
    Assert.assertTrue(lastName.contentEquals("Andru2"));

}
    @Test
public void updatedCreatedEmployee() {
    RequestSpecification preparedRequest = given().header("Authorization", token)
            .header("Content-Type", "application/json").body("{\n" +
                    "  \"employee_id\": \"" + employee_id + "\",\n" +
                    "  \"emp_firstname\": \"Andrei\",\n" +
                    "  \"emp_lastname\": \"Drozzhin\",\n" +
                    "  \"emp_middle_name\": \"Viktorovich\",\n" +
                    "  \"emp_gender\": \"M\",\n" +
                    "  \"emp_birthday\": \"2002-01-12\",\n" +
                    "  \"emp_status\": \"Active\",\n" +
                    "  \"emp_job_title\": \"Database Administrator\"\n" +
                    "}");

    Response response = preparedRequest.when().put("/updateEmployee.php");
}

}
