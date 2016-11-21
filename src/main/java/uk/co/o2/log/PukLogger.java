package uk.co.o2.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PukLogger {
	private final Log log = LogFactory.getLog("application_log");
	private final Log mis_log = LogFactory.getLog("mis_log");
	
    public PukLogger () {}
    
    
    @Before("execution(* uk.co.o2.soaclient.*.*(..))")
    public void SSLCertBeanLog(JoinPoint joinPoint) {
    	log.debug("Before Storing the Certificate");
    }
    
    @AfterReturning("execution(* uk.co.o2.soaclient.*.*(..))")
    public void SSLCertBeanLogAfter(JoinPoint joinPoint) {
    	log.debug("Certificate Storing is sucessful");
    }
    
    @Before("execution(* uk.co.o2.utility.Validator.validate(..))")
    public void logMethodAccessBeforeValidator(JoinPoint joinPoint) {
    	log.debug(joinPoint.getSignature().getName()+ 
    			",validating <"+joinPoint.getArgs()[0].toString()+"> MPN");
    }

    @Before("execution(* uk.co.o2.resources.*.*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
    	log.debug(joinPoint.getSignature());
    }
    
    @AfterReturning(pointcut="execution(* uk.co.o2.*.*.*(..))",returning="returnVal")
    public void logMethodAccessAfter(JoinPoint joinPoint, String returnVal) {
    	mis_log.debug("*** "+joinPoint.getArgs()[0].toString()+"\tis "+returnVal);
    }
    
    @AfterThrowing(pointcut="execution(* uk.co.o2.facade.*.*(..))",throwing="excep")
    public void logMethodAccessAfterThrowing(JoinPoint joinPoint, Throwable excep)throws Throwable {
    		log.info("Exception Thrown "+joinPoint.getSignature()+ ", "+excep);
    }
    
}
