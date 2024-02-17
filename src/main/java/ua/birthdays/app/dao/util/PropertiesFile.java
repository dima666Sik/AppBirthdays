package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.exceptions.DBPropertiesFileException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public final class PropertiesFile {
    private static final Properties PROPERTIES = new Properties();

    private PropertiesFile() {
    }

    public static void load(String fileName) {
        try (var propertiesFile = PropertiesFile.class.getClassLoader()
                                                      .getResourceAsStream(fileName)) {
            if (propertiesFile == null) {
                throw new DBPropertiesFileException("Properties file '" + fileName + "' is missing.");
            }
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            throw new DBPropertiesFileException("Cannot load properties file '" + fileName + "'.", e);
        }
    }

    public static String getProperty(String property) {
        return PROPERTIES.getProperty(property);
    }

}
