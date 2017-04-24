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

public class DynamicProperties extends PropertyResourceConfigurer {
	private static PropertiesConfiguration configuration = null;
	private static PropertiesConfiguration googleConfiguration = null;

	public DynamicProperties() {
		super();
	}

	Resource appPropPath;
	Resource googleCaptchPath;

	public Resource getAppPropPath() {
		return appPropPath;
	}

	public void setAppPropPath(Resource appPropPath) {
		this.appPropPath = appPropPath;
	}

	public Resource getGoogleCaptchPath() {
		return googleCaptchPath;
	}

	public void setGoogleCaptchPath(Resource googleCaptchPath) {
		this.googleCaptchPath = googleCaptchPath;
	}

	public static synchronized String getProperty(final String key) {

		return (String) configuration.getProperty(key);
	}

	public static synchronized Boolean getBooleanProperty(final String key) {
		if (key.equalsIgnoreCase("googleReCaptchaEnabled")) {
			return Boolean.valueOf((String) googleConfiguration.getProperty(key));
		}
		return Boolean.valueOf((String) configuration.getProperty(key));
	}

	public static synchronized int getIntegerProperty(final String key) {
		return Integer.valueOf((String) configuration.getProperty(key));
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory arg0, Properties arg1) throws BeansException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanInitializationException {

		try {
			File externalPropFile = getAppPropPath().getFile();
			File internalPropFile = new File(this.getClass().getClassLoader().getResource("getpuk.properties").toURI());
			if (!externalPropFile.exists()) {
				// set new location
				this.setLocation(new FileSystemResource(internalPropFile));
				configuration = new PropertiesConfiguration(internalPropFile);
			} else {
				// Compare both files
				List<String> ex = getKeys(externalPropFile);
				List<String> in = getKeys(internalPropFile);
				if (!ex.containsAll(in)) {
					in.removeAll(ex);
					throw new MissingPropertyException("Extenal property file has follwing missing properties." + in);
				}
				if (ex.size() > in.size()) {
					ex.removeAll(in);
					log.info("server contains extra properties which are not required for the application " + ex);
				}
				configuration = new PropertiesConfiguration(externalPropFile.getAbsolutePath());
			}

			File googleExternalPropFile = getGoogleCaptchPath().getFile();
			File googleInternalPropFile = new File(
					this.getClass().getClassLoader().getResource("puk_recaptcha.properties").toURI());
			if (!googleExternalPropFile.exists()) {
				// set new location
				this.setLocation(new FileSystemResource(googleInternalPropFile));
				configuration = new PropertiesConfiguration(googleInternalPropFile);
			} else {
				googleConfiguration = new PropertiesConfiguration(googleExternalPropFile.getAbsolutePath());
			}

			super.postProcessBeanFactory(beanFactory);
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
			googleConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());

		} catch (Exception e) {
			StringBuilder msg = new StringBuilder("Reading from property fails.");
			log.info("Reading from property file fails. ");

			if (e instanceof MissingPropertyException)
				msg.append(e.getMessage());
			BeanInitializationException exception = new BeanInitializationException(msg.toString());
			throw exception;
		}

	}

	private final Log log = LogFactory.getLog("application_log");

	public List<String> getKeys(File file) throws IOException {
		Function<String, String> extract = (content) -> {
			if (content != null && !content.isEmpty() && content.contains("=") && !content.contains("#"))
				return content.substring(0, content.indexOf("="));
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
