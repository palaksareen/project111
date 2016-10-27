package uk.co.o2.resources;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.facade.ModelAndViewFacade;
import uk.co.o2.facade.PUKFacade;
import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.exception.InvalidMPNException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;


@RestController
@Service
@RequestMapping("/puk")
public class PUKController {
	
	@Autowired
	ModelAndViewFacade modelAndView;
	@Autowired
	PUKFacade facade;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {    	
		return modelAndView.forWelcomePage();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPUK(@RequestParam("mpn") String mpn
			) {
		String result="no puk found";
		try {
			result=facade.getPuk(mpn);
		} catch (InvalidMPNException e) {
			return modelAndView.forErrorPage(e.getErrorList());
		} catch (PUKNotFoundException e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.PUKNOTFOUND));
		}catch (NotO2CustomerException e) {
			System.out.println("\n\nIn NotO2Customer\n");
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.NOTO2CUSTOMER));
		} catch (SOAException e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.SOAFAULT));
		}
		return modelAndView.forSuccessPage(result,mpn);
	}   
}
