package liv.bradford.stepdefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static liv.bradford.stepdefinitions.Hooks.*;
import static liv.bradford.stepdefinitions.US03_create_book.user;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import liv.bradford.utilities.DB_Util;
import liv.bradford.utilities.LibraryUtil;
import org.junit.Assert;

import java.util.Map;

import static liv.bradford.stepdefinitions.Hooks.librarianSpec;

public class US05_decode_user {
    RequestSpecification specification;
    String token;
    Response responseGlobal;
    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String email, String password) {
      Response response=  given().contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password",password)
                .when()
                .post("/login");
      response.then().statusCode(200);
      token = response.jsonPath().getString("token");
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String headers) {
       specification= given().accept(headers);
    }

    @Given("Request Content Type header  {string}")
    public void request_content_type_header(String contentType) {
        specification.contentType(contentType);
    }

    @Given("send token information as request body")
    public void send_token_information_as_request_body() {
        specification.formParam("token", token);
    }

    @When("send POST request to {string} endpoint")
    public void send_post_request_to_endpoint(String endpoint) {
        responseGlobal = specification.post(endpoint).prettyPeek();
    }

    @Then("status code should be {int} Ok")
    public void status_code_should_be_ok(Integer statusCode) {
        responseGlobal.then().statusCode(statusCode);
    }

    @Then("Response Content type should be {string}")
    public void response_content_type_should_be(String contentType) {
            responseGlobal.then().contentType(contentType);
    }

    @Then("the value for {string} path should be equal to {string}")
    public void the_value_for_path_should_be_equal_to(String ug, String ugid) {
        responseGlobal.then().body(ug, equalTo(ugid));
    }

    @Then("{string} field value should not be null")
    public void field_value_should_not_be_null(String field) {
        responseGlobal.then().body(field, notNullValue());

    }
}
