Feature: Test the CRUD operations of Employee API

@positive
  Scenario: To test Get Employees response Records matches DB
    Given I send request using "POST" Method with payload "payloads/Employee_V1/POST/ValidData.json"
    Then Verify Status code is 201
    And Verify Response time is less than 5 sec
    And Verify response details with DB with query "select * from employee WHERE emp_id='13';"



  @positive
  Scenario: To test Get Employees response Records matche DB
    Given I send request using "POST" Method with payload "payloads/Employee_V1/POST/emp_ID-invalidLenght.json"
    Then Verify Status code is 500
    #And Verify Response time is less than 5 sec
    #And Verify response details with DB with query "select * from employee WHERE emp_id='13';"
@negative
  Scenario: To test Get Employees response Records matches DB
    Given I send request using "POST" Method with payload "payloads/Employee_V1/POST/EmpID_InValidData-min-1-lenght.json"
    Then Verify Status code is 400
   # And Verify Response time is less than 5 sec
    #And Verify response details with DB with query "select * from employee WHERE emp_id='13';"

