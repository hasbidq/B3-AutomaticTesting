package com.jtklearn.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.jtklearn.definitions",
        plugin = {"pretty", "html:target/cucumber-reports-semua.html"}
)
public class RunnerSemua extends AbstractTestNGCucumberTests {
}
