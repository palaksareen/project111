package src.test.java.uk.co.o2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class PUKFunctionalTest {

	private static final String siteUrl = "http://localhost.ref.o2.co.uk:8080/puk";
	WebDriver driver=new FirefoxDriver();

	public PUKFunctionalTest() {
	}
	
	@AfterSuite
	public void closeBroweser(){
		driver.quit();
	}

	private void recaptchaCall() throws InterruptedException {
		WebElement iframeSwitch = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div/div/div/iframe"));
	    driver.switchTo().frame(iframeSwitch);
	    driver.findElement(By.cssSelector("div[class=recaptcha-checkbox-checkmark]")).click();
	    driver.switchTo().parentFrame();
	    TimeUnit.SECONDS.sleep(3);
	}
	
	
	@Test
	public void happyPath() throws InterruptedException {
		driver.get(siteUrl);    
		driver.findElement(By.id("MPN")).sendKeys("447704610259");
		recaptchaCall();
	    driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("puk-qa")).getText();
		Assert.assertEquals(msg, "Your PUK code is 9989");
	}
	
	@Test
	public void checkNonO2MPN() throws InterruptedException {
		driver.get(siteUrl);    
		driver.findElement(By.id("MPN")).sendKeys("447704610200");
		recaptchaCall();
		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvMsg")).getText();
		Assert.assertEquals(msg, "We can't find a PUK code for this mobile number. We can only give PUK codes to O2 customers. If you are an O2 customer, check your number and try again.");
	}

	@Test
	public void checkInvalidMPN() throws InterruptedException {
		driver.get(siteUrl);    
		driver.findElement(By.id("MPN")).sendKeys("44770461020");
		recaptchaCall();

		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("spanError")).getText();
		//String desc=driver.findElement(By.id("dvDesc")).getText();
		Assert.assertEquals(msg, "The mobile number you've entered hasn't been recognised. Check your number and try again.");
	}
}
