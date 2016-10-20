package uk.co.o2.utility;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Validator {
	public void validate(String userInputMPN) throws InvalidMPNException {
        ArrayList<String> errors = new ArrayList<String>();
        if (userInputMPN != null) {
            int mpnLength = userInputMPN.length();
            if (mpnLength == 0) {
                errors.add("MPN_NULL");
                throw new InvalidMPNException("Invalid MPN-MPN is null", userInputMPN, errors);
            }
            userInputMPN = userInputMPN.replaceFirst("\\+", "");
            userInputMPN = userInputMPN.replaceAll("\\-", "");
            if (!userInputMPN.matches("\\d+")) {
                errors.add("MPN_NOT_NUMERIC");
                throw new InvalidMPNException("Invalid MPN-MPN is not numeric", userInputMPN, errors);
            }
            if (mpnLength > 10 && GetPUKUtility.specialCharacterCheck((String)userInputMPN)) {
                if ((userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN)).matches("^(44|07)\\d+")) {
                    if (userInputMPN.startsWith("07") && userInputMPN.length() == 11 || userInputMPN.startsWith("447") && userInputMPN.length() == 12) {
                        return;
                    }
                    errors.add("MPN_INVALID_LENGTH");
                } else {
                    errors.add("MPN_INVALID_PREFIX");
                }
            } else {
                errors.add("MPN_TOO_SHORT");
            }
        } else {
            errors.add("MPN_NULL");
        }
        throw new InvalidMPNException("Invalid MPN", userInputMPN, errors);
    }
}
