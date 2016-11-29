package uk.co.o2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import uk.co.o2.utility.exception.MissingPropertyException;


public class DynamicProperties extends PropertyResourceConfigurer{
	private static PropertiesConfiguration configuration = null;

	
	public DynamicProperties() {
		super();
	}

	Resource locationn;

	public Resource getLocationn() {
		return locationn;
	}

	public void setLocationn(Resource locationn) {
		this.locationn = locationn;
	}

	public static synchronized String getProperty(final String key) {
		return (String) configuration.getProperty(key);
	}

	public static synchronized Boolean getBooleanProperty(final String key) {
		return Boolean.valueOf((String)configuration.getProperty(key));
	}

	public static synchronized int getIntegerProperty(final String key) {
		return Integer.valueOf((String)configuration.getProperty(key));
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory arg0, Properties arg1) throws BeansException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanInitializationException{

		try {
			File externalPropFile=getLocationn().getFile();
			File internalPropFile = new File(this.getClass().getClassLoader().getResource("getpuk.properties").toURI());
			if(!externalPropFile.exists()){
				//copy from source to destination
				//File srcFile=new File(this.getClass().getClassLoader().getResource("getpuk.properties").toURI());
				//FileUtils.copyFile(srcFile,externalPropFile);
				//set new location 
				this.setLocation(new FileSystemResource(internalPropFile));
				configuration = new PropertiesConfiguration(internalPropFile);
			}
			else{
				// Compare both files
				List<String> ex = getKeys(externalPropFile);
				List<String> in = getKeys(internalPropFile);
				if(! ex.containsAll(in)){
					in.removeAll(ex);
					throw new MissingPropertyException("Extenal property file has follwing missing properties."+in);
				}
				if(ex.size() > in.size()){
					ex.removeAll(in);
					log.debug("server contains extra properties which are not required for the application "+ex);
				}
				configuration = new PropertiesConfiguration(externalPropFile.getAbsolutePath());				
			}
			super.postProcessBeanFactory(beanFactory);
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (Exception e) {
			StringBuilder msg=new StringBuilder("Reading from property fails.");
			e.printStackTrace();
			if(e instanceof MissingPropertyException)
				msg.append(e.getMessage());
			BeanInitializationException exception=new BeanInitializationException(msg.toString());
			throw exception;
		}

	}
	private final Log log = LogFactory.getLog("application_log");

	public List<String> getKeys(File file) throws IOException {
		Function<String, String> extract=(content)-> {
			if(content != null && !content.isEmpty()&& content.contains("=") && !content.contains("#"))
				return 	content.substring(0,content.indexOf("="));
			return "";
		};

		return FileUtils.readLines(file, "UTF-8").stream().map(extract).collect(Collectors.toList());
	}

	public static PropertiesConfiguration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(PropertiesConfiguration configuration) {
		DynamicProperties.configuration = configuration;
	}

}
