package liv.bradford.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import liv.bradford.utilities.DB_Util;
import liv.bradford.utilities.LibraryUtil;
import org.junit.Assert;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import  static liv.bradford.stepdefinitions.US03_create_book.respons_id;
import  static liv.bradford.stepdefinitions.US03_create_book.user;
import static liv.bradford.stepdefinitions.Hooks.librarianSpec;

public class US4_create_user {
Response response;

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {
        DB_Util.runQuery("select * from users where id=" + respons_id);
        Map<String,Object> db_usr=  DB_Util.getRowMap(1);
        Assert.assertEquals(db_usr.get("full_name"), user.get("full_name"));
        Assert.assertEquals(db_usr.get("email"), user.get("email"));

    }

    @Then("newly created user can login into system")
    public void newly_created_user_can_login_into_system() {
        given().contentType(ContentType.URLENC)
                .formParam("email", user.get("email"))
                .formParam("password",user.get("password"))
                .when()
                .post("/login").then().statusCode(200);
    }
}
