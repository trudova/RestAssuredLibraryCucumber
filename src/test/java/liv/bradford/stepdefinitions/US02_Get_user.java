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

public class US02_Get_user {
Response response;
    int id =1;

    @When("I send GET request to {string} + id endpoint")
    public void iSendGETRequestToIdEndpoint(String endpoint) {
        response=  librarianSpec.get(endpoint+id).prettyPeek();
    }

    @Then("Response Content type is {string}, status code {int}")
    public void response_content_type_is_status_code(String contentTyp, Integer statusCod) {
        response.then().contentType(contentTyp).statusCode(statusCod);
    }

    @Then("id field should be same with path param")
    public void id_field_should_be_same_with_path_param() {
       response.then().body("id",is(""+id));
    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String > dataTable) {
        dataTable.stream().forEach(field-> response.then().body(field,notNullValue()));
        
    }



}
