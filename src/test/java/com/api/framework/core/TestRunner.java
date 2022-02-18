package com.api.framework.core;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


public class TestRunner {

@CucumberOptions(tags= "@regressionTest",
        features="src/test/java/com.api.framework/features",
        glue = "stepDefinitions"
)

    public class testRunner extends AbstractTestNGCucumberTests{};

}
