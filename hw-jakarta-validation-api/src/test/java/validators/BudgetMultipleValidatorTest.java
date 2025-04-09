package validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.itis.vhsroni.annotation.BudgetMultiple;

import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.validators.BudgetMultipleValidator;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BudgetMultipleValidatorTest {

    private BudgetMultipleValidator validator;
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

    @BeforeEach
    void beforeEach() {
        validator = new BudgetMultipleValidator();
        BudgetMultiple annotation = new BudgetMultiple() {
            @Override
            public int value() {
                return 100;
            }

            @Override
            public String message() {
                return "Бюджет должен быть кратен 100";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return BudgetMultiple.class;
            }
        };
        validator.initialize(annotation);
    }

    @Test
    void testIsValidWithNull() {
        assertTrue(validator.isValid(null, context));
    }

    @ParameterizedTest
    @MethodSource("provideValidBudgetValues")
    void testIsValidWithValidBudget(Double value) {
        assertTrue(validator.isValid(value, context));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidBudgetValues")
    void testIsValidWithInValidBudget(Double value) {
        assertFalse(validator.isValid(value, context));
    }

    private static Stream<Arguments> provideValidBudgetValues() {
        return Stream.of(
                Arguments.of(0.0),
                Arguments.of(100.0),
                Arguments.of(200.0),
                Arguments.of(500.0),
                Arguments.of(1000.0),
                Arguments.of(10000.0)
        );
    }

    private static Stream<Arguments> provideInvalidBudgetValues() {
        return Stream.of(
                Arguments.of(1.0),
                Arguments.of(50.0),
                Arguments.of(99.0),
                Arguments.of(101.0),
                Arguments.of(150.0),
                Arguments.of(999.0)
        );
    }
}
