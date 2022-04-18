package pages.otus;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.otus.component.Course;
import pages.otus.utils.DateTimeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import static java.time.Duration.ofSeconds;

public class AuthorizedMainPage extends MainPage {

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoginPage.class);

    public AuthorizedMainPage(WebDriver driver) {
        super(driver);
    }

    public Course filterCourseByCourseName(String courseName) {
        List<Course> courses = getCoursesNameAndBeginDate();
        Optional<Course> filteredCourse = courses.stream()
                .filter(course -> course.getCourseName().equals(courseName))
                .findFirst();

        if (filteredCourse.isPresent()) {
            return filteredCourse.get();
        } else {
            throw new InvalidArgumentException("Курс с названием " + courseName + " не найден");
        }
    }

    public Course filterCourseByStartDate(Predicate<Course> predicate) {
        List<Course> courses = getCoursesNameAndBeginDate();
        Optional<Course> filteredCourse = courses.stream()
                .filter(predicate)
                .findFirst();

        if (filteredCourse.isPresent()) {
            return filteredCourse.get();
        } else {
            throw new InvalidArgumentException("Курс не найден");
        }
    }

//    public CoursePage clickOnCourse(){
//        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(5));
//        wait.until(ExpectedConditions.elementToBeClickable(mainProfile));
//
//        WebElement icon = driver.findElement(mainProfile);
//        Actions actions = new Actions(driver);
//        actions.moveToElement(icon).build().perform();
//        driver.findElement(myProfileBtn).click();
//        logger.info("Вход в личный кабинет осуществлен успешно");
//        return new LKPage(driver);
//        }

    private List<Course> getCoursesNameAndBeginDate() {
        By courseItems = By.xpath("//div[contains(@class, 'lessons__new-item-container')]");

        By courseName = By.xpath("//following-sibling::div[contains(@class, 'lessons__new-item-title')]");
        By courseBeginDate = By.xpath("//following-sibling::div[contains(@class, 'lessons__new-item-bottom')]");

        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < driver.findElements(courseItems).size(); i++) {
            driver.manage().timeouts().implicitlyWait(ofSeconds(3));
            String name = driver.findElements(courseItems).get(i).findElement(courseName).getText();
            String rawDate = driver.findElements(courseItems).get(i).findElement(courseBeginDate).getText();

            String trimDate = rawDate.trim();
            try {
                String[] splitDate = trimDate.split(" ");
                String numericMonth = DateTimeConverter.getNumericMonth(splitDate[2]);

                String finDate = LocalDate.now().getYear() + "-" + numericMonth.concat("-" + splitDate[1]);
                LocalDate courseStartDate = LocalDate.parse(finDate);

                courses.add(new Course(name, courseStartDate));
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.error(e.getMessage());
            }

        }
        return courses;
    }
}
