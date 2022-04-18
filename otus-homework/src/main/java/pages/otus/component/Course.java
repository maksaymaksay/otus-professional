package pages.otus.component;

import org.openqa.selenium.WebElement;

import java.time.LocalDate;

public class Course {

    private String courseName;
    private LocalDate courseBeginDate;

    public Course(String courseName, LocalDate courseBeginDate) {
        this.courseName = courseName;
        this.courseBeginDate = courseBeginDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getCourseBeginDate() {
        return courseBeginDate;
    }

    public void setCourseBeginDate(LocalDate courseBeginDate) {
        this.courseBeginDate = courseBeginDate;
    }
}
