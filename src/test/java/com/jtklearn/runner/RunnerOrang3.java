package com.jtklearn.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.jtklearn.definitions",
        tags = "@Orang3",
        plugin = {"pretty", "html:target/cucumber-reports-orang3.html"}
)
public class RunnerOrang3 extends AbstractTestNGCucumberTests {
}
