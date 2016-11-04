package uk.co.o2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import uk.co.o2.resources.AppErrorController;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class PukApplication {
	@Autowired
	 private ErrorAttributes errorAttributes;

	 @Bean
	 public AppErrorController appErrorController(){return new AppErrorController(errorAttributes);}

	public static void main(String[] args) {
		SpringApplication.run(PukApplication.class, args);
		
	}
}
