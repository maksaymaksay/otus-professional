package pages.otus;

import static java.time.Duration.ofSeconds;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthorizedMainPage extends MainPage {

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoginPage.class);

    public AuthorizedMainPage(WebDriver driver) {
        super(driver);
    }

    public CoursesPage clickOnAllCoursesBtn() {
        By allCoursesBtn = By.xpath("//a[contains(@class, 'transitional-main__courses-more')]");
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(allCoursesBtn));

        WebElement btn = driver.findElement(allCoursesBtn);
        Actions actions = new Actions(driver);
        actions.moveToElement(btn).build().perform();
        driver.findElement(allCoursesBtn).click();
        logger.info("Вход на страницу со всеми курсами осуществлен успешно");
        return new CoursesPage(driver);
    }
}
