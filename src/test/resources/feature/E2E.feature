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
@All @E2E
Feature: End to End flow which involved all the roles

  @Regression
  Scenario: End to end API test
    Given Clerk has inserted working class hero records via all the channels
    And Bookkeeper has reported the figure
    When Governor dispenses the tax relief
    Then tax relief for all the records are dispensed successfully