package uk.co.o2.utility;

import uk.co.o2.DynamicProperties;

public class CaptchaModel {
	public static String siteKey(){
		return DynamicProperties.getProperty("sitekey");
	}
	
	public static boolean showCaptcha(){
		return DynamicProperties.getBooleanProperty("googlecaptcha.enabled");
	}
}
