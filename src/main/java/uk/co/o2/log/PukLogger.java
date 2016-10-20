package uk.co.o2.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PukLogger {
	private final Log log = LogFactory.getLog("application_log");
	private final Log mis_log = LogFactory.getLog("mis_log");
	
    public PukLogger () {}
    
    /*@AfterReturning("execution(* uk.co.o2.*.*.*(..))")
    public void logMethodAccessAfter(JoinPoint joinPoint) {
        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
    }*/
    @Before("execution(* uk.co.o2.utility.Validator.validate(..))")
    public void logMethodAccessBeforeValidator(JoinPoint joinPoint) {
    	//("GetPUKManagerImpl,validate,Entered method with: " + userInputMPN));    	
    	log.debug(joinPoint.getSignature().getName()+ ",Entered method with: "+joinPoint.getArgs()[0].toString());
    }
    @Before("execution(* uk.co.o2.*.*.*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
    	//("GetPUKManagerImpl,validate,Entered method with: " + userInputMPN));
    	
    	log.debug(joinPoint.getSignature()+"," + joinPoint.getSignature().getName() + ",Entered method with: "+joinPoint.getArgs());
    }
    
    @AfterThrowing(pointcut="execution(* uk.co.o2.*.*.*(..))",throwing="excep")
    public void logMethodAccessAfterThrowing(JoinPoint joinPoint, Throwable excep)throws Throwable {
    		log.info(joinPoint.getSignature().getName()+ ", "+excep);
    }
}
