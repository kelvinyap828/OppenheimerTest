package org.OppenheimerTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.OppenheimerTest.roles.Bookkeeper;
import org.OppenheimerTest.roles.Clerk;
import org.OppenheimerTest.roles.Employee;
import org.OppenheimerTest.utility.CommonFunction;
import org.OppenheimerTest.utility.Convertor;
import org.OppenheimerTest.utility.Generator;
import org.OppenheimerTest.utility.RobotHelper;

import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EndToEnd {

    Config config = new Config();
    WebDriverWait wait;
    WebDriver driver = new ChromeDriver();
    Employee employee;
    List<Employee> employeeList = new ArrayList<>();
    Response respInsert, respInsertMultiple;
    Response respTaxRelief, respTaxReliefSummary;

    @Given("Clerk has inserted working class hero records via all the channels")
    public void clerk_has_inserted_working_class_hero_records_via_all_the_channels() throws AWTException, InterruptedException {

        //insert single record
        employee = CommonFunction.generateEmployee();
        respInsert = Clerk.insert(employee);
        respInsert.then().assertThat().statusCode(HttpStatus.SC_ACCEPTED);

        //insert multiple records
        employeeList = CommonFunction.generateEmployeeList(20);
        respInsertMultiple = Clerk.insertMultiple(employeeList);

        //Upload csv
        Generator.createCSVFile(20);

        System.setProperty("webdriver.chrome.driver", Config.driverDir);
        driver = new ChromeDriver();
        driver.get(Config.baseUri);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#contents > div.input-group.mb-3 > div.custom-file")));
        driver.findElement(By.cssSelector("#contents > div.input-group.mb-3 > div.custom-file")).click();
        RobotHelper.uploadFile(Config.dateFilePath);

        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.cssSelector("#contents > button.btn.btn-primary")).click();

    }
    @Given("Bookkeeper has reported the figure")
    public void bookkeeper_has_reported_the_figure() {

        //Get tax relief and tax relief summary
        respTaxRelief = Bookkeeper.getTaxRelief();
        respTaxRelief.then().assertThat().statusCode(200);
        respTaxReliefSummary = Bookkeeper.getTaxReliefSummary();
        respTaxReliefSummary.then().assertThat().statusCode(200);

        List<Map<String, Object>> taxReliefList = respTaxRelief.then().extract().jsonPath().get();
        Map<String, String> taxReliefSummaryMap = respTaxReliefSummary.then().extract().jsonPath().get();

        //Group all data into one list for assertion
        List<Employee> dataListFromFile = CommonFunction.readCsvFile(Config.dateFilePath);
        employeeList.addAll(dataListFromFile);
        employeeList.add(employee);
        Assert.assertEquals(taxReliefList.size(), employeeList.size());

        //Verify tax relief response
        double expectedSumTaxRelief = 0;
        for(Employee expected : employeeList){

            expectedSumTaxRelief = expectedSumTaxRelief + Double.parseDouble(expected.getTaxRelief());
            String maskedNatId = Convertor.maskNatId(expected.getNatId(), 4);
            List<Map<String,Object>> filterResult = taxReliefList.stream()
                    .filter(actual -> maskedNatId.equals(actual.get("natid")) && expected.getName().equals(actual.get("name")))
                    .collect(Collectors.toList());

            //Assert.assertEquals(filterResult.get(0).get("relief"), expected.getTaxRelief());

        }

        //Verify tax relief summary response
        Assert.assertEquals(taxReliefSummaryMap.get("totalWorkingClassHeroes"), String.valueOf(employeeList.size()));
        //Assert.assertEquals(taxReliefSummaryMap.get("totalTaxReliefAmount"), expectedSumTaxRelief);

    }
    @When("Governor dispenses the tax relief")
    public void governor_dispenses_the_tax_relief() {

        WebElement dispenseButton = driver.findElement(By.cssSelector("#contents > a.btn.btn-danger.btn-block"));
        Assert.assertEquals(dispenseButton.getText(), "Dispense Now");
        Assert.assertEquals(Color.fromString(dispenseButton.getCssValue("background-color")).asHex(), "#dc3545");
        dispenseButton.click();

    }
    @Then("tax relief for all the records are dispensed successfully")
    public void tax_relief_for_all_the_records_are_dispensed_successfully() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='display-4 font-weight-bold']")));
        WebElement dispenseMessage = driver.findElement(By.xpath("//div[@class='display-4 font-weight-bold']"));

        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/dispense");
        Assert.assertEquals(dispenseMessage.getText(), "Cash dispensed");

        driver.quit();
    }
}
