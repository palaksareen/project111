package uk.co.o2.utility;

public enum ErrorCode {
	INVALID_MPN_NOT_O2("We are unable to provide a PUK code for this phone number.","Please note that we can only provide PUK codes to O2 customers."),
	INVALID_MPN("We do not recognise this phone number.","Please enter your full 11 digit phone number without hyphens or spaces."),
	INVALID_MPN_PREFIX("The phone number entered is not valid.","Please enter your full phone number, beginning '07', '447' or '+447'."),
	INVALID_MPN_EMPTY("Please enter your phone number.",""),
	INVALID_MPN_LONG("The phone number entered has too many digits.","Please enter your full 11 digit phone number without hyphens or spaces."),
	INVALID_MPN_SHORT("The phone number entered is too short.","Please enter your full 11 digit phone number without hyphens or spaces");
	
	 private final String message;
     private final String description;

    private ErrorCode(String msg,String desc) {
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
