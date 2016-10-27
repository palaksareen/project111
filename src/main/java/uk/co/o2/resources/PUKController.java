package uk.co.o2.resources;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.facade.ModelAndViewFacade;
import uk.co.o2.facade.PUKFacade;
import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.InvalidMPNException;
import uk.co.o2.utility.NotO2CustomerException;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;


@RestController
@Service
@RequestMapping("/puk")
public class PUKController {
	@Autowired
	Environment env;

	public PUKController() {
		System.out.println("\nPUKController is getting loaded "+env);
	}

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
			//TODO	It only prints error codes. convert it into nice human readable format
			return modelAndView.forErrorPage(e.getErrorList());
		} catch (PUKNotFoundException e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.PUKNOTFOUND));
		}catch (NotO2CustomerException e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.NOTO2CUSTOMER));
		} catch (SOAException e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.SOAFAULT));
		}
		return modelAndView.forSuccessPage(result,mpn);
	}   
}
