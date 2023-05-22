package liv.bradford.stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.BeforeAll;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import liv.bradford.utilities.ConfigurationReader;
import liv.bradford.utilities.DB_Util;

import static io.restassured.RestAssured.*;
public class Hooks {
    public static Faker faker = new Faker();

    public static String librarianToken;
    public static String studentToken;
    public static RequestSpecification librarianSpec;
    public static ResponseSpecification libraryResponseSpec;
    public static RequestSpecification studentSpec;
    public static ResponseSpecification studentResponseSpec;
    @BeforeAll
    public static void init(){
        baseURI=ConfigurationReader.getProperty("library.baseUri");
        basePath =ConfigurationReader.getProperty("library.basePath");
        librarianToken = getToken(ConfigurationReader.getProperty("librarian_username"), ConfigurationReader.getProperty("librarian_password"));
        studentToken = getToken(ConfigurationReader.getProperty("student_username"), ConfigurationReader.getProperty("student_password"));
        DB_Util.createConnection(ConfigurationReader.getProperty("library2.db.url"), ConfigurationReader.getProperty("library2.db.username"),ConfigurationReader.getProperty("library2.db.password"));
        librarianSpec=given().accept(ContentType.JSON).header("x-library-token",librarianToken);
        libraryResponseSpec=expect().logDetail(LogDetail.BODY)
                .statusCode(200)
                .contentType(ContentType.JSON);

        studentSpec=given().accept(ContentType.JSON).header("x-library-token",librarianToken);
        studentResponseSpec=expect().logDetail(LogDetail.BODY)
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    public static String  getToken(String username, String password){
        return given().contentType(ContentType.URLENC)
                .formParam("email", username)
                .formParam("password",password)
                .when()
                .post("/login")
                .path("token");

    }

}
