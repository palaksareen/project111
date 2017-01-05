package uk.co.o2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtils.class, DynamicProperties.class,PropertiesConfiguration.class})
public class DynamicPropertiesTest {

	@Mock
	Resource locationn;


	
		
	@Test
	public void readProperityFile(){
		MockitoAnnotations.initMocks(this);

		ConfigurableListableBeanFactory beanFactory =mock(ConfigurableListableBeanFactory.class);
		File mockExternalFile =mock(File.class);
		try {
			when(locationn.getFile()).thenReturn(mockExternalFile);
			when(mockExternalFile.exists()).thenReturn(true);


			PowerMockito.mockStatic(FileUtils.class);

			when(FileUtils.readLines(mockExternalFile, "UTF-8")).thenReturn(Arrays.asList("reCaptchaServiceUrl=http://localhost:8091/fake/recaptcha/","cache.enabled=false","cache.11enabled=false"));

			//PowerMockito.mockStatic(PropertiesConfiguration.class);
			PropertiesConfiguration configuration = mock(PropertiesConfiguration.class);
			DynamicProperties.setConfiguration(configuration);
			DynamicProperties dynamicProperties=new DynamicProperties();
			dynamicProperties.setLocationn(locationn);
			
			PowerMockito.whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments(mockExternalFile.getAbsolutePath()).thenReturn(configuration);
			
			FileChangedReloadingStrategy strategy=mock(FileChangedReloadingStrategy.class);
			PowerMockito.whenNew(FileChangedReloadingStrategy.class).withNoArguments().thenReturn(strategy);
			
			Mockito.doNothing().when(configuration).setReloadingStrategy(strategy);
			dynamicProperties.postProcessBeanFactory(beanFactory);
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			
		}

	}
}
