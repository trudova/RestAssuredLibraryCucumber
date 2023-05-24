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

import liv.bradford.utilities.DB_Util;
import liv.bradford.utilities.LibraryUtil;
import org.junit.Assert;

import java.util.Map;

import static liv.bradford.stepdefinitions.Hooks.librarianSpec;

public class US03_create_book {

    Response response;
    Map<String, Object> book =LibraryUtil.getRandomBookMap();
   public static Map<String, Object> user =LibraryUtil.getRandomUserMap();
   public static String respons_id;

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        librarianSpec.contentType(contentType);
    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint) {
        if(endPoint.equalsIgnoreCase("/add_book")){
            response =librarianSpec.formParams(book).post(endPoint).prettyPeek();
        }else {
            response =librarianSpec.formParams(user).post(endPoint).prettyPeek();
        }

    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        response.then().contentType(contentType);
    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String message, String expectedMessage) {
        String actualMessage = response.path(message);
        Assert.assertEquals(expectedMessage,actualMessage);
    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String id) {
        respons_id = response.path(id);
        response.then().body(id, notNullValue());

    }

    @Then("Database and API created book information must match")
    public void database_and_api_created_book_information_must_match() {
        DB_Util.runQuery("select * from books where id="+respons_id);
        Map<String,Object> bookRecord = DB_Util.getRowMap(1);
        System.out.println(book);
        Assert.assertEquals(bookRecord.get("name"),book.get("name"));
        Assert.assertEquals(bookRecord.get("author"),book.get("author"));
        Assert.assertEquals(bookRecord.get("description"),book.get("description"));
        Assert.assertEquals(bookRecord.get("book_category_id"),""+book.get("book_category_id"));
    }



}
