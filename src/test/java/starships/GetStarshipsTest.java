package starships;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetStarshipsTest extends BaseTest {

    @DisplayName("Test to read all Starships")
    @Test
    public void readAllStarships () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + STARSHIPS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> results = json.getList("results");
        assertThat(results).hasSize(10);
    }

    @DisplayName("Test to read first Starship")
    @Test
    public void readOneStarship () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + STARSHIPS + "/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("CR90 corvette");
        assertThat(json.getString("model")).isEqualTo("CR90 corvette");
        assertThat(json.getString("cost_in_credits")).isEqualTo("3500000");
        assertThat(json.getList("films"))
                .hasSize(3)
                .containsExactly("http://swapi.py4e.com/api/films/1/",
                        "http://swapi.py4e.com/api/films/3/",
                        "http://swapi.py4e.com/api/films/6/");
        assertThat(json.getList("films").get(2)).isEqualTo("http://swapi.py4e.com/api/films/6/");
    }

    @DisplayName("Test to read Starship using path parameters")
    @Test
    public void readOneStarshipWithPathVariable () {

        Response response = given()
                .spec(reqSpec)
                .pathParam("starshipId", 2)
                .when()
                .get(BASE_URL + "/" + STARSHIPS + "/{starshipId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("CR90 corvette");
        assertThat(json.getString("model")).isEqualTo("CR90 corvette");
        assertThat(json.getString("cost_in_credits")).isEqualTo("3500000");
        assertThat(json.getList("films"))
                .hasSize(3)
                .containsExactly("http://swapi.py4e.com/api/films/1/",
                        "http://swapi.py4e.com/api/films/3/",
                        "http://swapi.py4e.com/api/films/6/");
        assertThat(json.getList("films").get(2)).isEqualTo("http://swapi.py4e.com/api/films/6/");
    }

    @DisplayName("Test to read Invalid Starship")
    @Test
    public void readOneInvalidStarship(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + STARSHIPS + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}
