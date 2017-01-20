package src.test.java.uk.co.o2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class PUKHealthCheckTest {

	private static final String siteUrl = "http://localhost.ref.o2.co.uk:8080/puk/healthcheck";
	WebDriver driver=new FirefoxDriver();
	
	@AfterSuite
	public void closeBroweser(){
		driver.quit();
	}
	
	@Test
	public void happyTest()
	{
		driver.get(siteUrl);
		
		String msg = driver.findElement(By.id("outputQA")).getText();
		Assert.assertEquals(msg, "Status: up");
	}
	
}
