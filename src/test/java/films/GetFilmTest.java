package films;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swapi.py4e.base.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class GetFilmTest extends BaseTest {

    @DisplayName("Test to read all films")
    @Test
    public void readAllFilms(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + FILMS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> results = json.getList("results");
        assertThat(results).hasSize(7);
    }

    @DisplayName("Test to read first film")
    @Test
    public void readOneFilm(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + FILMS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("title")).isEqualTo("A New Hope");
        assertThat(json.getString("episode_id")).isEqualTo("4");
        assertThat(json.getString("opening_crawl")).isEqualToIgnoringWhitespace("It is a period of civil war. " +
                "Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire. " +
                "During the battle, Rebel spies managed to steal secret plans to the Empire's ultimate weapon, " +
                "the DEATH STAR, an armored space station with enough power to destroy an entire planet.  Pursued by " +
                "the Empire's sinister agents, Princess Leia races home aboard her starship, custodian of the stolen " +
                "plans that can save her people and restore freedom to the galaxy....");
        assertThat(json.getString("director")).isEqualTo("George Lucas");
        assertThat(json.getString("producer")).isEqualTo("Gary Kurtz, Rick McCallum");
        assertThat(json.getString("release_date")).isEqualTo("1977-05-25");
        assertThat(json.getList("characters"))
                .hasSize(18)
                .containsExactly("http://swapi.py4e.com/api/people/1/",
                        "http://swapi.py4e.com/api/people/2/",
                        "http://swapi.py4e.com/api/people/3/",
                        "http://swapi.py4e.com/api/people/4/",
                        "http://swapi.py4e.com/api/people/5/",
                        "http://swapi.py4e.com/api/people/6/",
                        "http://swapi.py4e.com/api/people/7/",
                        "http://swapi.py4e.com/api/people/8/",
                        "http://swapi.py4e.com/api/people/9/",
                        "http://swapi.py4e.com/api/people/10/",
                        "http://swapi.py4e.com/api/people/12/",
                        "http://swapi.py4e.com/api/people/13/",
                        "http://swapi.py4e.com/api/people/14/",
                        "http://swapi.py4e.com/api/people/15/",
                        "http://swapi.py4e.com/api/people/16/",
                        "http://swapi.py4e.com/api/people/18/",
                        "http://swapi.py4e.com/api/people/19/",
                        "http://swapi.py4e.com/api/people/81/");
        assertThat(json.getList("characters").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read film using path parameters")
    @Test
    public void readOneFilmWithPathVariable(){

        Response response = given()
                .spec(reqSpec)
                .pathParam("filmId", 1)
                .when()
                .get(BASE_URL + "/" + FILMS + "/{filmId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("title")).isEqualTo("A New Hope");
        assertThat(json.getString("episode_id")).isEqualTo("4");
        assertThat(json.getString("opening_crawl")).isEqualToIgnoringWhitespace("It is a period of civil war. " +
                "Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire. " +
                "During the battle, Rebel spies managed to steal secret plans to the Empire's ultimate weapon, " +
                "the DEATH STAR, an armored space station with enough power to destroy an entire planet.  Pursued by " +
                "the Empire's sinister agents, Princess Leia races home aboard her starship, custodian of the stolen " +
                "plans that can save her people and restore freedom to the galaxy....");
        assertThat(json.getString("director")).isEqualTo("George Lucas");
        assertThat(json.getString("producer")).isEqualTo("Gary Kurtz, Rick McCallum");
        assertThat(json.getString("release_date")).isEqualTo("1977-05-25");
        assertThat(json.getList("characters"))
                .hasSize(18)
                .containsExactly("http://swapi.py4e.com/api/people/1/",
                        "http://swapi.py4e.com/api/people/2/",
                        "http://swapi.py4e.com/api/people/3/",
                        "http://swapi.py4e.com/api/people/4/",
                        "http://swapi.py4e.com/api/people/5/",
                        "http://swapi.py4e.com/api/people/6/",
                        "http://swapi.py4e.com/api/people/7/",
                        "http://swapi.py4e.com/api/people/8/",
                        "http://swapi.py4e.com/api/people/9/",
                        "http://swapi.py4e.com/api/people/10/",
                        "http://swapi.py4e.com/api/people/12/",
                        "http://swapi.py4e.com/api/people/13/",
                        "http://swapi.py4e.com/api/people/14/",
                        "http://swapi.py4e.com/api/people/15/",
                        "http://swapi.py4e.com/api/people/16/",
                        "http://swapi.py4e.com/api/people/18/",
                        "http://swapi.py4e.com/api/people/19/",
                        "http://swapi.py4e.com/api/people/81/");
        assertThat(json.getList("characters").get(0)).isEqualTo("http://swapi.py4e.com/api/people/1/");
    }

    @DisplayName("Test to read Invalid film")
    @Test
    public void readOneInvalidFilm(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + FILMS + "/100")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}