package org.OppenheimerTest.utility;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.OppenheimerTest.roles.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Generator {

    public static int randomNumber(int min, int max) {

        Random rand = new Random();

        return rand.ints(min, max).findFirst().getAsInt();
    }

    public static int randomNumber(int scale) {

        Random rand = new Random();
        int min = 1;
        int max = 10;
        for(int x = 1; x < scale; x++) {

            min = min * 10;
            max = max * 10;
        }

        return rand.ints(min, max).findFirst().getAsInt();

    }

    public static LocalDate randomDate() {

        LocalDate start = LocalDate.now().minusYears(100);
        LocalDate end = LocalDate.now();

        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());

        return LocalDate.ofEpochDay(randomDay);
    }

    public static Employee randomEmployee() {

        Employee empl = new Employee();
        LocalDate birthday = randomDate();

        String day = birthday.getDayOfMonth() < 10 ? "0" + birthday.getDayOfMonth() : String.valueOf(birthday.getDayOfMonth());
        String month = birthday.getMonthValue() < 10 ? "0" + birthday.getMonthValue() : String.valueOf(birthday.getMonthValue());

        empl.setNatId(randomNumber(3) + "-" + randomNumber(2) + "-" + randomNumber(4))
                .setName("EmployeeTest-" + randomNumber(5))
                .setGender(randomNumber(1) > 5 ? "M" : "F")
                .setBirthday(day + month + birthday.getYear())
                .setSalary(randomNumber(2000, 20000) * 0.01)
                .setTaxPaid(randomNumber(100, 2000) * 0.01);

        return empl;
    }

    public static void createCSVFile(int numOfRecords) {

        PrintWriter pw = null;
        Employee empl;

        try {
            pw = new PrintWriter(".\\src\\test\\resources\\data\\data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        String columnNamesList = "natid,name,gender,salary,birthday,tax";
        builder.append(columnNamesList +"\n");

        for(int x = 0; x < numOfRecords; x++) {

            empl = randomEmployee();
            builder.append(empl.getNatId() + ",");
            builder.append(empl.getName() + ",");
            builder.append(empl.getGender() + ",");
            builder.append(empl.getSalary() + ",");
            builder.append(empl.getBirthday() + ",");
            builder.append(empl.getTaxPaid() + ",");
            builder.append('\n');

        }
        pw.write(builder.toString());
        pw.close();
    }
}
