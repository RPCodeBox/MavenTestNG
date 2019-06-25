/* FileName: TestReporter.Java
* Description: The code helps in generation of custom test execution report for CharlesHudson Automation Framework
* Disclaimer: The base code is a customization of TestNG's Email-able report format available at
* "https://github.com/cbeust/testng-eclipse/"and governed by Apache 2.0 Licenses.
*/
package Listener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.List;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

public class TASO_TestReporter implements IReporter  {
	public PrintWriter m_out;
	private static final Logger L = Logger.getLogger(TASO_TestReporter.class);
	String strResultFile = "";
	String strNewResultFile = "";
	String strToEmail = "";
	String strPOMFile = "./POM.xml";
	boolean sendEmail = false;
	
	//Main function to call the function for report header,  Summary, Footer, Archive and send email functions
	public void generateReport(List<XmlSuite> xml, List<ISuite> suites, String outdir) {
		ReporterLib Lib = new ReporterLib();
		strResultFile = "Test_Automation-TestReport.html";
		try {
			m_out = Lib.createWriter(outdir, strResultFile);
		} catch (IOException e) {
			L.error("output file", e);
			return;
		}
		Lib.startHtml(m_out);
		Lib.generateSuiteSummaryReport(suites, m_out);
		Lib.generateMethodSummaryReport(suites, m_out);
		try {
			Lib.generateFooterDetails(m_out);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Lib.endHtml(m_out);
		m_out.flush();
		m_out.close();
		strNewResultFile = Lib.ArchiveFile(outdir, strResultFile);
		if(sendEmail){
			try {
				strToEmail = ReporterLib.GetEnvironmentVariable(strPOMFile, "Test.EmailIDs");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(strToEmail!="" && strNewResultFile!=""){
				try {
					Lib.sendMail(strNewResultFile, strToEmail);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
//mvn install:install-file -Dfile=E:\PROJECTS\JavaWorkSpace\CH_MavenTestNG\src\main\java\com\ch\listeners\ReporterJar.jar -DgroupId=com.ch.listeners -DartifactId=ReportUtil -Dversion=1.0.0  -Dpackaging=jar -DgeneratePom=true
//https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project
//http://roufid.com/3-ways-to-add-local-jar-to-maven-project/
