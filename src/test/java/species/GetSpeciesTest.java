package species;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetSpeciesTest extends BaseTest {

    @DisplayName("Test to read all Species")
    @Test
    public void readAllSpecies () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + SPECIES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> results = json.getList("results");
        assertThat(results).hasSize(10);
    }

    @DisplayName("Test to read first species")
    @Test
    public void readOneSpecies () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + SPECIES + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Human");
        assertThat(json.getString("classification")).isEqualTo("mammal");
        assertThat(json.getString("hair_colors")).isEqualTo("blonde, brown, black, red");
        assertThat(json.getList("people"))
                .hasSize(35)
                .contains("http://swapi.py4e.com/api/people/6/");
        assertThat(json.getList("people").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read Species using path parameters")
    @Test
    public void readOneSpeciesWithPathVariable () {

        Response response = given()
                .spec(reqSpec)
                .pathParam("speciesId", 1)
                .when()
                .get(BASE_URL + "/" + SPECIES + "/{speciesId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Human");
        assertThat(json.getString("classification")).isEqualTo("mammal");
        assertThat(json.getString("hair_colors")).isEqualTo("blonde, brown, black, red");
        assertThat(json.getList("people"))
                .hasSize(35)
                .contains("http://swapi.py4e.com/api/people/6/");
        assertThat(json.getList("people").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read Invalid species")
    @Test
    public void readOneInvalidSpecies(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + SPECIES + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}
