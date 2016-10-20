package uk.co.o2.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.o2.service.PUKService;
import uk.co.o2.utility.InvalidMPNException;
import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;
import uk.co.o2.utility.Validator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class PUKFacadeTest {
	
	PUKFacade facade;
	@Mock
	Validator mockValidator;
	@Mock
	PUKService mockService;

	@Test
	public void testGetPukFunctionality() throws InvalidMPNException, PUKNotFoundException, SOAException{
		MockitoAnnotations.initMocks(this);
		PUKFacade facade=new PUKFacade();		
		facade.validator  = mockValidator;
		facade.service = mockService;
		PUKCode code=new PUKCode("123");
		doNothing().when(this.mockValidator).validate("07701359421");
		when(mockService.getPUKCode("447701359421")).thenReturn(code);
		
		String actual = facade.getPuk("07701359421");
		assertEquals("MPN are not equal", code.getPukCode(), actual);
	}
}
