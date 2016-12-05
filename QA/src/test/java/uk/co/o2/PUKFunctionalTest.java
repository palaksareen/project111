package src.test.java.uk.co.o2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
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
		WebElement iframeSwitch = driver.findElement(By.xpath("/html/body/div/fieldset/form/div/div/div/iframe"));
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
		
		
		String msg=driver.findElement(By.id("dvPuk")).getText();
		Assert.assertEquals(msg, "The PUK code for mobile number 447704610259 is : 9989");
	}

	@Test
	public void checkNonO2MPN() throws InterruptedException {
		driver.get(siteUrl);    

		driver.findElement(By.id("MPN")).sendKeys("447704610200");
		
		recaptchaCall();

		
		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvMsg")).getText();
		Assert.assertEquals(msg, "Not a O2 customer");
	}

	

	@Test
	public void checkInvalidMPN() throws InterruptedException {
		driver.get(siteUrl);    

		driver.findElement(By.id("MPN")).sendKeys("44770461020");

		recaptchaCall();

		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvMsg")).getText();
		String desc=driver.findElement(By.id("dvDesc")).getText();
		Assert.assertEquals(msg, "The phone number entered has too many digits.");
		Assert.assertEquals(desc, "Please enter your full 11 digit phone number without hyphens or spaces.");
	}

}
