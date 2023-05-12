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

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static liv.bradford.stepdefinitions.Hooks.librarianSpec;

public class us01_02_getUser_s {
RequestSpecification spec;
Response response;
ValidatableResponse validatableResponse;
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String string) {
        if(string.equalsIgnoreCase("librarian")){
           spec= given().spec(librarianSpec);
        }
    }
    @Given("Accept header is {string}")
    public void accept_header_is(String header) {
    spec.accept(header);
    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String users) {
        response = spec.when().get(users).prettyPeek();
    }
    @Then("status code should be {int}")
    public void status_code_should_be(Integer code) {
     validatableResponse= response.then().statusCode(code);
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        validatableResponse.contentType(contentType);
    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
    validatableResponse.body(path,everyItem(notNullValue()));
    }

    /*
    TODO: US 02
     */
    String id;
    @And("Path param is {string}")
    public void pathParamIs(String path) {
        spec.pathParam("id",path);
        id =path;
    }

    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String idPath) {
        validatableResponse.body(idPath, is(id));
    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String > dataTable) {
        dataTable.stream().map(p->validatableResponse.body(p, is(notNullValue())));
    }
}
