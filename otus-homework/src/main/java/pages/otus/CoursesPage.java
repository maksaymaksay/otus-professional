package pages.otus;

import static java.time.Duration.ofSeconds;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import pages.otus.component.Course;
import pages.otus.utils.CourseFilterType;
import pages.otus.utils.DateTimeConverter;
import pages.otus.utils.MarkBeforeClickListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoursesPage extends AbstractPage {

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoginPage.class);

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    private List<Course> getCoursesNameAndBeginDate() {
        By courseName = By.xpath("//following-sibling::div[contains(@class, 'lessons__new-item-title')]");
        By courseBeginDate = By.xpath("//following-sibling::div[contains(@class, 'lessons__new-item-start')]");

        List<Course> courses = new ArrayList<>();

        int size = driver.findElements(courseBeginDate).size();
        for (int i = 0; i < size; i++) {
            driver.manage().timeouts().implicitlyWait(ofSeconds(2));

            String name = driver.findElements(courseName).get(i).getText();
            String rawDate = driver.findElements(courseBeginDate).get(i).getText();
            int indexOfElement = i;

            if (rawDate.matches("(.)(\\s)(\\d{1,2})(\\s).*")) {
                String trimDate = rawDate.trim();
                try {
                    String[] splitDate = trimDate.split(" ");
                    String numericMonth = DateTimeConverter.getNumericMonth(splitDate[2]);

                    String finDate = LocalDate.now().getYear() + "-" + numericMonth.concat("-" + splitDate[1]);
                    LocalDate courseStartDate = LocalDate.parse(finDate);

                    courses.add(new Course(name, courseStartDate, i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return courses;
    }

    public Course filterCourseByCourseName(String courseName) {
        List<Course> courses = getCoursesNameAndBeginDate();
        Optional<Course> filteredCourse = courses.stream()
                .filter(course -> course.getCourseName().equals(courseName))
                .findFirst();

        if (filteredCourse.isPresent()) {
            Course finalCourse = filteredCourse.get();
            System.out.println("Курс с названием - " + finalCourse.getCourseName() + ", найден.");
            return finalCourse;
        } else {
            throw new InvalidArgumentException("У курса с названием " + courseName + " отсутствует дата начала курса. Укажите другой курс.");
        }
    }

    public Course filterCourseByStartDate(CourseFilterType courseFilterType) {
        List<Course> courses = getCoursesNameAndBeginDate();
        Course filteredCourse;
        if (CourseFilterType.EARLIEST.equals(courseFilterType)) {
            Optional<Course> courseBefore = courses.stream()
                    .reduce((course, course2) -> course.getCourseBeginDate().isBefore(course2.getCourseBeginDate()) ? course : course2);
            if (courseBefore.isPresent()) {
                filteredCourse = courseBefore.get();
                System.out.println("Найден курс с названием - " + filteredCourse.getCourseName() + " и датой начала - "
                        + filteredCourse.getCourseBeginDate());
                return filteredCourse;
            }
        } else if (CourseFilterType.LATEST.equals(courseFilterType)) {
            Optional<Course> courseAfter = courses.stream()
                    .reduce((course, course2) -> course.getCourseBeginDate().isAfter(course2.getCourseBeginDate()) ? course : course2);
            if (courseAfter.isPresent()) {
                filteredCourse = courseAfter.get();
                System.out.println("Найден курс с названием - " + filteredCourse.getCourseName() + " и датой начала - "
                        + filteredCourse.getCourseBeginDate());
                return filteredCourse;
            }
        }
        throw new InvalidArgumentException("Курс не найден");
    }

    public CoursePage chooseFilteredCourse(Course filteredCourse) {
        // TODO: 24.04.2022 Доработать метод
        By courseNames = By.xpath("//following-sibling::div[contains(@class, 'lessons__new-item-title')]");
        int indexOfWebElement = filteredCourse.getIndexOfWebElement();

        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new MarkBeforeClickListener());
        eventFiringWebDriver.manage().window().maximize();
        eventFiringWebDriver.get("https://otus.ru");
        eventFiringWebDriver.findElements(courseNames).get(indexOfWebElement).click();
        return new CoursePage(eventFiringWebDriver);
    }
}
