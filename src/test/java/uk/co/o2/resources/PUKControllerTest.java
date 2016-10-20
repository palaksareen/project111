package uk.co.o2.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.facade.ModelAndViewFacade;
import uk.co.o2.facade.PUKFacade;
import uk.co.o2.utility.InvalidMPNException;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;
import uk.co.o2.utility.Validator;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class PUKControllerTest {
	@Mock
	ModelAndViewFacade modelAndView;
	@Mock
	PUKFacade facade;

	@Test
	public void getPUKTest() throws InvalidMPNException, PUKNotFoundException, SOAException {
		MockitoAnnotations.initMocks(this);
		PUKController pukController =new PUKController();
		pukController.facade=facade;
		pukController.modelAndView=modelAndView;
		
		String result="no puk found";
		ModelAndView mav=new ModelAndView();
		
		when(facade.getPuk("some mpn")).thenReturn(result);
		when(modelAndView.forSuccessPage(result)).thenReturn(mav);
		

		ModelAndView actual=pukController.getPUK("some mpn");
		assertEquals(mav, actual);
		
		InvalidMPNException exception=new InvalidMPNException("Invalid MPN-MPN is null",null,Arrays.asList("MPN_NULL"));
		doThrow(exception).when(facade).getPuk("some mpn");
		when(modelAndView.forErrorPage(exception.getErrorList())).thenReturn(mav);
		actual=pukController.getPUK("some mpn");
		assertEquals(mav, actual);
		
		PUKNotFoundException pukNotFoundException=new PUKNotFoundException("PUK Not found");
		doThrow(pukNotFoundException).when(facade).getPuk("some mpn");
		when(modelAndView.forErrorPage(Arrays.asList(pukNotFoundException.getMessage().toString()))).thenReturn(mav);
		actual=pukController.getPUK("some mpn");
		assertEquals(mav, actual);
		
	}   
}
