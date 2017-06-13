package uk.co.o2.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PukInterceptor extends HandlerInterceptorAdapter{
	

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)
					throws Exception {

		MDC.put("RequestId",UUID.randomUUID().toString());
		return true;
	}

	//after the handler is executed
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView)
					throws Exception {
		response.setHeader("Server", "NA");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("X-Frame-Options", "SAMEORIGIN");
		MDC.clear();
	}
}
