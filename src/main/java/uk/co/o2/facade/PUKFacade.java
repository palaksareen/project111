package uk.co.o2.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.o2.service.PUKService;
import uk.co.o2.utility.GetPUKConstants;
import uk.co.o2.utility.GetPUKUtility;
import uk.co.o2.utility.Validator;
import uk.co.o2.utility.exception.GoogleServiceException;
import uk.co.o2.utility.exception.InvalidMPNException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.InValidCaptcha;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;

@Service
public class PUKFacade {

	@Autowired
	Validator validator;

	@Autowired
	PUKService service;
	
	
	
	public String getPuk(String mpn) throws InvalidMPNException, PUKNotFoundException,NotO2CustomerException, SOAException{
		// Validate MPN
		validator.validate(mpn);
		// Convert MPN to SOA accepted format
		String MPN = GetPUKUtility.removeChars((String)mpn, (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN);
		String internationalFormat=GetPUKUtility.convertMPN2InternationalFormat(MPN);
		// Call to SOA Service
		uk.co.o2.utility.PUKCode pukResult = service.getPUKCode(internationalFormat);
		return pukResult.getPukCode();
	}

	
	// Recaptcha Code :: eShop Version apache CXF
	public void varifyCaptcha( String reCaptchaDetails) throws InValidCaptcha, GoogleServiceException {
		service.varifyCaptcha(reCaptchaDetails);
	}
}
