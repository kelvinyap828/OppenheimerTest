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
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RetrieveTaxRelief {

    List<Employee> employeeList = new ArrayList<Employee>();

    Response respTaxRelief, respTaxReliefSummary;

    @Given("Bookkeeper see there are {int} working class hero records")
    public void bookkeeper_see_there_are_working_class_hero_records(int numOfRecords) {

        employeeList = CommonFunction.generateEmployeeList(numOfRecords);
        Clerk.insertMultiple(employeeList);

    }

    @When("Bookkeeper retrieves working class hero tax relief")
    public void bookkeeper_retrieves_working_class_hero_tax_relief() {
        respTaxRelief = Bookkeeper.getTaxRelief();
        respTaxRelief.then().assertThat().statusCode(200);
        respTaxReliefSummary = Bookkeeper.getTaxReliefSummary();
        respTaxReliefSummary.then().assertThat().statusCode(200);
    }

    @Then("tax relief for all the records are calculated")
    public void tax_relief_for_all_the_records_are_calculated() {

        List<Map<String, Object>> taxReliefList = respTaxRelief.then().extract().jsonPath().get();
        Map<String, String> taxReliefSummaryMap = respTaxReliefSummary.then().extract().jsonPath().get();
        double expectedSumTaxRelief = 0;

        //Verify tax relief response
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
}
