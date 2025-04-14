package dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.itis.vhsroni.dto.TaskDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskDtoTest {

    public static final String VALID_TITLE = "VALID TITLE";

    public static final String INVALID_TITLE = "";

    public static final String VALID_DESCRIPTION = "VALID DESC";

    public static final int VALID_PRIORITY = 3;

    public static final LocalDate VALID_END_DATE = LocalDate.now().plusDays(1);

    public static final LocalDate INVALID_END_DATE = LocalDate.now().minusDays(1);

    public static final double VALID_BUDGET = 100.0;

    public static final double INVALID_BUDGET = 99.0;

    public static final String VALID_EMPLOYEE_NAME = "Veronika";

    public static final String VALID_EMPLOYEE_POSITION = "dev";

    public static final String VALID_EMPLOYEE_EMAIL = "test@mail.ru";

    public static final String INVALID_EMPLOYEE_EMAIL = "mail";

    public static final String VALID_EMPLOYEE_PHONE = "+70000000000";

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTaskDtoSuccess() {
        TaskDto taskDto = TaskDto.builder()
                .title(VALID_TITLE)
                .description(VALID_DESCRIPTION)
                .priority(VALID_PRIORITY)
                .endDate(VALID_END_DATE)
                .budget(VALID_BUDGET)
                .name(VALID_EMPLOYEE_NAME)
                .position(VALID_EMPLOYEE_POSITION)
                .email(VALID_EMPLOYEE_EMAIL)
                .phone(VALID_EMPLOYEE_PHONE)
                .build();

        var violations = validator.validate(taskDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidTitle() {
        TaskDto taskDto = TaskDto.builder()
                .title(INVALID_TITLE)
                .description(VALID_DESCRIPTION)
                .priority(VALID_PRIORITY)
                .build();

        var violations = validator.validate(taskDto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("title"))
                .count());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void testInvalidPriority(int priority) {
        TaskDto taskDto = TaskDto.builder()
                .title(VALID_TITLE)
                .priority(priority)
                .build();
        var violations = validator.validateProperty(taskDto, "priority");
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidEndDate() {
        TaskDto taskDto = TaskDto.builder()
                .title(VALID_TITLE)
                .endDate(INVALID_END_DATE)
                .build();

        var violations = validator.validateProperty(taskDto, "endDate");
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidBudget() {
        TaskDto taskDto = TaskDto.builder()
                .title(VALID_TITLE)
                .budget(INVALID_BUDGET)
                .build();

        var violations = validator.validateProperty(taskDto, "budget");
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        TaskDto taskDto = TaskDto.builder()
                .title(VALID_TITLE)
                .email(INVALID_EMPLOYEE_EMAIL)
                .build();

        var violations = validator.validateProperty(taskDto, "email");
        assertFalse(violations.isEmpty());
    }
}