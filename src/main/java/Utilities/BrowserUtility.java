package Utilities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class BrowserUtility implements Constants {
	public static WebDriver driver;

	public static WebDriver SetBrowser() throws Exception {
		System.setProperty(ChromeDriverKey, ChromeDriverPath);
		System.setProperty(FireFoxDriverKey, FireFoxDriverPath);
		System.setProperty(IEDriverKey, IEDriverPath);
		System.setProperty(EdgeDriverKey, EdgeDriverPath);
		if (Browser.equalsIgnoreCase("CHROME")) {
			driver = new ChromeDriver();
		} else if (Browser.equalsIgnoreCase("FIREFOX")) {
			driver = new FirefoxDriver();
		} else if (Browser.equalsIgnoreCase("EDGE")) {
			driver = new EdgeDriver();
		} else if (Browser.equalsIgnoreCase("IE")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		Reporter.log("Utility: Browser initiated " + Browser);
		return driver;
	}

	public static void SelectDropDown(String ddObject, String Value) {
		Select Object = new Select(driver.findElement(By.name(ddObject)));
		Object.selectByVisibleText(Value);
	}

	public static void SelectRadioButton(String RDObject) {
		WebElement radio1 = driver.findElement(By.xpath(RDObject));
		radio1.click();
	}

	public static final WebDriver getDriver() {
		return driver;
	}
}
