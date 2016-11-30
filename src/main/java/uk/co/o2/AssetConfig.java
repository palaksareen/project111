package uk.co.o2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
	
	
	 @Override
	  public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new PukInterceptor());
	 }
}