package uk.co.o2.utility;

public enum ErrorCode {
	INVALID_MPN_NOT_O2("We are unable to provide a PUK code for this phone number.","Please note that we can only provide PUK codes to O2 customers."),
	INVALID_MPN("We do not recognise this phone number.","Please enter your full 11 digit phone number without hyphens or spaces."),
	INVALID_MPN_PREFIX("The phone number entered is not valid.","Please enter your full phone number, beginning '07', '447' or '+447'."),
	INVALID_MPN_EMPTY("Invalid phone number.","Please enter your phone number."),
	INVALID_MPN_LONG("The phone number entered has too many digits.","Please enter your full 11 digit phone number without hyphens or spaces."),
	INVALID_MPN_SHORT("The phone number entered is too short.","Please enter your full 11 digit phone number without hyphens or spaces"), 
	PUKNOTFOUND("Sorry We are unable to find the PUK","Please try after some time."),
	NOTO2CUSTOMER("Not a O2 customer","Please try with valid mpn."),
	INVALID_CAPTCHA("Invalid Captacha","Please try with valid captcha."),
	GOOGLE_SERVICE_DOWN("Some Internal Service is down","Please try after some time."),
	SOAFAULT("Something went wrong","Unable to connect to SOA service. Please try after some time."),
	GENERICFAULT("Something went wrong","Please try after some time."),
	SSL_CERTIFICATE_ERROR("Exception occured during Storing certificate for soa rest call.","Please try after some time."),
	MSG_HANDLER_ERROR("Error occured during message handler.","Please try after some time.");
	 private final String message;
     private final String description;

    ErrorCode(String msg,String desc) {
    	message= msg;
    	description=desc;
    }
    
    public String getMessage() {
		return message;
	}
    
    public String getDescription() {
		return description;
	}
}
