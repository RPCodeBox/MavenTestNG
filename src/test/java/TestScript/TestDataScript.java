package TestScript;

import org.testng.annotations.*;

import Utilities.CSVUtility;
import Utilities.Constants;
import Utilities.TestData01;
import java.util.List;
import java.io.IOException;

public class TestDataScript implements Constants{
	//Sample method to call getCSVData function though TestNG Data Provider
	//public static String CSV3 = "./src/main/resources/TestData/TestData01.csv";
	
	@DataProvider(name = "TestData01")
	public static Object[][] TestData01() throws IOException{
		String strTestDataFile = CSV3;
		return CSVUtility.getCSVData(strTestDataFile);
	}

	//TestData01.csv file used an array object for test data usage
	@Test(dataProvider = "TestData01")
	public void test1(String strField1, String strField2, String strField3, String strField4, String strField5){
		System.out.println(strField1 + " : " + strField2 + " : " + strField3 + " : " + strField4 + " : " + strField5);
		//Test steps to follow
	}

	//TestData01.csv file used as an List collection for a test
	@Test()
	public void test2() throws Exception{
		List<TestData01> list = CSVUtility.getSampleTestData(CSV3);
    	for(TestData01 c:list) {
			System.out.println(c.Field1 + " " + c.Field2 + " " + c.Field3 + " " + c.Field4 + " " + c.Field5);
			//Test steps to follow
    	}
	}

	//Config CSV file used to read and update (set) a specific values and Object library used for reading specific object property 
	@Test
    public void test3() throws IOException {
    	System.out.println("My URL: " + CSVUtility.getConfigValue("URL"));
		System.out.println("Page load: " + CSVUtility.getConfigValue("PAGE_LOAD"));
		System.out.println(CSVUtility.setConfigValue("PAGE_LOAD", "50"));
		System.out.println("Page load (updated): " + CSVUtility.getConfigValue("PAGE_LOAD"));
		System.out.println("Element: " + CSVUtility.getObjectValue("COUNTRY"));
		System.out.println("Element: " + CSVUtility.getObjectValue("M_REG_COMPANY"));
    }
}
