# OppenheimerTest
## Framework
The test cases are managed and running in cucumber-testng framework, API calls are using Rest-Assured framework and UI automated in Selenium.

## Structure
### Roles
Since this is to simulate the scenario where there are three individual working on the working class heroes data in the application
Therefore 4 roles are created in and action of each roles are defined
* Bookkeeper, will only calls
 * /calculator/taxRelief
 * /calculator/taxReliefSummary
* Clerk, will only calls
 * /calculator/insert
 * /calculator/insertMultiple
 * /calculator/uploadLargeFileForInsertionToDatabase
* Employee (working class hero)
 * holds the required data for execution
* Governor, will only calls
 * /dispense

### Feature & Step Definition
As there are 3 features mentioned, 3 feature files are created
* InputData (Clerk)
* RetrieveTaxRelief (Bookkeeper)
* DispenseTaxRelief (Gorvernor)

And each feature has a designated step definition, to the functional test of each individual feature

To extend the coverage, a E2E feature file is also created with its designated step definition.

### Utility
* Generator - To generate all the data required
* Convertor - To convert all values required in execution for assertion or request call
* RobotHelper - To handle window popup
* TaxReliefCalculator - To handle the calculation for each employee generated
* CommonFunction - To include all the method that was being used frequently

### Config
This is to keep a central value to ease up execution in case of changing environment, folder structure and etc.

### Test Runner
Test Runner would execute based on the feature, glue and tags given and generate a cucumber report for each execution.
Each test case in feature file are tagged based on the coverage of the test case, so that execution could be more specific to needs

To execute test case, simply update the tags in Test Runner and run it, for e.g.
> tags = "@InputData"

### Report
When execution completed, a native cucumber report (/target/cucumber-reports.html) will be generated

## Note
Currently there is a glitch when executing @All causing one of the test case failed, but executing by feature, or single test case has no issue.
