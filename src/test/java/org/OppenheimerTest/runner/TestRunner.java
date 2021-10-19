package org.OppenheimerTest.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        glue = {"org.OppenheimerTest.stepDefinition"},
        features = {"src/test/resources/feature"},
        plugin = { "pretty", "html:target/cucumber-reports.html" },
        tags = "@InputData"
)
public class TestRunner extends AbstractTestNGCucumberTests{

}
