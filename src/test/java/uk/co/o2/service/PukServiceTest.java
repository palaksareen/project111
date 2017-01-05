package uk.co.o2.service;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.o2.utility.PUKCode;
import uk.co.o2.utility.Validator;
import uk.co.o2.utility.exception.GoogleServiceException;
import uk.co.o2.utility.exception.InValidCaptcha;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;

@RunWith(PowerMockRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class PukServiceTest {

	@Mock
	SoaService soaService;
	
	@Mock
	private ReCaptchaService reCaptchaService;
	
	@InjectMocks
	PUKService pukService;
	
	@Test
	public void testPuk(){
		try {
			PUKCode code=new PUKCode("some puk");
			when(soaService.getPuk("some mpn")).thenReturn("some puk");
			
			PUKCode actual = pukService.getPUKCode("some mpn");
			
			assertSame(code.getPukCode(), actual.getPukCode());
			
		} catch (PUKNotFoundException | NotO2CustomerException | SOAException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=InValidCaptcha.class)
	public void testCaptcha() throws InValidCaptcha, GoogleServiceException{
		when(reCaptchaService.verifyCaptchaResponse("some responce")).thenReturn(false);
		//boolean responce=pukService.varifyCaptcha1("some responce");
		pukService.varifyCaptcha1("some responce");
	}
}
