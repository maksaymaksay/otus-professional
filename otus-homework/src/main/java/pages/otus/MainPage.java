package pages.otus;

import factory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static factory.Browsers.getBrowserByStringName;
import static java.time.Duration.ofSeconds;

public class MainPage extends AbstractPage {
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(MainPage.class);

    private By loginAndRegistrationButton = By.cssSelector("button.header2__auth.js-open-modal");

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage openOtus() {
        driver.get("https://otus.ru/");
        logger.info("Страница Отуса открыта");
        return this;
    }

    public LoginPage login() {
        driver.findElement(loginAndRegistrationButton).click();
        return new LoginPage(driver);
    }
}
