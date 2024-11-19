package introduction;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
        plugin = {"json:target/cucumber-report.json"},
        features = "src/test/resources/features",
        glue = "stepdefs"
)

public class TestRunner {
}
