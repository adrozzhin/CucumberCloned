package Runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        //features = "src/test/resources/features/Login.feature",
        features = "src/test/resources/features/",
        // glue - connect implementation in steps to features
        // glue - path to steps package
        glue = "APISteps",
        // dryRun - check for implementation without execution (true)
        dryRun = false,
        // monochrome - it keeps console output easily readable
        monochrome = true,
        tags = "@APIWorkFlow",
        // strict = true  - it checks all steps definition (like dryRun)
        plugin = {"pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json",
                "rerun:target/failed.txt"}
)
public class APIRunner {
}