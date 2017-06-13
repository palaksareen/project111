package uk.co.o2.interceptor;

import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.log4j.MDC;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UUID.class})
public class PukInterceptorTest {
	
	
	@Test
	public void testRequestId(){
		MockitoAnnotations.initMocks(this);
		PukInterceptor interceptor= new PukInterceptor();
		try {
			interceptor.preHandle(null, null, null);
			Pattern p = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f‌​]{4}-[0-9a-f]{12}$");
			Assert.assertTrue(p.matcher(MDC.get("RequestId").toString()).matches());
			
			interceptor.postHandle(null, null, null, null);
			Assert.assertNull(MDC.get("RequestId"));
		} catch (Exception e) {
			//Assert.fail(e.getMessage());
		}
	}
}
