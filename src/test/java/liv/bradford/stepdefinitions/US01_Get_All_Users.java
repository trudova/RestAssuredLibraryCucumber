package liv.bradford.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static liv.bradford.stepdefinitions.Hooks.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class US01_Get_All_Users {
public static Response response;
    @Given("I logged Library api as a {string} with {string} header")
    public void i_logged_library_api_as_a_with_header(String userRole, String headers) {
        if(userRole.equalsIgnoreCase("librarian")){
            given().spec(librarianSpec).accept(headers);
        }else {
            given().spec(studentSpec).accept(headers);
        }
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
       response= librarianSpec.get(endpoint).prettyPeek();
    }



    @Then("Response Content type is {string}, status code {int},{string} and {string} not null")
    public void responseContentTypeIsStatusCodeAndNotNull(String contentType, int statusCode, String id, String name) {
        response.then().statusCode(statusCode)
                .contentType(contentType)
                .body(id, everyItem(notNullValue())).body(name, everyItem(notNullValue()));

    }
}
