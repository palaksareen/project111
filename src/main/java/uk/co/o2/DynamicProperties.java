package uk.co.o2;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;


public class DynamicProperties {
	private static PropertiesConfiguration configuration = null;

	public DynamicProperties(String locationn) {
    	try {
            configuration = new PropertiesConfiguration(locationn);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
	}

	
	public static synchronized String getProperty(final String key) {
        return (String) configuration.getProperty(key);
    }
	
	public static synchronized Boolean getBooleanProperty(final String key) {
        return Boolean.valueOf((String)configuration.getProperty(key));
    }
}
