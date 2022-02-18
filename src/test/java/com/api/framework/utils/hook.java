package com.api.framework.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;


public class hook {

    @Before
    public void beforeScenario(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(CommonUtils.readProperties("config/config.properties").getProperty("baseURI")).
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

    }

    @After
    public void afterScenario(){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }
}