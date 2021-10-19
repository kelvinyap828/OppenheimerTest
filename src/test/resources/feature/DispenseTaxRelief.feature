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
@All @DispenseTaxRelief
Feature: Dispense tax relief for working class hero

  @FunctionalTest @UI
  Scenario: Governor should be able to dispense tax relief for working class heroes via UI
    Given the governor has accessed to portal
    And the governor see there are 20 working class hero records
    When the governor click on "Dispense Now" button
    Then dispense page is displayed

  @FunctionalTest @API
  Scenario: Governor should be able to dispense tax relief for working class heroes via API
    Given the governor see there are 20 working class hero records
    When the governor dispense tax relief via API
    Then tax relief are successfully dispensed