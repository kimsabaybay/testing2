package ApiTests;

import com.github.dockerjava.api.command.CreateConfigCmd;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.jar.JarEntry;

public class ApiTests {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }
    @Test
    public void testGetPosts(){

        Response response = RestAssured.get("/posts");

        //validate the response status and content
        response.then().statusCode(200);
        response.then().log().body();
    }

    @Test
    public void testCreatePost() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "New Post");
        requestParams.put("body", "This is the body of my new post");
        requestParams.put("userId", 1);

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestParams.toString())
                .when()
                .post("/posts");

        response.then().statusCode(201);
        response.then().log().body();
    }
}
