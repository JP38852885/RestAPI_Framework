package com.api.framework.stepDefinitions;

import com.api.framework.utils.TestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseStepDefinition {

    protected TestContext testContext;

    private final Logger log = LoggerFactory.getLogger(BaseStepDefinition.class);

    public BaseStepDefinition(TestContext testContext){
        this.testContext=testContext;
    };

}
