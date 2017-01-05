package uk.co.o2.service;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Timed;

import uk.co.o2.soaclient.rest.CaptchaValidationResource;
import uk.co.o2.soaclient.rest.ReCaptchaResponse;
import uk.co.o2.soaclient.rest.ReCaptchaToken;
import uk.co.o2.soaclient.rest.SoaRestResourceClientFactory;

@Service
public class ReCaptchaService {

    private final CaptchaValidationResource captchaValidationResource;
    
    @Autowired
    public ReCaptchaService(SoaRestResourceClientFactory soaRestResourceClientFactory) {
    	this.captchaValidationResource =  soaRestResourceClientFactory.createCaptchaValidationResource();
    }

    @Timed
    public boolean verifyCaptchaResponse(String reCaptchaResponse) throws Fault{
    	ReCaptchaResponse verifiedReCaptchaResponse = captchaValidationResource.validateReCaptcha(new ReCaptchaToken(reCaptchaResponse));
    	return verifiedReCaptchaResponse.success;
    }
}