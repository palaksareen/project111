package uk.co.o2.resources;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
import uk.co.o2.utility.exception.InvalidMPNException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.NotValidCaptcha;
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
 
	@Autowired 
	private HttpServletRequest request;
	
	@Value("${sitekey}")
	String sitekey;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		return modelAndView.forWelcomePage(sitekey,DynamicProperties.getBooleanProperty("googlecaptcha.enabled"));
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPUK(@RequestParam("mpn") String mpn, @RequestParam("g-recaptcha-response") String grecaptcha
			) {
		String result="no puk found";
		try {
		if(request != null )
			if(DynamicProperties.getBooleanProperty("googlecaptcha.enabled"))
				facade.varifyCaptcha( request.getLocalAddr(), grecaptcha);
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
		} catch (NotValidCaptcha e) {
			return modelAndView.forErrorPage(Arrays.asList(ErrorCode.INVALID_CAPTCHA));
		} catch (GoogleServiceException e) {
			// TODO Do nothing ....
			e.printStackTrace();
		}
		return modelAndView.forSuccessPage(result,mpn);
	}   
}
