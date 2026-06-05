package com.jtklearn.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.jtklearn.definitions",
        tags = "@Orang2",
        plugin = {"pretty", "html:target/cucumber-reports-orang2.html"}
)
public class
RunnerOrang2 extends AbstractTestNGCucumberTests {
}
