package validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.validators.WithinYearValidator;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WithinYearValidatorTest {

    public static final LocalDate TODAY = LocalDate.now();

    public static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    public static final LocalDate TODAY_PLUS_6_MONTH = LocalDate.now().plusMonths(6);

    public static final LocalDate TODAY_PLUS_355_DAYS = LocalDate.now().plusYears(1).minusDays(1);

    public static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

    public static final LocalDate TODAY_PLUS_YEAR = LocalDate.now().plusYears(1);

    public static final LocalDate TODAY_PLUS_YEAR_AND_DAY = LocalDate.now().plusYears(1).plusDays(1);

    private final WithinYearValidator validator = new WithinYearValidator();
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

    @Test
    void testIsValidNullValue() {
        assertTrue(validator.isValid(null, context));
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testIsValidVariousDates(LocalDate date, boolean expected) {
        assertEquals(expected, validator.isValid(date, context));
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(TOMORROW, true),
                Arguments.of(TODAY_PLUS_6_MONTH, true),
                Arguments.of(TODAY_PLUS_355_DAYS, true),
                Arguments.of(TODAY, false),
                Arguments.of(YESTERDAY, false),
                Arguments.of(TODAY_PLUS_YEAR, true),
                Arguments.of(TODAY_PLUS_YEAR_AND_DAY, false)
        );
    }
}
