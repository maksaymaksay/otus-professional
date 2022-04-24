import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import pages.otus.MainPage;
import pages.otus.component.Course;
import pages.otus.utils.CourseFilterType;

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertNotNull;

public class FirstHomeworkTests extends BaseTest {
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(BaseTest.class);

    @Test
    //1. На главной странице Otus'a снизу найти список курсов(популярные курсы, специализации, рекомендации) и реализовать
    //1.1 Метод фильтр по названию курса
    //1.2 Метод выбора курса, стартующего раньше всех/позже всех (при совпадении дат - выбрать любой) при помощи reduce
    //2. Реализовать подсветку элементов перед нажатием, после нажатия вернуть данные в исходное состояние
    //3. Реализовать движение мыши при помощи и выбор курса при помощи библиотеки Actions
    //4. Фабрику (WebDriverFactory), которая будет получать значение из окружения и запускать соответствующий браузер Браузеры: Chrome, Firefox, Opera

    public void filterCourseByNameTest() {
        driver.manage().timeouts().implicitlyWait(ofSeconds(3));

        Course course = new MainPage(driver)
                .openOtus()
                .login()
                .authOtus()
                .clickOnAllCoursesBtn()
                .filterCourseByCourseName("Разработчик IoT");

        assertNotNull(course);
//-Dbrowser=chrome -Dprofile=test -Dlogin=jenya_maksaeva@mail.ru -Dpassword=Maksaeva
    }

    @Test
    public void filterCourseByDateTest() {
        driver.manage().timeouts().implicitlyWait(ofSeconds(3));

        Course course = new MainPage(driver)
                .openOtus()
                .login()
                .authOtus()
                .clickOnAllCoursesBtn()
                .filterCourseByStartDate(CourseFilterType.EARLIEST);

    }
}
