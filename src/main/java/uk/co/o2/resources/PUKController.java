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

import com.codahale.metrics.annotation.Timed;

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

	@Timed
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPUK(@RequestParam("mpn") String mpn, @RequestParam(name="g-recaptcha-response",required=false) String grecaptcha
			) {
		String result="no puk found";
		try {
			if(DynamicProperties.getBooleanProperty("googleReCaptchaEnabled")){
				facade.varifyCaptcha( grecaptcha);
			}
			result=facade.getPuk(mpn);
		} catch (InvalidMPNException e) {
			log.info(e.getMessage() + " : " + e.getErrorCode().getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(e.getErrorCode()));
		} catch (PUKNotFoundException e) {
			log.info(e.getMessage() + " : " + ErrorCode.PUKNOTFOUND.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.PUKNOTFOUND));
		}catch (NotO2CustomerException e) {
			log.info(e.getMessage() + " : " + ErrorCode.NOTO2CUSTOMER.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.NOTO2CUSTOMER));
		} catch (SOAException e) {
			log.error(e.getMessage() + " : " + ErrorCode.SOAFAULT.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.SOAFAULT));
		} catch (InValidCaptcha e) {
			log.info(e.getMessage() + " : " + ErrorCode.INVALID_CAPTCHA.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.INVALID_CAPTCHA));
		} catch (GoogleServiceException e) {
			log.error(e.getMessage() + " : " + ErrorCode.GOOGLE_SERVICE_DOWN.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.GOOGLE_SERVICE_DOWN));
		}catch (Exception e) {
			log.error(e.getMessage() + " : " + ErrorCode.GENERICFAULT.getDescription());
			return modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.GENERICFAULT));
		}
		
		return modelAndView.forSuccessPage(result,mpn);
	}   
}
