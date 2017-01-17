package uk.co.o2.resources;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.DynamicProperties;
import uk.co.o2.utility.CaptchaModel;
import uk.co.o2.utility.ErrorCode;

@Controller
public class AppErrorController implements ErrorController{

	//TODO Logging is missing 
    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";
    
    private final static String errorPage=DynamicProperties.getProperty("pukPagesPath")+"/welcomePage";

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
    	ModelAndView mav=new ModelAndView(errorPage, getErrorAttributes(request, false));
    	mav.addObject("errors", Arrays.asList(ErrorCode.SOAFAULT));
    	showCaptcha(mav);
    	return mav;
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes,
                includeStackTrace);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            }
            catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
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