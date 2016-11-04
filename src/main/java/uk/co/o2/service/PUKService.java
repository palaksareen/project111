package uk.co.o2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;


@Configuration
public class PUKService {
	
	@Autowired
	SoaService soaService;
	
	public uk.co.o2.utility.PUKCode getPUKCode(String mpn) throws PUKNotFoundException, NotO2CustomerException,SOAException{
		String puk =soaService.getPuk(mpn);
		return new PUKCode(puk);
	}
}
