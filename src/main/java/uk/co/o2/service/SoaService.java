package uk.co.o2.service;

import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;

public interface SoaService {
	String getPuk(String mpn)throws PUKNotFoundException, NotO2CustomerException,SOAException;
}
