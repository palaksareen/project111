package uk.co.o2.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.o2.service.PUKService;
import uk.co.o2.utility.GetPUKConstants;
import uk.co.o2.utility.GetPUKUtility;
import uk.co.o2.utility.InvalidMPNException;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;
import uk.co.o2.utility.Validator;

@Service
public class PUKFacade {
	@Autowired
	Validator validator;
	@Autowired
	PUKService service;
	
	public String getPuk(String mpn) throws InvalidMPNException, PUKNotFoundException, SOAException{
		// Validate MPN
		validator.validate(mpn);
		// Convert MPN to SOA accepted format
		String MPN = GetPUKUtility.removeChars((String)mpn, (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN);
		String internationalFormat=GetPUKUtility.convertMPN2InternationalFormat(MPN);
		// Call to SOA Service
		uk.co.o2.utility.PUKCode pukResult = service.getPUKCode(internationalFormat);
		
		
		return pukResult.getPukCode();
	}
}
