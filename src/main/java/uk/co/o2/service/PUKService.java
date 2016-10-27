package uk.co.o2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import uk.co.o2.soaclient.SoaService;
import uk.co.o2.utility.NotO2CustomerException;
import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;


@Configuration
public class PUKService {
	
	@Autowired
	SoaService soaService;
	public uk.co.o2.utility.PUKCode getPUKCode(String mpn) throws PUKNotFoundException, NotO2CustomerException,SOAException{
		String p = soaService.getPuk(mpn);
		return new PUKCode(p);
	}
}
