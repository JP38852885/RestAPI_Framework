package com.api.framework.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Properties;

import static org.testng.AssertJUnit.assertTrue;


public class CommonUtils {
    static JSONObject obj = new JSONObject();
    static JSONArray jsonArray = new JSONArray();
  /*  public CommonUtils(TestContext testContext){
        super(testContext);
    }*/
    public static Properties readProperties(String fileName){
            FileInputStream fileInputStream=null;
            Properties properties = null;
            try{
                fileInputStream=new FileInputStream(fileName);
                properties=new Properties();
                properties.load(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try{
                    fileInputStream.close();
                } catch (IOException e) {

                }
            }
         return properties;
        }

    public static String generateStringFromJSONFile(String fileName) throws IOException {
        ClassLoader classLoader = CommonUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        File file = new File(classLoader.getResource(fileName).getFile());
        String absolutePath = file.getAbsolutePath();
        return new String(Files.readAllBytes(Paths.get(absolutePath)));
    }

    public static File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = CommonUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(((URL) resource).toURI());
        }

    }

    public static JSONObject convertStringToJsonObject(String response) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response);
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public void getAccessToken(String userName, String password){
        RequestSpecification requestSpec = RestAssured.with();
        requestSpec.given().contentType("application/json");
        requestSpec.headers("Authorization", "Basic  your-string-here");
        Response response = requestSpec.post("http://localhost:8082/oauth/token" + "token_resource" + "mobile" + "&password=" + "pin" + "&client_id=yourApp&grant_type=password");
        String responseMsg = response.asString();
        System.out.println(">> responseMsg=" + responseMsg);
        assertTrue("Missing access token",responseMsg.contains("access_token"));
        System.out.println(">> Get Access token RESPONSE: " + responseMsg);

       // DocumentContext doc = JsonPath.parse(responseMsg);
       // acessToken= doc.read("access_token");

        //System.out.println(" >> doc.read access_token= " + acessToken);
    }
    public static JSONArray convert(ResultSet resultSet) throws Exception {

        while (resultSet.next()) {
            int columns = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < columns; i++)
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                jsonArray.add(obj);
        }
        return jsonArray;
    }

}
