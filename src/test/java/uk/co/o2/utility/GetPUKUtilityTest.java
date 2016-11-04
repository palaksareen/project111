/*
 * Decompiled with CFR 0_115.
 */
package uk.co.o2.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class GetPUKUtilityTest {
	
	GetPUKUtility pukUtility;
	
	@Test
	public void checkIsNumeric(){
		assertTrue(GetPUKUtility.isNumeric("123"));
		assertFalse(GetPUKUtility.isNumeric("some non number"));
	}
	
	@Test
	public void checkRemoveCharacter(){
		String inputStr="123456-1244";
		String[] removalChar={"-"};
		assertEquals("1234561244", GetPUKUtility.removeChars(inputStr, removalChar));
	}
	
	@Test
	public void check_specialCharacterCheck(){
		String inputStr="+1234561244";
		assertTrue(GetPUKUtility.specialCharacterCheck(inputStr));
	}

	@Test
	public void check_convertMPN2InternationalFormat(){
		String inputStr="07701359421";
		assertEquals("447701359421", GetPUKUtility.convertMPN2InternationalFormat(inputStr));
	}

	/*

	public static String convertMPN2InternationalFormat(String userInputMPN) {
		userInputMPN = GetPUKUtility.removeChars((String)userInputMPN.trim(), (String[])GetPUKConstants.REMOVABLE_SPECIAL_CHAR_FROM_MPN);
		if (userInputMPN.startsWith("07")) {
			userInputMPN = userInputMPN.replaceFirst("07", "447");
		}
		return userInputMPN;
	}
*/
}
