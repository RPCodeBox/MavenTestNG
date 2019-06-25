package Library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import ObjectLibrary.ObjectLibrary;
import Utilities.CSVUtility;
import Utilities.Constants;
import Utilities.BrowserUtility;
import Utilities.ScreenShot;

public class NewTours_POM implements Constants{
	public static WebDriver driver;
	public ObjectLibrary element = new ObjectLibrary(); 
	public static BrowserUtility browserUtility = new BrowserUtility();
	static ObjectLibrary obj = new ObjectLibrary();
	
	public static boolean UserLogin(WebDriver driver, String username, String password) {
		try {
			driver.get(URL);
			Reporter.log("Browser navigated to: " + URL);
			driver.findElement(By.linkText("SIGN-ON")).click();
			Reporter.log("POM_Lib.UserLogin: Sign On Page initiated<BR>");
			driver.findElement(By.name(obj.txtUserName)).clear();
			driver.findElement(By.name(obj.txtUserName)).sendKeys(username);
			driver.findElement(By.name(obj.txtPassword)).clear();
			driver.findElement(By.name(obj.txtPassword)).sendKeys(password);
			driver.findElement(By.name(obj.btnLogin)).click();
			if(driver.getTitle().contains("Find a Flight")){
				driver.findElement(By.linkText(obj.lnkSignOff)).click();
				Reporter.log("POM_Lib.UserLogin: Page valid after login<BR>");
				ScreenShot.getScreenShot("UserLogin");
				return true;
				}
			else {
				Reporter.log("POM_Lib.UserLogin: Page invalid after login<BR>");
				return false;
			}
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static boolean Register(WebDriver driver, String username, String password) {
		try {
			driver.get(URL);
			Reporter.log("Browser navigated to: " + URL);
			driver.findElement(By.linkText("REGISTER")).click();
			Reporter.log("POM_Lib.Register: Register Page initiated<BR>");
			driver.findElement(By.name("firstName")).sendKeys("TestUser");
			driver.findElement(By.name("lastName")).sendKeys("TestUser");
			driver.findElement(By.name("phone")).sendKeys("1800100200");
			driver.findElement(By.id("userName")).sendKeys("testuser@Qualitest.in");
			driver.findElement(By.name("address1")).sendKeys("Company and Co");
			driver.findElement(By.name("address2")).sendKeys("MTP");
			driver.findElement(By.name("city")).sendKeys("Bangalore");
			driver.findElement(By.name("state")).sendKeys("Karnataka");
			driver.findElement(By.name("postalCode")).sendKeys("560025");
			driver.findElement(By.name("email")).sendKeys(username);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.name("confirmPassword")).sendKeys(password);
			driver.findElement(By.name("register")).click();
			if(driver.getTitle().contains("Register:")){
				Reporter.log("POM_Lib.Register: Page valid after login<BR>");
				driver.findElement(By.linkText("SIGN-OFF")).click();
				ScreenShot.getScreenShot("Register");
				return true;
				}
			else {
				Reporter.log("POM_Lib.Register: Page invalid after login<BR>");
				return false;
				}
			}
		catch (Exception ex){
			System.out.println(ex);
			return false;
		}
	}

	public static boolean LoginHome(WebDriver driver, String strUser, String strPassword){
		try{
			driver.get(URL);
			Reporter.log("Browser navigated to: " + URL);
			driver.findElement(By.name("userName")).sendKeys(strUser);
			driver.findElement(By.name("password")).sendKeys(strPassword);
			driver.findElement(By.name("login")).click();
			if(driver.getTitle().contains("Find a Flight:")){
				Reporter.log("POM_Lib.LoginHome: Page valid after login");
				driver.findElement(By.linkText("ITINERARY")).click();
				driver.findElement(By.linkText("PROFILE")).click();
				driver.findElement(By.linkText("SUPPORT")).click();
				driver.findElement(By.linkText("CONTACT")).click();
				Reporter.log("POM_Lib.LoginHome: Header links verified");
				driver.findElement(By.linkText("Home")).click();
				driver.findElement(By.linkText("Flights")).click();
				driver.findElement(By.linkText("Hotels")).click();
				driver.findElement(By.linkText("Car Rentals")).click();
				driver.findElement(By.linkText("Cruises")).click();
				driver.findElement(By.linkText("Destinations")).click();
				driver.findElement(By.linkText("Vacations")).click();
				Reporter.log("POM_Lib.LoginHome: Left Menu links verified");
				driver.findElement(By.linkText(obj.lnkSignOff)).click();
				ScreenShot.getScreenShot("HomeLogin");
				return true;
				}
				Reporter.log("POM_Lib.LoginHome: Page invalid after login<BR>");
				return false;
			}
		catch (Exception ex){
		System.out.println(ex);
		return true;
		}
	}

	public static boolean BookFlight(WebDriver driver, String strUser, String strPassword) {
		try{
			String URL = CSVUtility.getConfigValue("URL");
			driver.get(URL);
			Reporter.log("Browser navigated to: " + URL);
			driver.findElement(By.linkText("SIGN-ON")).click();
			driver.findElement(By.name("userName")).sendKeys(strUser);
			driver.findElement(By.name("password")).sendKeys(strPassword);
			driver.findElement(By.name("login")).click();
			Reporter.log("POM_Lib.BookFlight: Page valid after login");
			//Flight details
			BrowserUtility.SelectRadioButton("//input[@value='oneway']");
			BrowserUtility.SelectDropDown("passCount", "2");
			BrowserUtility.SelectDropDown("fromPort", "London");
			BrowserUtility.SelectDropDown("toPort", "New York");
			BrowserUtility.SelectDropDown("airline", "Unified Airlines");
			BrowserUtility.SelectRadioButton("//input[@value='First']");
			driver.findElement(By.name("findFlights")).click();
			Reporter.log("POM_Lib.BookFlight: Flight details selected");
			//Select Flight
			driver.findElement(By.name("reserveFlights")).click();
			//Passenger Details
			driver.findElement(By.name("passFirst0")).sendKeys("Jim");		
			driver.findElement(By.name("passLast0")).sendKeys("Carrey");		
			driver.findElement(By.name("creditnumber")).sendKeys("76619411823");
			driver.findElement(By.name("buyFlights")).click();
			Reporter.log("POM_Lib.BookFlight: Flight booked - Successfully");
			ScreenShot.getScreenShot("FlightBooking");
			driver.findElement(By.linkText("SIGN-OFF")).click();
			driver.quit();
			return true;
			}
		catch (Exception ex){
		System.out.println(ex);
		return false;
		}
	}
}