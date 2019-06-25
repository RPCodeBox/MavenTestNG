/* Script Name: NewTours_Driver.java
 * Author:	Rana Pratap
 * Data: Jan 2019 Version 1.0 */
package TestScript;

import org.openqa.selenium.WebDriver;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.Reporter;
import org.testng.annotations.*;
import Library.NewTours_POM;
import Utilities.BrowserUtility;

//New tours driver
public class NewTours_Driver {
	public WebDriver driver;
	
	@DataProvider(name = "dp") //Test data
	public static Object[][] credentials(){
		return new Object[][] {new Object[] { "test1", "test1" }};
	}
	private StringBuffer verificationErrors = new StringBuffer();
	@BeforeTest //Setup browser
	public void setUp() throws Exception {
		driver = BrowserUtility.SetBrowser();
	}
	//Verify to register a user
	@Test (dataProvider="dp", groups = {"sample", "Ready" })
	public void TS01_Register(String sUsername, String sPassword) {
		Reporter.log("Scenario: Verify registering an new user");
		assertTrue(NewTours_POM.Register(driver, sUsername, sPassword));
	}
	//Verify login user from Home page
	@Test (dataProvider="dp", groups = {"sample1", "Ready"})
	public void TS02_LoginHome(String sUsername, String sPassword){
		Reporter.log("Scenario: Verify login from home page");
		assertTrue(NewTours_POM.LoginHome(driver, sUsername, sPassword));
	}
	//Verify login from sign-on page
	@Test (dataProvider="dp", groups = {"sample1", "Ready"})
	public void TS03_UserLogin(String sUsername, String sPassword) {
		Reporter.log("Scenario: Verify login from login page and sanity");
		assertTrue(NewTours_POM.UserLogin(driver, sUsername, sUsername));
	}
	@Test (dataProvider="dp", groups = {"sample1", "Ready"})
	public void TS04_BookFlight(String sUsername, String sPassword) {
		Reporter.log("Scenario: Verify booking a flight with login");
		assertTrue(NewTours_POM.BookFlight(driver, sUsername, sPassword));
	}
	@AfterTest //Verify errors and close driver
	public void tearDown(){
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		} 
		driver.quit();
		Reporter.log("Browser closed");
	}
}
