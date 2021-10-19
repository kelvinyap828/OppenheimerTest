package org.OppenheimerTest.utility;

import org.OppenheimerTest.roles.Employee;

import java.math.BigDecimal;

public class TaxReliefCalculator {

    private double getVariable(int age){

        double variable;
        if(age <= 18){
            variable = 1;
        }else if (age <= 35){
            variable = 0.8;
        }else if (age <= 50){
            variable = 0.5;
        }else if (age <= 75){
            variable = 0.367;
        }else{
            variable = 0.05;
        }
        return variable;
    }

    private int getGenderBonus(String gender){

        return gender.equalsIgnoreCase("M") ? 0 : 500;
    }

    public double calcTaxRelief(Employee empl){

        double taxRelief = ((empl.getSalary() - empl.getTaxPaid()) * getVariable(empl.getAge())) + getGenderBonus(empl.getGender());
        System.out.println("Actual TaxRelief = " + taxRelief);

        if(taxRelief > 0 && taxRelief < 50.00){

            taxRelief = 50.00;

        }else if(BigDecimal.valueOf(taxRelief).scale() <= 2){

            taxRelief = Convertor.roundHalfUp(taxRelief, 0);

        }else {

            taxRelief = Convertor.roundHalfUp(taxRelief, 2);

        }

        return taxRelief;
    }
}
