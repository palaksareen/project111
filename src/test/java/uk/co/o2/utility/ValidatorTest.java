package uk.co.o2.utility;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.o2.utility.exception.InvalidMPNException;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class ValidatorTest {
	@Mock
	Validator validator;
	
	@Test(expected=InvalidMPNException.class)
	public void happyPath() throws InvalidMPNException {
		MockitoAnnotations.initMocks(this);

		doThrow(new InvalidMPNException("Invalid MPN-MPN is null",null,Arrays.asList("MPN_NULL"))).when(validator).validate("");
        validator.validate("ww");
        
        doThrow(new InvalidMPNException("Invalid MPN",null,Arrays.asList("MPN_NULL"))).when(validator).validate("0770135942");
        validator.validate("0770135942");
        
        doThrow(new InvalidMPNException("Invalid MPN",null,Arrays.asList("MPN_NULL"))).when(validator).validate("077013594211");
        validator.validate("077013594211");
        
        doThrow(new InvalidMPNException("Invalid MPN",null,Arrays.asList("MPN_NULL"))).when(validator).validate("047013594211");
        validator.validate("047013594211");
        
        doThrow(new InvalidMPNException("Invalid MPN",null,Arrays.asList("MPN_NULL"))).when(validator).validate("abc");
        validator.validate("abc");
        
        
        doNothing().when(validator).validate("07701359421");
        validator.validate("07701359421");
        
	}
}
