package org.OppenheimerTest.roles;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.OppenheimerTest.stepDefinition.Config;

public class Bookkeeper {

    public static Response getTaxRelief(){

        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .log().all()
                .get("/calculator/taxRelief");
        response.then().log().all();

        return response;
    }

    public static Response getTaxReliefSummary(){

        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .log().all()
                .get("/calculator/taxReliefSummary");
        response.then().log().all();

        return response;
    }
}
