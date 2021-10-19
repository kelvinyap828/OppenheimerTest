package org.OppenheimerTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.OppenheimerTest.roles.Clerk;
import org.OppenheimerTest.roles.Employee;
import org.OppenheimerTest.utility.CommonFunction;
import org.OppenheimerTest.utility.Generator;

import java.awt.AWTException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.OppenheimerTest.utility.RobotHelper;
import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class InputData {

    WebDriverWait wait;
    WebDriver driver;
    Employee employee;
    Response respInsert, respUpload;
    List<Employee> employeeList = new ArrayList<Employee>();

    @Given("clerk has a working class hero record")
    public void clerk_has_a_working_class_hero_record() {

        employee = CommonFunction.generateEmployee();

    }

    @When("the clerk insert the record via insert API")
    public void the_clerk_insert_the_record_via_api() {

        respInsert = Clerk.insert(employee);
    }

    @Then("record inserted successfully")
    public void record_inserted_successfully() {

        respInsert.then().assertThat().statusCode(HttpStatus.SC_ACCEPTED);
    }

    @Given("clerk has {int} working class hero records in list")
    public void clerk_has_working_class_hero_records_in_list(int numOfRecords) {

        employeeList = CommonFunction.generateEmployeeList(numOfRecords);

    }

    @When("the clerk insert all the records via insertMultiple API")
    public void the_clerk_insert_all_the_records_via_insert_multiple_api(){

        respInsert = Clerk.insertMultiple(employeeList);

    }

    @Given("clerk has a csv file of {int} working class hero record")
    public void clerk_has_a_csv_file_of_working_class_hero_record(int numOfRecords) {

        Generator.createCSVFile(numOfRecords);

    }
    @And("the clerk has accessed to portal")
    public void the_clerk_has_accessed_to_portal() {

        System.setProperty("webdriver.chrome.driver", Config.driverDir);
        driver = new ChromeDriver();
        driver.get(Config.baseUri);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }
    @When("the clerk upload the file via UI")
    public void the_clerk_upload_the_file_via_ui() throws AWTException {

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#contents > div.input-group.mb-3 > div.custom-file")));
        driver.findElement(By.cssSelector("#contents > div.input-group.mb-3 > div.custom-file")).click();
        RobotHelper.uploadFile(Config.dateFilePath);

    }

    @And("click on refresh tax relief table button")
    public void click_on_refresh_tax_relief_table_button() throws InterruptedException {

        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.cssSelector("#contents > button.btn.btn-primary")).click();
    }

    @Then("all the records are displayed")
    public void all_the_records_are_displayed() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#contents > div.m-4")));
        WebElement table = driver.findElement(By.cssSelector("#contents > div.m-4 > table"));
        List<WebElement> tableRecords = table.findElements(By.tagName("tr"));
        List<Employee> csvRecords = CommonFunction.readCsvFile(Config.dateFilePath);

        //Remove header for assertion
        tableRecords.remove(0);
        Assert.assertEquals(tableRecords.size(), csvRecords.size());

        driver.quit();
    }

    @When("the clerk upload the file via uploadLargeFileForInsertionToDatabase API")
    public void the_clerk_upload_the_file_via_upload_large_file_for_insertion_to_database_api() {

        respUpload = Clerk.uploadLargeFile(Config.dateFilePath);

    }
    @Then("the file uploaded successfully")
    public void the_file_uploaded_successfully() {

        respUpload.then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}