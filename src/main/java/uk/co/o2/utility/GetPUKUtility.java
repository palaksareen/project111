/*
 * Decompiled with CFR 0_115.
 */
package uk.co.o2.utility;

public class GetPUKUtility {
	public static boolean isNumeric(String numberStr) {
		try {
			if (numberStr != null && numberStr.indexOf("+") >= 0) {
				numberStr = numberStr.substring(numberStr.indexOf("+") + 1);
			}
			Long.parseLong(numberStr);
			return true;
		}
		catch (NumberFormatException numberExcep) {
			return false;
		}
	}

	public static String removeChars(String inputStr, String[] removalChar) {
		StringBuffer userInputMPNStrBuf = new StringBuffer(inputStr);
		for (int indx = removalChar.length - 1; indx > -1; --indx) {
			userInputMPNStrBuf = GetPUKUtility.removeRepeatableChar(userInputMPNStrBuf, removalChar[indx]);
		}
		return userInputMPNStrBuf.toString();
	}

	private static StringBuffer removeRepeatableChar(StringBuffer orgStr, String tobeRemovedStrChar) {
		int x = -1;
		while ((x = orgStr.indexOf(tobeRemovedStrChar)) > -1) {
			orgStr.deleteCharAt(x);
		}
		return orgStr;
	}

	public static boolean specialCharacterCheck(String inputMPN) {
		boolean isSplCheckSuccessful = false;
		if (inputMPN != null) {
			int hypenIndx = inputMPN.trim().indexOf("-");
			int mpnLength = inputMPN.length();
			if (inputMPN.lastIndexOf("+") <= 0 && hypenIndx != 0 && hypenIndx != inputMPN.length() && inputMPN.lastIndexOf("-") != mpnLength - 1 && !inputMPN.startsWith("+07")) {
				hypenIndx = (inputMPN = GetPUKUtility.removeChars(inputMPN, new String[]{" "})).trim().indexOf("-");
				if (hypenIndx < 0) {
					isSplCheckSuccessful = true;
					return isSplCheckSuccessful;
				}
				if (inputMPN.startsWith("+-")) {
					isSplCheckSuccessful = false;
					return isSplCheckSuccessful;
				}
				while (hypenIndx > -1) {
					int nextHyphenIndx = inputMPN.indexOf("-", hypenIndx + 1);
					if (hypenIndx == nextHyphenIndx - 1) {
						isSplCheckSuccessful = false;
						break;
					}
					isSplCheckSuccessful = true;
					hypenIndx = nextHyphenIndx;
				}
			}
		}
		return isSplCheckSuccessful;
	}


	public static String convertMPN2InternationalFormat(String userInputMPN) {
		userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN);
		if (userInputMPN.startsWith("07")) {
			userInputMPN = userInputMPN.replaceFirst("07", "447");
		}
		return userInputMPN;
	}

}
