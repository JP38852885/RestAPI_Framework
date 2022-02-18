package com.api.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtil {
TestContext testContext = new TestContext();
    private final static Logger log = LoggerFactory.getLogger(DBUtil.class);
    private String dbDriver, dbUrl, dbUserName, dbPassword, sqlQuery = null;
     private Connection connection = null;
     private Statement statement = null;
     private ResultSet resultSet = null;

    public ResultSet executeQuery(String query){
      try{
          dbUrl = CommonUtils.readProperties("config/config.properties").getProperty("dbUrl");
          dbUserName = CommonUtils.readProperties("config/config.properties").getProperty("dbUserName");
          dbPassword = CommonUtils.readProperties("config/config.properties").getProperty("dbPassword");
         Class.forName(CommonUtils.readProperties("config/config.properties").getProperty("dbDriver"));
         connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
          statement = connection.createStatement();
          resultSet=statement.executeQuery(query);
          log.info("DB connection successful");
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
        return resultSet;
    }
}
