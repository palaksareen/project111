package uk.co.o2.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.DynamicProperties;
import uk.co.o2.utility.CaptchaModel;
@Service
public class ModelAndViewFacade {
	String pukPagesPath=DynamicProperties.getProperty("pukPagesPath");
	
	
	private final String welcomePage=pukPagesPath+"/welcomePage";
	private final String successPage=pukPagesPath+"/successPage";
	private final String errorPage=pukPagesPath+"/errorPage";
	public ModelAndView forWelcomePage(){
		ModelAndView mav=new ModelAndView(welcomePage);
		showCaptcha(mav);
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
	
	public ModelAndView forWelcomePagewithErorr(List<?> errorList) {
		ModelAndView mav=new ModelAndView(welcomePage);
		mav.addObject("errors", errorList);
		showCaptcha(mav);
		return mav;
		
	}

	private void showCaptcha(ModelAndView mav) {
		String style=null;
		if(CaptchaModel.showCaptcha()){
			style="show";
			mav.addObject("sitekey",CaptchaModel.siteKey());
		}
		else
			style="hidden";
		mav.addObject("showCaptcha",style);
	}
	
}
	