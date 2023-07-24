package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.exceptions.DAOException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class PropertiesFile extends Properties {

    public void load(String fileName) throws DAOException {
        try (InputStream propertiesFile = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (propertiesFile == null) {
                throw new DAOException("Properties file '" + fileName + "' is missing.");
            }
            super.load(propertiesFile);
        } catch (IOException e) {
            throw new DAOException("Cannot load properties file '" + fileName + "'.", e);
        }
    }

}
