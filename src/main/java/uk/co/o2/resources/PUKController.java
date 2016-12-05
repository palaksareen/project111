package uk.co.o2.resources;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.DynamicProperties;
import uk.co.o2.facade.ModelAndViewFacade;
import uk.co.o2.facade.PUKFacade;
import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.exception.GoogleServiceException;
import uk.co.o2.utility.exception.InValidCaptcha;
import uk.co.o2.utility.exception.InvalidMPNException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;


@RestController
@Service
@RequestMapping("/")
public class PUKController {
	private final Log log = LogFactory.getLog("application_log");

	@Autowired
	ModelAndViewFacade modelAndView;

	@Autowired
	PUKFacade facade;

	String sitekey=DynamicProperties.getProperty("sitekey");

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		return modelAndView.forWelcomePage();
	}


	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPUK(@RequestParam("mpn") String mpn, @RequestParam("g-recaptcha-response") String grecaptcha
			) {
		String result="no puk found";
		try {
			if(DynamicProperties.getBooleanProperty("googlecaptcha.enabled")){
				facade.varifyCaptcha1( grecaptcha);
			}
			result=facade.getPuk(mpn);
		} catch (InvalidMPNException e) {
			log.debug("MPN is not valid.");
			return modelAndView.forWelcomePagewithErorr(e.getErrorList());
		} catch (PUKNotFoundException e) {
			log.debug("Unable to found the puk for given mpn.");
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.PUKNOTFOUND));
		}catch (NotO2CustomerException e) {
			log.debug("Not an O2 Customer");
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.NOTO2CUSTOMER));
		} catch (SOAException e) {
			log.debug("SOA service is down.");
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.SOAFAULT));
		} catch (InValidCaptcha e) {
			log.debug("Invalid captch exception");
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.INVALID_CAPTCHA));
		} catch (GoogleServiceException e) {
			log.debug("Google ReCAPTCHA Service is down.");
			e.printStackTrace();
		}
		return modelAndView.forSuccessPage(result,mpn);
	}   
}
