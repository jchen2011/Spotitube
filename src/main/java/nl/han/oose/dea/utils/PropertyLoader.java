package nl.han.oose.dea.utils;

import nl.han.oose.dea.services.exceptions.InternalServerError;

import java.util.Properties;

/**
 * Class for the automated loading of property within a properties file
 * within the resource folder.
 */
public class PropertyLoader {

    private Properties property = new Properties();

    /**
     * Method to load the propertyFileName from the resource folder.
     * Use the relative path from the resource file as the path and extension for the name.
     * Will throw {@link InternalServerError} when the properties file cannot be found.
     *
     * @param propertyFileName The name of the properties file with path and extension as a {@link String}
     */
    public void load(String propertyFileName) {
        try {
            property.load(getClass().getClassLoader().getResourceAsStream(propertyFileName));
        } catch (Exception e) {
            Log.console("Failed to find properties file by the name of " + propertyFileName + ".", e);
            throw new InternalServerError();
        }
    }

    /**
     * Get a property from a .properties file with a key.
     * Will throw {@link InternalServerError} when the key cannot be found in
     * the .properties file.
     *
     * @param key The key as a {@link String}
     * @return The value of the key inside the properties file as a {@link String}
     */
    public String getProperty(String key) {
        String value =  property.getProperty(key);

        if (value == null) {
            Log.console("Failed to find key by the name of " + key + ".", new RuntimeException());
            throw new InternalServerError();
        }

        return value;
    }

    /**
     * Setter for a {@link Properties} that is already preloaded.
     *
     * @param property The {@link Properties} to be set
     */
    public void setProperty(Properties property) {
        this.property = property;
    }
}
