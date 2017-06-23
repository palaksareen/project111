package uk.co.o2.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.o2.DynamicProperties;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String PATH = "/error";
    private String pukPagesPath = DynamicProperties.getProperty("pukPagesPath");

    @RequestMapping(value = PATH)
    public ModelAndView handleHttpError(HttpServletRequest request) {
        int httpErrorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        ModelAndView mav = new ModelAndView();
        if (httpErrorCode == 500) {
            mav.setViewName(pukPagesPath + "/errorPage500");
        } else {
            mav.setViewName(pukPagesPath + "/errorPage404");
        }

        return mav;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
