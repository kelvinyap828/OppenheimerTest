package org.OppenheimerTest.utility;

import org.OppenheimerTest.roles.Employee;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommonFunction {

    public static List<Employee> generateEmployeeList(int numOfRecords){

        List<Employee> employeeList = new ArrayList<>();

        for(int x = 0; x < numOfRecords; x++) {

            employeeList.add(Generator.randomEmployee());
        }

        employeeList.forEach((e) -> System.out.println(e.getName() + " " +
                e.getNatId() + " " +
                e.getGender() + " " +
                e.getBirthday() + " " +
                e.getSalary() + " " +
                e.getTaxPaid() + " " +
                e.getAge() + " " +
                e.getTaxRelief()));

        return employeeList;
    }

    public static Employee generateEmployee(){

        Employee employee = Generator.randomEmployee();

        System.out.println(employee.getName() + " " +
                employee.getNatId() + " " +
                employee.getGender() + " " +
                employee.getBirthday() + " " +
                employee.getSalary() + " " +
                employee.getTaxPaid() + " " +
                employee.getAge() + " " +
                employee.getTaxRelief());

        return employee;
    }
/*
    public static WebDriver getDriver(){

        System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

        return driver;
    }
*/
    public static List<Employee> readCsvFile(String filePath){

        List<Employee> dataListFromFile = null;
        try{

            File file = new File(filePath);
            InputStream inputFileStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream));

            // skip the header of the csv
            dataListFromFile = br.lines().skip(1).map((line) -> {
                //natid,name,gender,salary,birthday,tax
                String[] record = line.split(",");
                Employee e = new Employee();
                e.setNatId(record[0]);
                e.setName(record[1]);
                e.setGender(record[2]);
                e.setSalary(Double.parseDouble(record[3]));
                e.setBirthday(record[4]);
                e.setTaxPaid(Double.parseDouble(record[5]));

                return e;
            }).collect(Collectors.toList());
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataListFromFile;
    }
}
