package org.OppenheimerTest.roles;

import io.restassured.response.Response;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.OppenheimerTest.stepDefinition.Config;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONString;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class Clerk {

    public static Response insert(Employee employee) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("birthday", employee.getBirthday());
        requestBody.put("gender", employee.getGender());
        requestBody.put("name", employee.getName());
        requestBody.put("natid", employee.getNatId());
        requestBody.put("salary", employee.getSalary());
        requestBody.put("tax", employee.getTaxPaid());

        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .header("Content-Type", "application/json")
                .body(requestBody.toString(1))
                .log().all()
                .post("/calculator/insert");
        response.then().log().all();

        return response;
    }

    public static Response insertMultiple(List<Employee> employeeList) {

        JSONArray requestBody = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        ListIterator<Employee> it = employeeList.listIterator();

        JSONObject requestPart;
        Employee empl;
        while(it.hasNext()) {

            requestPart = new JSONObject();
            empl = it.next();
            requestPart.put("birthday", empl.getBirthday());
            requestPart.put("gender", empl.getGender());
            requestPart.put("name", empl.getName());
            requestPart.put("natid", empl.getNatId());
            requestPart.put("salary", empl.getSalary());
            requestPart.put("tax", empl.getTaxPaid());
            jsonList.add(requestPart);
        }
        requestBody.putAll(jsonList);

        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .header("Content-Type", "application/json")
                .body(requestBody.toString(1))
                .log().all()
                .post("/calculator/insertMultiple");
        response.then().log().all();

        return response;
    }

    public static Response uploadLargeFile(String directory){

        File file = new File(directory);
        Response response = RestAssured.given()
                .baseUri(Config.baseUri)
                .header("Content-Type", "multipart/form-data")
                .multiPart(file)
                .log().all()
                .post("/calculator/uploadLargeFileForInsertionToDatabase");
        response.then().log().all();

        return response;

    }
}