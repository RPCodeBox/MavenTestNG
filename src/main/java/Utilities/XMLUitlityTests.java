package Utilities;

public class XMLUitlityTests {
	
	public static void main(String[] args) throws Exception{
		String strPOMXML="./pom.xml";

		System.out.println("Application.URL: " + XMLUtility.GetSuiteVariable(strPOMXML, "Application.URL"));
		System.out.println("Application.Platform: " + XMLUtility.GetSuiteVariable(strPOMXML, "Application.Platform"));
		System.out.println("Application.Browser: " + XMLUtility.GetSuiteVariable(strPOMXML, "Application.Browser"));
		System.out.println("Test.EmailIDs: " + XMLUtility.GetSuiteVariable(strPOMXML, "Test.EmailIDs"));
		System.out.println("Set Test.EmailIDs: " + XMLUtility.SetSuiteVariable(strPOMXML, "Test.EmailIDs", "rpsingh@chtsinc.com"));
		System.out.println("Test.EmailIDs: " + XMLUtility.GetSuiteVariable(strPOMXML, "Test.EmailIDs"));
 
		System.out.println("Read Variable1: " + XMLUtility.GetTestVariable(strPOMXML, "variables", "Variable1"));
		System.out.println("Read Variable2: " + XMLUtility.GetTestVariable(strPOMXML, "variables", "Variable2"));
		System.out.println("Set Variable5: " + XMLUtility.SetTestVariable(strPOMXML, "variables", "Variable5", "NewValue5"));
		System.out.println("Read Variable5: " + XMLUtility.GetTestVariable(strPOMXML, "variables", "Variable5"));
		System.out.println("Set Variable6: " + XMLUtility.CreateTestVariable(strPOMXML, "variables", "Variable6", "NewValue6"));
		System.out.println("Read Variable6: " + XMLUtility.GetTestVariable(strPOMXML, "variables", "Variable6"));
		System.out.println("Remove Variable6: " + XMLUtility.RemoveTestVariable(strPOMXML, "variables", "Variable6"));
		System.out.println("Read Variable6: " + XMLUtility.GetTestVariable(strPOMXML, "variables", "Variable6"));
	}
}
