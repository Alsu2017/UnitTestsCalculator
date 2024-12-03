import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

public class SimpleAPITests {

    @Test
    void getInventory() {
        String endpoint = "https://petstore.swagger.io/v2/store/inventory";
        given().when().get(endpoint).then().log().all();
    }

    @Test
    void postGetUser() {
        String url = "https://petstore.swagger.io/v2/user";

        String body = """
                  {
                    "id": 1,
                    "username": "alsu",
                    "firstName": "alsu",
                    "lastName": "alsu",
                    "email": "alsu@gmail.com",
                    "password": "alsu",
                    "phone": "123",
                    "userStatus": 0
                  }
                """;

//        Метод post
        ValidatableResponse responseStatus =
                given().
                        header("accept", "application/json").
                        header("Content-Type", "application/json").
                        body(body).
                when().
                    post(url).
                then();
        Response responseAsResponse = responseStatus.extract().response();
        int status = responseAsResponse.statusCode();
        String responseText = responseAsResponse.body().prettyPrint();
        Assertions.assertEquals(200, status);

//        Метод get
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
        when().
                get(url+"/alsu").
        then().
                statusCode(200).
                body("email", equalTo("alsu@gmail.com")).
                log().all();

//        Метод put
        String bodyPut = """
                  {
                    "id": 1,
                    "username": "alsu111",
                    "firstName": "alsu",
                    "lastName": "alsu",
                    "email": "alsu@gmail.com",
                    "password": "alsu",
                    "phone": "123",
                    "userStatus": 0
                  }
                """;

        var response = given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(bodyPut).
            when().
                put(url+"/alsu").
            then();
        response.statusCode(200);
        response.log().all();
    }

    @Test
    void postArray() {
        String url = "https://petstore.swagger.io/v2/user/createWithArray";

        String body = """
                       [{
                           "id": 1,
                                   "username": "alsu",
                                   "firstName": "alsu",
                                   "lastName": "alsu",
                                   "email": "alsu@gmail.com",
                                   "password": "alsu",
                                   "phone": "123",
                                   "userStatus": 0
                       },
                       {
                           "id": 2,
                               "username": "slava",
                               "firstName": "slava",
                               "lastName": "slava",
                               "email": "slava@gmail.com",
                               "password": "slava",
                               "phone": "333",
                               "userStatus": 0
                       }]
                """;

//        Метод post
        ValidatableResponse responseStatus =
                given().
                        header("accept", "application/json").
                        header("Content-Type", "application/json").
                        body(body).
                        when().
                        post(url).
                        then().statusCode(200);

        //       Метод get
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                when().
                get("https://petstore.swagger.io/v2/user/slava").
                then().
                statusCode(200).
                log().all();

//        Метод delete
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                when().
                delete("https://petstore.swagger.io/v2/user/slava").
                then().statusCode(200).
                log().all();
    }

    @Test
    void postWithModelTest() {
        String endpoint = "https://petstore.swagger.io/v2/user";
        User user = new User(0, "FPMI_user_3", "firstName3", "lastName3", "email3@gmail.com", "qwe123", "123123123", 0);
        var response = given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(user).
                when().
                post(endpoint).
                then();
        response.log().body();
        response.statusCode(200);

        String username = "FPMI_user_3";
        String endpoint2 = "https://petstore.swagger.io/v2/user/" + username;
        given().
                when().
                get(endpoint2).
                then().
                log().
                all().
                assertThat().
                statusCode(200).
                body("username", equalTo("FPMI_user_3")).
                body("firstName", startsWith("firstName3")).
                body("lastName", equalToIgnoringCase("LASTNAME3")).
                body("email", matchesPattern("^[a-zA-Z0-9._%+-]+@gmail\\.com$")).
                body("password", equalTo("qwe123")).
                body("phone", equalTo("123123123"));
    }

    @Test
    void getComplexResponseWithQueryParamTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
        given().
                header("accept", "application/json").
                queryParam("status", "available").
                when().
                get(endpoint).
                then().
                assertThat().
                statusCode(200).
                header("content-type", equalTo("application/json")).
                body("id", everyItem(notNullValue())).
                body("status", everyItem(equalTo("available"))).
                body("size()", greaterThan(2));
    }

}
