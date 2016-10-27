package src.test.java.uk.co.o2;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class PUKFunctionalTest {

	WebDriver driver=new FirefoxDriver();

	public PUKFunctionalTest() {
	}
	@AfterSuite
	public void closeBroweser(){
		System.out.println("Closign brower.....");
		driver.quit();
	}


	@Test
	public void happyPath() {
		driver.get("http://localhost:8080/puk-0.0.1-SNAPSHOT/puk");    

		driver.findElement(By.id("MPN")).sendKeys("447704610259");
		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvPuk")).getText();
		Assert.assertEquals(msg, "The PUK code for mobile number 447704610259 is : 1234");
	}

	@Test
	public void checkNonO2MPN() {
		driver.get("http://localhost:8080/puk-0.0.1-SNAPSHOT/puk");    

		driver.findElement(By.id("MPN")).sendKeys("447704610200");
		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvMsg")).getText();
		Assert.assertEquals(msg, "Not a O2 customer");
	}

	@Test
	public void checkInvalidMPN() {
		driver.get("http://localhost:8080/puk-0.0.1-SNAPSHOT/puk");    

		driver.findElement(By.id("MPN")).sendKeys("44770461020");
		driver.findElement(By.id("btnSubmit")).click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		String msg=driver.findElement(By.id("dvMsg")).getText();
		String desc=driver.findElement(By.id("dvDesc")).getText();
		Assert.assertEquals(msg, "The phone number entered has too many digits.");
		Assert.assertEquals(desc, "Please enter your full 11 digit phone number without hyphens or spaces.");
	}
}
