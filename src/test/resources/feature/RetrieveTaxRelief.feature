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
@All @RetrieveTaxRelief
Feature: Retrieve tax relief for working class hero

  @FunctionalTest @API
  Scenario: Bookkeeper should be able to retrieve tax relief for each working class hero
    Given Bookkeeper see there are 20 working class hero records
    When Bookkeeper retrieves working class hero tax relief
    Then tax relief for all the records are calculated