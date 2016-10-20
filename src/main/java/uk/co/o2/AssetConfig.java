package uk.co.o2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import uk.co.o2.log.PukLogger;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"uk.co.o2.utility"})
public class AssetConfig {

    //-----------------------------------------------------------------------------------------------------------------------
    @Bean   
    public PukLogger myLogger(){
       return new PukLogger();
    }
}