package uk.co.o2.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.o2.soaclient.rest.CaptchaValidationResource;
import uk.co.o2.soaclient.rest.ReCaptchaResponse;
import uk.co.o2.soaclient.rest.ReCaptchaToken;
import uk.co.o2.soaclient.rest.SoaRestResourceClientFactory;

@Service
public class ReCaptchaService {

    private final CaptchaValidationResource captchaValidationResource;
    
    @Autowired
    public ReCaptchaService(SoaRestResourceClientFactory soaRestResourceClientFactory) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    	this.captchaValidationResource =  soaRestResourceClientFactory.createCaptchaValidationResource();
    }

    public boolean verifyCaptchaResponse(String reCaptchaResponse){
        ReCaptchaResponse verifiedReCaptchaResponse = captchaValidationResource.validateReCaptcha(new ReCaptchaToken(reCaptchaResponse));
        return verifiedReCaptchaResponse.success;
    }

    
}