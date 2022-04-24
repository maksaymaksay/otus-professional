package pages.otus.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Course {

    private String courseName;
    private LocalDate courseBeginDate;
    private int indexOfWebElement;
}
