package uk.co.o2.utility;

public enum ErrorCode {
	INVALID_MPN("The mobile number you\'ve entered hasn\'t been recognised. Check your number and try again.","Please enter your full 11 digit phone number without hyphens or spaces."),
	INVALID_MPN_PREFIX("The mobile number you\'ve entered hasn\'t been recognised. Check your number and try again.","Please enter your full phone number, beginning '07', '447' or '+447'."),
	INVALID_MPN_EMPTY("Enter your mobile number to find your PUK code.","Phone number can not be empty."),
	INVALID_MPN_LONG("The mobile number you\'ve entered hasn\'t been recognised. Check your number and try again.","Mobile number entered have too many digits, please enter your full 11 digit number."),
	INVALID_MPN_SHORT("The mobile number you\'ve entered hasn\'t been recognised. Check your number and try again.","Mobile number entered is too short, please enter your full 11 digit number."),
	PUKNOTFOUND("Sorry we are unable to find the PUK. Please try again after sometime.","Unable to find PUK"),
	NOTO2CUSTOMER("We are unable to find a PUK code for this phone number. Please note that we can only provide PUK codes to O2 customers.", "It is not o2 number"),
	INVALID_CAPTCHA("Invalid Captacha","Please try with valid captcha."),
	SOAFAULT("Oops, something went wrong. Try again later.","Unable to connect to SOA service."),
	GOOGLE_SERVICE_DOWN("Oops, something went wrong. Try again later.","Unable to connect to Google service."),
	GENERICFAULT("Oops, something went wrong. Try again later.", "Some internal service is down"),
	SSL_CERTIFICATE_ERROR("Oops, something went wrong. Try again later.","Exception occured during storing certificate for soa rest call."),
	MSG_HANDLER_ERROR("Oops, something went wrong. Try again later." , "Error occured during SOAP message handler.");
	
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
