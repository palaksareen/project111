package uk.co.o2.soaclient;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.o2.soaclient.rest.SoaConfig;

@RunWith(PowerMockRunner.class)
public class SoaCertBeanTest {

	@InjectMocks
	SslCertBean certBean;
	
	@Mock
	SoaConfig soaConfig;
	
	
	
	@Test(expected = Exception.class)
	public void testProperties() throws Exception{
		
			when(soaConfig.getSoaCertificateLocation()).thenReturn("some string");
			when(soaConfig.getKeyStorePassword()).thenReturn("some string");
			when(soaConfig.getKeyStorePassword()).thenReturn("some string");
			when(soaConfig.getSoaTrustedStoreCertificateLocation()).thenReturn("some string");
			when(soaConfig.getSoaTrustedStorePassword()).thenReturn("some string");
			
			certBean.afterPropertiesSet();

			assertSame(System.getProperty("javax.net.ssl.keyStore"),"some string");
		
	}
}
