package com.api.framework.stepDefinitions;

import com.api.framework.utils.CommonUtils;
import com.api.framework.utils.DBUtil;
import com.api.framework.utils.TestContext;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class employeeStepDef extends BaseStepDefinition{
    //employee employee = new employee();
    DBUtil dbUtil = new DBUtil();
    String reqPayload = null;
    String responseString = null;
       ResultSet resultSet = null;
    JSONObject dbresultJsonObj=null;
    JSONArray jsonArray=null;
    Response response = null;
    String dbResult=null;
    public employeeStepDef(TestContext testContext){
        super(testContext);
    }
    private final Logger log = LoggerFactory.getLogger(BaseStepDefinition.class);

    @Given("I send request using {string} Method with payload {string}")
    public void IsendrequestusingMethodwithpayload(String httpMethod, String payloadPath) throws IOException {
       // converting JSON to String
        reqPayload= CommonUtils.generateStringFromJSONFile(payloadPath);
        testContext.set("requestPayload",reqPayload );
        log.info("request payload is:"+ reqPayload);
    if(httpMethod.equalsIgnoreCase("GET")) {
            responseString = given().
                body(reqPayload).
                when().
                get("").
                then().
                extract().body().asString();
        } else if(httpMethod.equalsIgnoreCase("DELETE")) {
             responseString = given().
                body(reqPayload).
                when().
                get("").
                then().
                extract().body().asString();
         } else if(httpMethod.equalsIgnoreCase("POST")){
             response = given().port(8083).
                     baseUri("http://localhost").basePath("/employee").
                     auth().oauth2("b399113a-7f11-4a21-a5f1-9ae0446f36eb").
                   /* auth().preemptive().basic("mobile", "pin").
                     header("Authorization","bW9iaWxlOnBpbg==").
                     //header("Authorization", "bearer "+"bdbeb3dc-94d4-4c42-b585-532fdfc6d18d").*/
                body(reqPayload).
                when().
                post("/add");
        } else if(httpMethod.equalsIgnoreCase("PUT")){
             responseString = given().
                body(reqPayload).
                when().
                put("/update").
                then().
                extract().body().asString();
    }
        testContext.set("response", response);
        log.info("response is:"+ response);
    }

    @Then("Verify Status code is {int}")
    public void verify_status_code_is(int statusCode) throws IOException, ParseException {

        Assert.assertEquals(response.statusCode(), statusCode);

       // resJsonObject = CommonUtils.convertStringToJsonObject(testContext.get(responseString));
    }

    @Then("Verify Response time is less than {int} sec")
    public void response_time_is_less_than_sec(int resTime) {

        Assert.assertTrue(response.timeIn(TimeUnit.SECONDS)<resTime);
    }

   // @Then()
    public void validateJsonSchema(){
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
        //get("/events?id=390").then().assertThat()
               // .body(matchesJsonSchemaInClasspath("event_0.json")
                       // .using(jsonSchemaFactory));
    }
    @Then("Verify response details with DB with query {string}")
    public void verify_response_details_with_db_with_below_query(String query) throws Exception {
        resultSet = dbUtil.executeQuery(query);
        jsonArray=CommonUtils.convert(resultSet);
        dbresultJsonObj= (JSONObject) jsonArray.get(0);

        Assert.assertTrue(dbresultJsonObj.get("emp_id").toString().equalsIgnoreCase("13"));
        Assert.assertTrue(dbresultJsonObj.get("emp_firstname").toString().equalsIgnoreCase("fn13"));
        Assert.assertTrue(dbresultJsonObj.get("emp_lastname").toString().equalsIgnoreCase("ln13"));
        Assert.assertTrue(dbresultJsonObj.get("emp_gender").toString().equalsIgnoreCase("male"));
        Assert.assertTrue(dbresultJsonObj.get("emp_phone").toString().equalsIgnoreCase("1234567890"));
        Assert.assertTrue(dbresultJsonObj.get("isnewemployee").toString().equalsIgnoreCase("0"));

    }
}
