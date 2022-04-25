import static factory.Browsers.getBrowserByStringName;
import static java.time.Duration.ofSeconds;

import factory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(BaseTest.class);

    @Before
    public void startUp() {
        driver = WebDriverFactory.create(getBrowserByStringName(System.getProperty("browser")));
        driver.manage().timeouts().implicitlyWait(ofSeconds(5));
        logger.info("Драйвер поднят");
    }

    @After
    public void end() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
