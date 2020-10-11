package people;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPeopleTest extends BaseTest {

        @DisplayName("Test to read all People resource")
        @Test
        public void readAllPeople () {

            Response response = given()
                    .spec(reqSpec)
                    .when()
                    .get(BASE_URL + "/" + PEOPLE)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            List<String> results = json.getList("results");
            assertThat(results).hasSize(10);
        }

        @DisplayName("Test to read first person")
        @Test
        public void readOnePerson () {

            Response response = given()
                    .spec(reqSpec)
                    .when()
                    .get(BASE_URL + "/" + PEOPLE + "/1")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("name")).isEqualTo("Luke Skywalker");
            assertThat(json.getString("height")).isEqualTo("172");
            assertThat(json.getString("mass")).isEqualTo("77");
            assertThat(json.getList("films"))
                    .hasSize(5)
                    .containsExactly("http://swapi.py4e.com/api/films/1/",
                    "http://swapi.py4e.com/api/films/2/",
                    "http://swapi.py4e.com/api/films/3/",
                    "http://swapi.py4e.com/api/films/6/",
                    "http://swapi.py4e.com/api/films/7/");
            assertThat(json.getList("films").get(0)).isEqualTo("http://swapi.py4e.com/api/films/1/");
        }

        @DisplayName("Test to read person using path parameters")
        @Test
        public void readOnePersonWithPathVariable () {

            Response response = given()
                    .spec(reqSpec)
                    .pathParam("personId", 1)
                    .when()
                    .get(BASE_URL + "/" + PEOPLE + "/{personId}")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("name")).isEqualTo("Luke Skywalker");
            assertThat(json.getString("height")).isEqualTo("172");
            assertThat(json.getString("mass")).isEqualTo("77");
            assertThat(json.getList("films"))
                    .hasSize(5)
                    .containsExactly("http://swapi.py4e.com/api/films/1/",
                            "http://swapi.py4e.com/api/films/2/",
                            "http://swapi.py4e.com/api/films/3/",
                            "http://swapi.py4e.com/api/films/6/",
                            "http://swapi.py4e.com/api/films/7/");
            assertThat(json.getList("films").get(0)).isEqualTo("http://swapi.py4e.com/api/films/1/");
        }

    @DisplayName("Test to read Invalid person")
    @Test
    public void readOneInvalidPerson(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PEOPLE + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}