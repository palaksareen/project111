package uk.co.o2.soaclient.rest;

public enum ReCaptchaErrorCode {
	MISSING_INPUT_SECRET("missing-input-secret", "The secret parameter is missing."),
	INVALID_INPUT_SECRET("invalid-input-secret", "The secret parameter is invalid or malformed."),
	MISSING_INPUT_RESPONSE("missing-input-response", "The response parameter is missing."),
	INVALID_INPUT_RESPONSE("invalid-input-response", "The response parameter is invalid or malformed."),

	UNKNOWN("unknown", "An unknown error occurred");

	private final String code;
	private final String description;

	ReCaptchaErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/*static String getErrorDescription(String code) {
		def errorCode = values().find {
			it.code == code
		}
		errorCode ? errorCode.description : UNKNOWN.description
	}*/
}
