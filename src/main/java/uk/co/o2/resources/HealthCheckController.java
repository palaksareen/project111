package uk.co.o2.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Service
@RequestMapping("/healthcheck")
public class HealthCheckController {
	private final Log log = LogFactory.getLog("application_log");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		log.info("In Get PUK healthCheck...");
		ModelAndView mv = new ModelAndView("hc");
		mv.addObject("status", "up");
		
		return mv;
	}

}
