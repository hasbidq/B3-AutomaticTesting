package com.jtklearn.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.jtklearn.definitions",
        tags = "@Orang1",
        plugin = {"pretty", "html:target/cucumber-reports-orang1.html"}
)
public class RunnerOrang1 extends AbstractTestNGCucumberTests {
}
