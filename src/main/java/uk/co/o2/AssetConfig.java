package uk.co.o2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import uk.co.o2.log.PukLogger;
@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan(basePackages = {"uk.co.o2.utility"})
@ImportResource("classpath:config.xml")
public class AssetConfig {
	@Bean   
	public PukLogger myLogger(){
		return new PukLogger();
	}
}