package com.api.framework.stepDefinitions;

import com.api.framework.utils.TestContext;

import static io.restassured.RestAssured.given;

public class testtStepDef extends BaseStepDefinition{
    public testtStepDef(TestContext testContext) {
        super(testContext);
    }
 public void mtest(){

        given().basePath("").body("").
                when().post("").
                then().extract().response().getBody().prettyPrint();
 }

}
