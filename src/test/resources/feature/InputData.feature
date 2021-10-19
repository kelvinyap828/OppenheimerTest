#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@All @InputData
Feature: Input working class hero data

  @FunctionalTest @API
  Scenario: Clerk should be able to insert single working class hero record via API
    Given clerk has a working class hero record
    When the clerk insert the record via insert API
    Then record inserted successfully

  @FunctionalTest @API
  Scenario: Clerk should be able to insert list of working class hero records via API
    Given clerk has 20 working class hero records in list
    When the clerk insert all the records via insertMultiple API
    Then record inserted successfully

  @FunctionalTest @UI @test
  Scenario: Clerk should be able to upload working class hero record in csv file via UI
    Given clerk has a csv file of 20 working class hero record
    And the clerk has accessed to portal
    When the clerk upload the file via UI
    And click on refresh tax relief table button
    Then all the records are displayed

  @FunctionalTest @API
  Scenario: Clerk should be able to upload working class hero record in csv file via API
    Given clerk has a csv file of 20 working class hero record
    When the clerk upload the file via uploadLargeFileForInsertionToDatabase API
    Then the file uploaded successfully

#  @FunctionalTest @API
#  Scenario Outline: Verify the endpoint validation
#    When the clerk insert invalid <value> for <field>
#    Then error message is returned
#
#    Examples:
#      |field|value|
#      |birthday|32051948|
##      |birthday|30022008|
#      |birthday|15131988|
##      |birthday|15022500|
##      |gender|X|
#      |gender|Female|
##      |natid|32155145|
##      |salary|-1|
#      |salary|0|
#      |tax|-1|
##      |tax|9999999|