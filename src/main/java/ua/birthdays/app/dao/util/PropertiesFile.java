package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.exceptions.DBPropertiesFileException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * Custom extension of the {@link Properties} class for loading properties from a file.
 */
public final class PropertiesFile extends Properties {

    public void load(String fileName) {
        try (InputStream propertiesFile = getClass().getClassLoader()
                                                    .getResourceAsStream(fileName)) {
            if (propertiesFile == null) {
                throw new DBPropertiesFileException("Properties file '" + fileName + "' is missing.");
            }
            super.load(propertiesFile);
        } catch (IOException e) {
            throw new DBPropertiesFileException("Cannot load properties file '" + fileName + "'.", e);
        }
    }

}
