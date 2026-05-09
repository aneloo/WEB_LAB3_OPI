package org.example;

import org.example.validation.ArgumentValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArgumentValidatorTest {

    private static int testsStarted;
    private static int testsFinished;

    private Dot dot;

    private final String fieldName;
    private final float invalidValue;

    public ArgumentValidatorTest(String fieldName, float invalidValue) {
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    @Parameterized.Parameters(name = "поле={0}, некорректное значение={1}")
    public static Collection<Object[]> invalidValues() {
        return Arrays.asList(new Object[][]{
                {"x", Float.NaN},
                {"y", Float.NaN},
                {"r", Float.NaN},
                {"x", Float.POSITIVE_INFINITY},
                {"y", Float.NEGATIVE_INFINITY},
                {"r", Float.POSITIVE_INFINITY}
        });
    }

    @BeforeClass
    public static void beforeAllTests() {
        testsStarted = 0;
        testsFinished = 0;
    }

    @AfterClass
    public static void afterAllTests() {
        System.out.println("Запущено тестов" + testsStarted);
        System.out.println("Завершено тестов" + testsFinished);
    }

    @Before
    public void setUp() {
        testsStarted++;
        dot = createValidDot();
    }

    @After
    public void tearDown() {
        testsFinished++;
        dot = null;
    }

    @Test
    public void validateShouldNotThrowWhenDotIsValid() {
        ArgumentValidator.validate(dot);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateShouldThrowWhenDotIsNull() {
        ArgumentValidator.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateShouldThrowWhenFieldIsNaNOrInfinite() {
        setFieldValue(dot, fieldName, invalidValue);

        ArgumentValidator.validate(dot);
    }

    private static Dot createValidDot() {
        Dot dot = new Dot();
        dot.setX(1.0f);
        dot.setY(2.0f);
        dot.setR(3.0f);
        return dot;
    }

    private static void setFieldValue(Dot dot, String fieldName, float value) {
        switch (fieldName) {
            case "x":
                dot.setX(value);
                break;
            case "y":
                dot.setY(value);
                break;
            case "r":
                dot.setR(value);
                break;
            default:
                throw new IllegalArgumentException(fieldName);
        }
    }
}