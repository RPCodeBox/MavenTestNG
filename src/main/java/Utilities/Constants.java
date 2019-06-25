package Utilities;

import drivers.Keys;

public interface Constants extends Keys {

	public static String strPOM = "./pom.xml";

	public static String URL = getXML("Application.URL");
	public static String Browser = getXML("Application.Browser");
	
	public static String CSV1 = "./src/main/resources/TestData/Config.csv";
	public static String CSV2 = "./src/main/java/ObjectLibrary/ObjectLibrary.csv";
	
	public static String CSV3 = "./src/main/resources/TestData/TestData01.csv";
	
	static String getXML(String strValue) {
		String strReturn = "";
		try {
			return XMLUtility.GetSuiteVariable(strPOM, strValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strReturn;
	}
}
