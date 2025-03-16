package ru.kpfu.itis.vhsroni.clinicsemestrovka.utils;

import lombok.experimental.UtilityClass;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.Main;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.InitializationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertyReader {

    private final Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream db = Main.class.getClassLoader().getResourceAsStream("properties/app.properties");
            properties.load(db);
        } catch (IOException e) {
            throw new InitializationException("Error occurred during initialization", e);
        }
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}