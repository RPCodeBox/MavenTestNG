package ObjectLibrary;

import java.io.IOException;
import Utilities.CSVUtility;

public class ObjectLibrary{
	public String txtUserName = getValue("USERNAME");
	public String txtPassword = getValue("PASSWORD");
	public String btnLogin = getValue("LOGIN");
	public String lnkSignOff = getValue("SIGNOFF");
	
	private static String getValue(String strValue) {
		String strReturn = "";
		try {
			strReturn = CSVUtility.getObjectValue(strValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strReturn;
	}	
}

//@FindBy(id="AddressForm.address2")
//public WebElement ADDRESS_LINE2;
//WebElement element = CSVUtility.getObjectValue("USERNAME");