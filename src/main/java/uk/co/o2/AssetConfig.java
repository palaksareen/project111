package uk.co.o2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import uk.co.o2.interceptor.PukInterceptor;
import uk.co.o2.log.PukLogger;
@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan(basePackages = {"uk.co.o2.utility"})
@ImportResource("classpath:config.xml")
public class AssetConfig extends WebMvcConfigurerAdapter {
	@Bean   
	public PukLogger myLogger(){
		return new PukLogger();
	}
	
	
	@Value("${application.asset.shared}")
	private String exStaticContentPath;
	
	@Value("${application.asset}")	
	private String 	asset;
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/shared/**").addResourceLocations(exStaticContentPath);
		registry.addResourceHandler("/_asset/**").addResourceLocations(asset);

		registry.addResourceHandler("/**").addResourceLocations(exStaticContentPath);
		 super.addResourceHandlers(registry);
	}
	
	 @Override
	  public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new PukInterceptor());
	 }
}