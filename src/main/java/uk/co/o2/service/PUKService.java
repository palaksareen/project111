package uk.co.o2.service;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.exception.GoogleServiceException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.InValidCaptcha;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;


@Configuration
public class PUKService {
	
	@Autowired
	ISubscriberService soaService;
	
	@Autowired
	private ReCaptchaService reCaptchaService;
	
	public uk.co.o2.utility.PUKCode getPUKCode(String mpn) throws PUKNotFoundException, NotO2CustomerException,SOAException{
		String puk =soaService.getPuk(mpn);
		return new PUKCode(puk);
	}
	
	public void varifyCaptcha( String reCaptchaDetails) throws InValidCaptcha, GoogleServiceException {
		try {
			if(! reCaptchaService.verifyCaptchaResponse(reCaptchaDetails))
				throw new InValidCaptcha(ErrorCode.INVALID_CAPTCHA.getMessage(),"");
		} catch (Fault e) {
			throw new GoogleServiceException(e.getCause());
		}
	}
}
