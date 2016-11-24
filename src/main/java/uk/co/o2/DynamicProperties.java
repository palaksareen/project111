package uk.co.o2;

import java.io.File;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.core.io.Resource;


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
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		try {
			File externalPropFile=getLocationn().getFile();
			System.out.println("\n\n This is wonderful jitendra :: "+getLocationn().getFile().getAbsolutePath()+"\t"+getLocationn().getFile().exists());
			if(!externalPropFile.exists()){
				//copy from source to destination
				File srcFile=new File(this.getClass().getClassLoader().getResource("getpuk.properties").toURI());
				FileUtils.copyFile(srcFile,externalPropFile);
			}
			super.postProcessBeanFactory(beanFactory);
			
			configuration = new PropertiesConfiguration(externalPropFile.getAbsolutePath());
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

		} catch (Exception e) {
			System.out.println("1 >>>>>"+e.getMessage());
			e.printStackTrace();
		}

	}

}
