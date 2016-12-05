package uk.co.o2.resources;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Service
@RequestMapping("/hc")
public class HealthCheckController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		System.out.println("....In Get PUK healthCheck...");
		return new ModelAndView("hc");
	}

}
