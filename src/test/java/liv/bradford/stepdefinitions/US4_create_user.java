package liv.bradford.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.response.Response;
import liv.bradford.utilities.DB_Util;
import liv.bradford.utilities.LibraryUtil;
import org.junit.Assert;

import java.io.File;
import java.util.Map;
import  static liv.bradford.stepdefinitions.US03_create_book.respons_id;
import  static liv.bradford.stepdefinitions.US03_create_book.user;
import static liv.bradford.stepdefinitions.Hooks.librarianSpec;

public class US4_create_user {
Response response;

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {
        DB_Util.runQuery("select * from users where id=" + respons_id);
      Map<String,Object> db_usr=  DB_Util.getRowMap(1);
        System.out.println(user);
        Assert.assertEquals(db_usr.get("full_name"), user.get("full_name"));
        Assert.assertEquals(db_usr.get("email"), user.get("email"));

    }
}
