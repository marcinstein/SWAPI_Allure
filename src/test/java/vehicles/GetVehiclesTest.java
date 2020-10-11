package vehicles;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetVehiclesTest extends BaseTest {

    @DisplayName("Test to read all Vehicles")
    @Test
    public void readAllVehicles () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + VEHICLES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> results = json.getList("results");
        assertThat(results).hasSize(10);
    }

    @DisplayName("Test to read first Vehicle")
    @Test
    public void readOneVehicle () {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + VEHICLES + "/4")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Sand Crawler");
        assertThat(json.getString("model")).isEqualTo("Digger Crawler");
        assertThat(json.getString("cost_in_credits")).isEqualTo("150000");
        assertThat(json.getList("films"))
                .hasSize(2)
                .containsExactly("http://swapi.py4e.com/api/films/1/","http://swapi.py4e.com/api/films/5/");
        assertThat(json.getList("films").get(0)).isEqualTo("http://swapi.py4e.com/api/films/1/");
    }

    @DisplayName("Test to read Vehicle using path parameters")
    @Test
    public void readOneVehicleWithPathVariable () {

        Response response = given()
                .spec(reqSpec)
                .pathParam("vehicleId", 4)
                .when()
                .get(BASE_URL + "/" + VEHICLES + "/{vehicleId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Sand Crawler");
        assertThat(json.getString("model")).isEqualTo("Digger Crawler");
        assertThat(json.getString("cost_in_credits")).isEqualTo("150000");
        assertThat(json.getList("films"))
                .hasSize(2)
                .containsExactly("http://swapi.py4e.com/api/films/1/","http://swapi.py4e.com/api/films/5/");
        assertThat(json.getList("films").get(0)).isEqualTo("http://swapi.py4e.com/api/films/1/");
    }

    @DisplayName("Test to read Invalid Vehicle")
    @Test
    public void readOneInvalidVehicle(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + VEHICLES + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}