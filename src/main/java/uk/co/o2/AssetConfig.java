package uk.co.o2;

import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

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
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		for(Entry<String, String> entry:retriveMap(DynamicProperties.getProperty("applicationAssetPaths")).entrySet()){
			registry.addResourceHandler(entry.getKey()+"**").addResourceLocations(entry.getValue());
		}
		 super.addResourceHandlers(registry);
	}
		
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PukInterceptor());
	}

	public Map<String,String> retriveMap(String input){
		Map<String, String> map=new WeakHashMap<>(input.length());
		String[] rawExternalPaths=input.split(";");

		String a="";
		for(String path:rawExternalPaths){
			a=path.substring(path.lastIndexOf("/",(path.lastIndexOf("/")-1)));
			map.put(a, path);
		}
		return map;
	}
}