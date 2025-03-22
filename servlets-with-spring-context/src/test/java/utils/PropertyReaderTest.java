package utils;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.Main;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.InitializationException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.PropertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderTest {

    private static final String EXISTING_PROPERTY_NAME = "test.property";

    private static final String NONEXISTING_PROPERTY_NAME = "nonexistent.property";

    private static final String EXISTING_PROPERTY_VALUE = "testValue";

    @Test
    void getProperty_ShouldReturnPropertyValue_WhenPropertyExists() {
        String actualValue = PropertyReader.getProperty(EXISTING_PROPERTY_NAME);
        assertEquals(EXISTING_PROPERTY_VALUE, actualValue);
    }

    @Test
    void getProperty_ShouldReturnNull_WhenPropertyDoesNotExist() {
        String actualValue = PropertyReader.getProperty(NONEXISTING_PROPERTY_NAME);
        assertNull(actualValue);
    }
}
