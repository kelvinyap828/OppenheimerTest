package org.OppenheimerTest.roles;


import java.time.LocalDate;
import java.time.Period;

import org.OppenheimerTest.utility.Convertor;
import org.OppenheimerTest.utility.TaxReliefCalculator;

public class Employee extends TaxReliefCalculator {

    private String natId;
    private String name;
    private String gender;
    private String birthday;
    private double salary;
    private double taxPaid;
    private int age = -1;
    private String taxRelief;

    public Employee setNatId(String natId) {

        this.natId = natId;

        return this;
    }

    public Employee setName(String name) {

        this.name = name;

        return this;
    }

    public Employee setGender(String gender) {

        this.gender = gender.toUpperCase();

        return this;
    }

    public Employee setBirthday(String date) {

        this.birthday = date;

        return this;
    }

    public Employee setSalary(double salary) {

        this.salary = Convertor.roundHalfUp(salary, 2);

        return this;
    }

    public Employee setTaxPaid(double taxPaid) {

        this.taxPaid = Convertor.roundHalfUp(taxPaid, 2);

        return this;
    }

    public String getNatId() {

        return this.natId;
    }

    public String getName() {

        return this.name;
    }

    public String getGender() {

        return this.gender;
    }

    public String getBirthday() {

        return this.birthday;
    }

    public double getSalary() {

        return this.salary;
    }

    public double getTaxPaid() {

        return this.taxPaid;
    }

    public int getAge(){

        if(age == -1) {
            //format: ddmmyyyy
            String birthday = getBirthday();
            int day = Integer.valueOf(birthday.substring(0, 2));
            int month = Integer.valueOf(birthday.substring(2, 4));
            int year = Integer.valueOf(birthday.substring(4));

            LocalDate birthdate = LocalDate.of(year, month, day);
            Period period = Period.between(birthdate, LocalDate.now());

            this.age = period.getYears();
        }

        return this.age;
    }

    public String getTaxRelief(){

        if(taxRelief == null) {
            taxRelief = String.valueOf(calcTaxRelief(this));

            if (taxRelief.substring(taxRelief.indexOf(".") + 1, taxRelief.length()).length() < 2) {

                taxRelief = taxRelief + "0";

            }
        }
        return taxRelief;

    }
}