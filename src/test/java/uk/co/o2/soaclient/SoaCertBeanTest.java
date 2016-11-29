package uk.co.o2.soaclient;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

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
	
	
	
	@Test
	public void testProperties(){
		try {
			when(soaConfig.getSoaKeyStoreCertificateLocation()).thenReturn("some string");
			when(soaConfig.getKeyStorePassword()).thenReturn("some string");
			when(soaConfig.getKeyStorePassword()).thenReturn("some string");
			when(soaConfig.getSoaTrustedStoreCertificateLocation()).thenReturn("some string");
			when(soaConfig.getSoaTrustedStorePassword()).thenReturn("some string");
			
			certBean.afterPropertiesSet();

			assertSame(System.getProperty("javax.net.ssl.keyStore"),"some string");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
