package com.api.framework.utils;

//import com.api.pojos.employee;

import java.util.HashMap;

public class TestContext {

    //com.api.pojos.employee employee = new employee();
    //DBUtil dbUtil = new DBUtil();
 private ThreadLocal<HashMap<String, Object>> testContext = new ThreadLocal<HashMap<String, Object>>(){
     @Override
     protected HashMap<String, Object> initialValue() {
         return new HashMap<String, Object>();
         }
    } ;

 public <T> T get(String name){
     return (T) testContext.get().get(name);
    }

 public <T> T set(String name, T object){
     testContext.get().put(name, object);
     return object;
 }

  public <T> T delete(String name){
     return (T) testContext.get().remove(name);
  }

    @Override
    public String toString() {
        return testContext.get().toString();
    }
}
