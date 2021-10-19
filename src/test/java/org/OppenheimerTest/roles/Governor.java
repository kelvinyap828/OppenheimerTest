package org.OppenheimerTest.roles;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.OppenheimerTest.stepDefinition.Config;

public class Governor {

    public static Response dispense(){

        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .log().all()
                .get("/dispense");
        response.then().log().all();

        return response;
    }
}
