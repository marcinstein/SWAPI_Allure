package planets;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPlanetsTest extends BaseTest {

    @DisplayName("Test to read all Planets")
    @Test
    public void readAllPlanets () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PLANETS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> results = json.getList("results");
        assertThat(results).hasSize(10);
    }

    @DisplayName("Test to read first Planet")
    @Test
    public void readOnePlanet () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PLANETS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Tatooine");
        assertThat(json.getString("rotation_period")).isEqualTo("23");
        assertThat(json.getString("orbital_period")).isEqualTo("304");
        assertThat(json.getList("residents"))
                .hasSize(10)
                .containsExactly("http://swapi.py4e.com/api/people/1/",
                        "http://swapi.py4e.com/api/people/2/",
                        "http://swapi.py4e.com/api/people/4/",
                        "http://swapi.py4e.com/api/people/6/",
                        "http://swapi.py4e.com/api/people/7/",
                        "http://swapi.py4e.com/api/people/8/",
                        "http://swapi.py4e.com/api/people/9/",
                        "http://swapi.py4e.com/api/people/11/",
                        "http://swapi.py4e.com/api/people/43/",
                        "http://swapi.py4e.com/api/people/62/");
        assertThat(json.getList("residents").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read Planet using path parameters")
    @Test
    public void readOnePlanetWithPathVariable () {

        Response response = given()
                .spec(reqSpec)
                .pathParam("planetId", 1)
                .when()
                .get(BASE_URL + "/" + PLANETS + "/{planetId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Tatooine");
        assertThat(json.getString("rotation_period")).isEqualTo("23");
        assertThat(json.getString("orbital_period")).isEqualTo("304");
        assertThat(json.getList("residents"))
                .hasSize(10)
                .containsExactly("http://swapi.py4e.com/api/people/1/",
                        "http://swapi.py4e.com/api/people/2/",
                        "http://swapi.py4e.com/api/people/4/",
                        "http://swapi.py4e.com/api/people/6/",
                        "http://swapi.py4e.com/api/people/7/",
                        "http://swapi.py4e.com/api/people/8/",
                        "http://swapi.py4e.com/api/people/9/",
                        "http://swapi.py4e.com/api/people/11/",
                        "http://swapi.py4e.com/api/people/43/",
                        "http://swapi.py4e.com/api/people/62/");
        assertThat(json.getList("residents").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read Invalid planet")
    @Test
    public void readOneInvalidPlanet(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PLANETS + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}
