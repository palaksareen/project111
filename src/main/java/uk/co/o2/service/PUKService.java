package uk.co.o2.service;

import org.springframework.context.annotation.Configuration;

import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;


@Configuration
public class PUKService {

	public uk.co.o2.utility.PUKCode getPUKCode(String mpn) throws PUKNotFoundException, SOAException{
		return new PUKCode("43253-344");
	}
}
