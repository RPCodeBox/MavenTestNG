/* CSVUtility - Class library functions
 * Author: Rana Pratap Singh
 * Version: 1.0 - Aug 2018
 * Description: Key files - 
 * Test Configuration (Config) getConfigData, getConfigValue, setConfigValue and 
 * 		CSVUtility.getConfigData(ConfigPath);
 *		CSVUtility.getConfigValue(strField);
 *		CSVUtility.setConfigValue(strField, strValue);
 * Object Library - getObjectLibraryData, getObjectValue
 *		CSVUtility.getObjectLibraryData(ObjectFile);
 *		CSVUtility.getObjectValue(strElement);
 * CSV General functions - getCSVData, getRowCount, getColumnsCount
 *		CSVUtility.getCSVData(CSVPath);
 *		CSVUtility.getSampleTestData(DataPath);
 */
package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.io.FileInputStream;

import java.io.FileWriter;

public class CSVUtility implements Constants {
	public static List<ConfigFile> configSet = getConfigData(CSV1);
	public static List<ObjectFile> objRepo = getObjectLibraryData(CSV2);

	private static String strReturn = null;

	// ================================================================================================================
	// CONFIG FILE FUNCTIONS
	// ================================================================================================================
	/*
	 * Function: getConfigCSVData(String ConfigPath) Description: To get data as
	 * ArrayList for the application objects Return: Application objects referred in
	 * ObjectFile path ObjectFile is a construct with columns for Module, Element,
	 * ID and Value
	 */
	public static List<ConfigFile> getConfigData(String ConfigPath) {
		Object[][] ObjectData = null;
		ArrayList<ConfigFile> config = null;
		try {
			ObjectData = getCSVData(ConfigPath);
			if (ObjectData.length == 0 || ObjectData == null) {
				return null;
			}
			List<Object[]> list = java.util.Arrays.asList(ObjectData);
			config = new ArrayList<ConfigFile>();
			for (Object[] data : list) {
				ConfigFile C = new ConfigFile(data[0].toString(), data[1].toString());
				config.add(C);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	/*
	 * Function: GetCSVValue Description: Returns specific value of a collection
	 * Description: Returns a specific fileds values
	 */
	public static String getConfigValue(String strField) throws IOException {
		if (configSet.isEmpty()) {
			return null;
		}
		Predicate<ConfigFile> byField = e -> e.Field.contains(strField);
		configSet.stream().filter(byField).forEach(e -> strReturn = e.Value);
		for (ConfigFile s : configSet) {
			if (s.Field.equalsIgnoreCase(strField)) {
				strReturn = s.Value;
			}
		}
		return strReturn;
	}

	/*
	 * Function: updateConfigValue Description: To update a particular value in
	 * ConfigFile Parameters: strField - 1st Column Field, 2nd Column Value
	 */
	public static boolean setConfigValue(String strField, String strValue) throws IOException {
		int i = 0;
		if (configSet.isEmpty()) {
			return false;
		}
		for (ConfigFile c : configSet) {
			if (c.Field.equalsIgnoreCase(strField)) {
				configSet.set(i, new ConfigFile(strField, strValue));
			}
			i++;
		}
		FileWriter fileWriter = null;
		fileWriter = new FileWriter(CSV1);
		for (ConfigFile c : configSet) {
			fileWriter.append(String.valueOf(c.Field) + ",");
			fileWriter.append(String.valueOf(c.Value) + "\n");
		}
		fileWriter.flush();
		fileWriter.close();
		return true;
	}

	// ================================================================================================================
	// OBJECT LIBRARY FILE FUNCTIONS
	// ================================================================================================================
	/*
	 * Function: getObjectLibraryData(String ObjectFile) Description: To get data as
	 * ArrayList for the application objects Return: Application objects referred in
	 * ObjectFile path ObjectFile is a construct with columns for Module, Element,
	 * ID and Value
	 */
	public static List<ObjectFile> getObjectLibraryData(String ObjectFile) {
		Object[][] ObjectData = null;
		ArrayList<ObjectFile> objectSet = null;
		try {
			ObjectData = getCSVData(ObjectFile);
			if (ObjectData.length == 0 || ObjectData == null) {
				return null;
			}
			List<Object[]> list = java.util.Arrays.asList(ObjectData);
			objectSet = new ArrayList<ObjectFile>();
			for (Object[] data : list) {
				ObjectFile C = new ObjectFile(data[0].toString(), data[1].toString(), data[2].toString(),
						data[3].toString());
				objectSet.add(C);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectSet;
	}

	/*
	 * Function: getElementValue Description: Returns specific value of a collection
	 */
	public static String getObjectValue(String strElement) throws IOException {
		String strReturn = null;
		for (ObjectFile s : objRepo) {
			if (s.Element.equalsIgnoreCase(strElement)) {
				strReturn = s.Value;
			}
		}
		return strReturn;
	}

	// ================================================================================================================
	// SAMPLE TEST DATA FUNCTIONS
	// ================================================================================================================

	public static List<TestData01> getSampleTestData(String DataPath) {
		Object[][] ObjectData = null;
		ArrayList<TestData01> testData = null;
		try {
			ObjectData = getCSVData(DataPath);
			if (ObjectData.length == 0 || ObjectData == null) {
				return null;
			}
			List<Object[]> list = java.util.Arrays.asList(ObjectData);
			testData = new ArrayList<TestData01>();
			for (Object[] data : list) {
				TestData01 C = new TestData01(data[0].toString(), data[1].toString(), data[2].toString(),
						data[3].toString(), data[4].toString());
				testData.add(C);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return testData;
	}

	// ================================================================================================================
	// GENERAL CSV FILES FUNCTIONS
	// ================================================================================================================
	// getCSVData - Function to get data from specified CSV file name, Returns an
	// array as output
	public static Object[][] getCSVData(String CSVPath) throws IOException {
		File file = new File(CSVPath);
		if (!file.isFile() || !file.exists() || !CSVPath.endsWith(".csv")) {
			System.out.println("CSVUtility Error: '" + CSVPath + "' File not found OR Unsupported file format");
			return null;
		}
		String strrowdta, strrowinfo[];
		Object[][] strData = null;
		if (!CSVPath.endsWith(".csv")) {
			System.out.println("Test data error: Unsupported file format");
			return null;
		}
		int introwcount = getRowCount(CSVPath);
		int intcolcount = getColumnsCount(CSVPath);
		strData = new Object[introwcount][intcolcount];
		try {
			InputStream inputfile = new FileInputStream(new File(CSVPath));
			BufferedReader bfrreader = new BufferedReader(new InputStreamReader(inputfile));
			int rowcnt = 0;
			while ((strrowdta = bfrreader.readLine()) != null) {
				strrowinfo = strrowdta.split(",");
				for (int i = 0; i < strrowinfo.length; i++) {
					strData[rowcnt][i] = strrowinfo[i];
				}
				rowcnt++;
			}
			bfrreader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strData;
	}

	// getRowCount - Function to get row count from specified CSV File
	private static int getRowCount(String strFileName) throws IOException {
		InputStream inputfile = new FileInputStream(new File(strFileName));
		BufferedReader bfrReader = new BufferedReader(new InputStreamReader(inputfile));
		int rowscount = 0;
		while (bfrReader.readLine() != null) {
			rowscount++;
		}
		bfrReader.close();
		return rowscount;
	}

	// getColumnsCount- Function to get column count from specified CSV File
	private static int getColumnsCount(String strFileName) throws IOException {
		InputStream inputfile = new FileInputStream(new File(strFileName));
		BufferedReader bfrReader = new BufferedReader(new InputStreamReader(inputfile));
		int columncnt = bfrReader.readLine().split(",").length;
		bfrReader.close();
		return columncnt;
	}
}

// Construct for ConfiFile
class ConfigFile {
	String Field;
	String Value;

	public ConfigFile(String Field, String Value) {
		this.Field = Field;
		this.Value = Value;
	}

	public String getField() {
		return Field;
	}

	public void setField(String Field) {
		this.Field = Field;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String Value) {
		this.Value = Value;
	}

	@Override
	public String toString() {
		return "\nField= " + getField() + "::Value= " + getValue();
	}
}

