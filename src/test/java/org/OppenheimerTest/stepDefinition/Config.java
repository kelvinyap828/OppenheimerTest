package org.OppenheimerTest.stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Config {

    public static final String baseUri = "http://localhost:8080";
    public static final String driverDir = ".\\src\\test\\resources\\driver\\chromedriver.exe";
    public static final String dateFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.csv";

    @Before
    public void clearData(){
        RestAssured.given()
                .baseUri(baseUri)
                .log().all()
                .post("/calculator/rakeDatabase")
                .then().log().all()
                .assertThat().statusCode(200);
    }
}
