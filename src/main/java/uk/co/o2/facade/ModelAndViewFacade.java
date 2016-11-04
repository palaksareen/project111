package uk.co.o2.facade;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
@Configuration
public class ModelAndViewFacade {
	private final String welcomePage="webcontent/welcomePage";
	private final String successPage="webcontent/successPage";
	private final String errorPage="webcontent/errorPage";
	public ModelAndView forWelcomePage(String sitekey,Boolean flag){
		System.out.println("\n\nsitekey"+sitekey);
		ModelAndView mav=new ModelAndView(welcomePage);
		mav.addObject("sitekey",sitekey);
		String style=null;
		if(flag)
			style="show";
		else
			style="hidden";
		mav.addObject("showCaptcha",style);
		return mav;
	}

	public ModelAndView forSuccessPage(String pukCode,String mpn) {
		ModelAndView mav=new ModelAndView(successPage);
		mav.addObject("puk", pukCode);
		mav.addObject("mpn", mpn);
		return mav;
	}
	
	public ModelAndView forErrorPage(List<?> errorList) {
		ModelAndView mav=new ModelAndView(errorPage);
		mav.addObject("errors", errorList);
		return mav;
	}
}
	