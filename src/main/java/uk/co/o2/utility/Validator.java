package uk.co.o2.utility;


import org.springframework.context.annotation.Configuration;

import uk.co.o2.utility.exception.InvalidMPNException;

@Configuration
public class Validator {
	public void validate(String userInputMPN) throws InvalidMPNException {
        ErrorCode errorCode ;
        if (userInputMPN != null) {
            int mpnLength = userInputMPN.length();
            if (mpnLength == 0) {
                errorCode = ErrorCode.INVALID_MPN_EMPTY;       
                throw new InvalidMPNException(errorCode.getMessage(), errorCode);
            }
            userInputMPN = userInputMPN.replaceFirst("\\+", "");
            userInputMPN = userInputMPN.replaceAll("\\-", "");
            if (!userInputMPN.matches("\\d+")) {
                errorCode = ErrorCode.INVALID_MPN;
                throw new InvalidMPNException(errorCode.getMessage(), errorCode);                
            }
            if (mpnLength > 10 && GetPUKUtility.specialCharacterCheck((String)userInputMPN)) {
                if ((userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN)).matches("^(44|07)\\d+")) {
                    if (userInputMPN.startsWith("07") && userInputMPN.length() == 11 || userInputMPN.startsWith("447") && userInputMPN.length() == 12) {
                        return;
                    }
                    errorCode = ErrorCode.INVALID_MPN_LONG;
                } else {
                	errorCode = ErrorCode.INVALID_MPN_PREFIX;
                }
            } else {
            	errorCode = ErrorCode.INVALID_MPN_SHORT ;
            }
        } else {
        	errorCode = ErrorCode.INVALID_MPN_EMPTY;
        }
        throw new InvalidMPNException(errorCode.getMessage(), errorCode);
    }
}
