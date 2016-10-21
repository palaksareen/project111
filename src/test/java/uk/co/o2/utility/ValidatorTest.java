package uk.co.o2.utility;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
public class ValidatorTest {
	Validator validator;

	
	@Test()
	public void withoutException() throws InvalidMPNException {
		MockitoAnnotations.initMocks(this);
		validator=new Validator();
		validator.validate("07701359421");
	}
	
	@Test(expected=InvalidMPNException.class)
	public void checkException() throws InvalidMPNException {
		MockitoAnnotations.initMocks(this);
		validator=new Validator();
		validator.validate("077013594211");
	}
	@Test(expected=InvalidMPNException.class)
	public void checkEmptyException() throws InvalidMPNException {
		MockitoAnnotations.initMocks(this);
		validator=new Validator();
		validator.validate("");
	}
}
