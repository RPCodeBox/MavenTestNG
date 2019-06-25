package Listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.testng.IInvokedMethod;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.common.io.Files;

public class ReporterLib {
	private int m_row;
	private Integer m_testIndex;
	
	//generateFooterDetails - Function to add details of system
	void generateFooterDetails(PrintWriter m_out) throws UnknownHostException {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		long Time = System.currentTimeMillis();
		String SystemIP = null;
		try {
			InetAddress ownIP = Inet4Address.getLocalHost();
			SystemIP = ownIP.getHostAddress();
		} catch (Exception e) {
		}
		m_out.println("<B>System Details:</B><Br><Table id=\"Footer\">");
		m_out.println("<Tr><Td>Tester:</Td><Td>" + System.getProperty("user.name")
				+ "</Td><Td>Operating System:</Td><Td>" + System.getProperty("os.name") + "</Td></Tr>");
		m_out.println("<Tr><Td>IP Address: </Td><Td>" + SystemIP + "</Td><Td>Time Zone:</Td><Td>"
				+ Calendar.getInstance().getTimeZone().getDisplayName() + "</Td></Tr>");
		m_out.println("<Tr><Td>Date:</Td><Td>" + formatDate.format(Time) + "</Td><Td>Time:</Td><Td>"
				+ formatTime.format(Time) + "</Td></Tr>");
		m_out.println("<Table><Hr><I>SLK - TASO Test Automation Team</I>");
	}

	//createWriter: Function to initiate a print writer with return of writer steam
	protected PrintWriter createWriter(String outdir, String strResultFile) throws IOException {
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, strResultFile))));
	}

	//startHtml: Function to start html report with CSS styling and HTML header details
	protected void startHtml(PrintWriter out) {
		out.println(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>Test Automation Report</title>");
		out.println("<style type=\"text/css\">");
		out.println(
				"table {font-family:Tahoma;color:black;font-size:12px;width:1000px;margin-bottom:10px;border-collapse:collapse;background-color:#EBF5FB;empty-cells:show}");
		out.println("tr,td,th {border:1px solid #009;padding:.25em .5em}");
		out.println("body {font-family:Tahoma;color:black;font-size:12px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Project -Automation Execution Report</h3>");
	}

	//generateSuiteSummaryReport - Function to generate suite summary containing key metrics
	public void generateSuiteSummaryReport(List<ISuite> suites, PrintWriter m_out) {
		m_out.println("<B>Test Summary</B>");
		tableStart("testOverview", null, m_out);
		m_out.print(
				"<tr bgcolor=\"#D6EAF8\"><Th>Test-Suite</Th><Th>Passed</Th><Th>Failed</Th><Th>Total</Th><Th>Start Time</Th><Th>End Time</Th><Th>Total Time</Th><Tr>");
		NumberFormat formatter = new DecimalFormat("#,##0");
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_pass_s = 0;
		int qty_fail = 0;
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() > 1) {
				titleRow(suite.getName(), 8, m_out);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				startSummaryRow(overview.getName(), m_out);
				int q = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += q;
				summaryCell(q, Integer.MAX_VALUE, m_out);
				q = overview.getPassedTests().size();
				// qty_pass_s += q;
				// summaryCell(q, Integer.MAX_VALUE);
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				// summaryCell(q, 0,m_out);
				q = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += q;
				summaryCell(q, 0, m_out);
				int total = qty_pass_m + qty_fail;
				summaryCell(total, 0, m_out);
				time_start = Math.min(overview.getStartDate().getTime(), time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				summaryCell(ft.format(time_start), true, m_out);
				summaryCell(ft.format(time_end), true, m_out);
				summaryCell(
						formatter.format((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 60000.)
								+ " Mins",
						true, m_out);
				// summaryCell(overview.getIncludedGroups());
				// summaryCell(overview.getExcludedGroups());
				m_out.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 1) {
			m_out.println("<tr align='Center' class=\"total\"><td>Total</td>");
			summaryCell(qty_pass_m, Integer.MAX_VALUE, m_out);
			summaryCell(qty_pass_s, Integer.MAX_VALUE, m_out);
			// summaryCell(qty_skip, 0);
			summaryCell(qty_fail, 0, m_out);
			summaryCell(formatter.format((time_end - time_start) / 60000.) + " Mins", true, m_out);
			m_out.println("<td colspan=\"2\">&nbsp;</td></tr>");
		}
		m_out.println("</table>");
	}


	//generateMethodSummaryReport - Function to generate details of each method - pass & fail
	protected void generateMethodSummaryReport(List<ISuite> suites, PrintWriter m_out) {
		m_out.println("<B>Test Details</B>");
		startResultSummaryTable("methodOverview", m_out);
		int testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() > 1) {
				titleRow(suite.getName(), 5, m_out);
			}
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				m_testIndex = testIndex;
				resultSummary(suites, suite, testContext.getPassedTests(), testName, "Passed", "", m_out);
				resultSummary(suites, suite, testContext.getFailedTests(), testName, "Failed", "", m_out);
				resultSummary(suites, suite, testContext.getSkippedTests(), testName, "Skipped", "", m_out);
				testIndex++;
			}
		}
		m_out.println("</table>");
	}

	//resultSummary - Function to generate result summary
	private void resultSummary(List<ISuite> suites, ISuite suite, IResultMap tests, String testname, String style,
			String details, PrintWriter m_out) {
		if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			String[] strLogs;
			String strLogs1 = "";
			String strScript = "";
			String strScenario = "";
			String strScreenShot = "";
			//String strException = "";
			int mq = 0;
			String moduleName = "";
			SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				m_row += 1;
				strScenario = "";
				strScreenShot = "";
				ITestClass testClass = method.getTestClass();
				String className = testClass.getName();
				moduleName = lastClassName.substring(lastClassName.lastIndexOf(".") + 1, lastClassName.length());
				moduleName = moduleName.replace("D_", "").replace("M_", "");
				if (mq == 0) {
					String id = (m_testIndex == null ? null : "t" + Integer.toString(m_testIndex));
					titleRow(testname + " &#8212; " + style + details, 7, id, m_out);
					m_testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
					}
					mq = 0;
					buff.setLength(0);
					lastClassName = className;
				}
				long end = Long.MIN_VALUE;
				long start = Long.MAX_VALUE;
				for (ITestResult testResult : tests.getResults(method)) {
					if (testResult.getEndMillis() > end) {
						end = testResult.getEndMillis();
					}
					if (testResult.getStartMillis() < start) {
						start = testResult.getStartMillis();
					}
				}
				mq += 1;
				NumberFormat formatter = new DecimalFormat("#,##0");
				strLogs = getReporterLog(suites, method.getMethodName(), style).split("<>");
				for(int i=0;i<strLogs.length;i++) {
					if(strLogs[i].contains("Scenario")){
						strScenario = strLogs[i].replace("Scenario:", "").trim();
					}
				}
				for(int i=0;i<strLogs.length;i++) {
					if(strLogs[i].contains("ScreenShot")){
						strScreenShot = strLogs[i].replace("ScreenShot:", "").trim();
					}
				}
				for(int i=0;i<strLogs.length;i++) {
					if(!strLogs[i].contains("Scenario") && !strLogs[i].contains("ScreenShot")){
						strLogs1 += strLogs[i] + "<BR>";
					}
				}
				strScript = method.getMethodName();
				strScript = strScript.substring(strScript.lastIndexOf("_") + 1, strScript.length());
				
				String module = testClass.getName().replace("D_", "").replace("M_", "");
				module = module.substring(module.lastIndexOf(".") + 1, module.length());
				
				m_out.print("<tr><td>" + module + "</td><td align=\"left\">" + strScript + "</td><td>" + strScenario +
						"</td><td align=\"left\">" + strLogs1 + "</td><td>" +  strScreenShot + "</td><td>" +
						ft.format(start) + "</td><td>" + ft.format(end) + "</td><td align=\"center\">" + 
						formatter.format((end - start) / 1000) + "</td></tr>");
				strScript = "";
				strLogs1 = "";
			}
		}
	}

	//Function to get reporter logs for given test suite for parameterized methods
	protected String getReporterLog(List<ISuite> suites, String strMethod, String style) {
		List<String> msgs = null;
		String strLine = "";
		IResultMap tests = null;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				if (style.equalsIgnoreCase("passed")) {
					tests = testContext.getPassedTests();
				} else if (style.equalsIgnoreCase("failed")) {
					tests = testContext.getFailedTests();
				} else if (style.equalsIgnoreCase("skipped")) {
					tests = testContext.getFailedTests();
				}
				for (ITestResult result : tests.getAllResults()) {
					ITestNGMethod method = result.getMethod();
					msgs = Reporter.getOutput(result);
					if ((method.getMethodName() == strMethod) && (msgs.size() > 0)) {
						for (String line : msgs) {
							strLine += line.replace("<BR>", "") + "<>";
						}
					//Throwable exception=result.getThrowable();
					}
				}
				//strLine = strLine.replace("<BR>", "");
			}
		}
		return strLine;
	}

	//endHtml - Function to finish HTML stream
	protected void endHtml(PrintWriter out) {
		out.println("</body></html>");
	}

	//ArchiveFile - Function to archive test execution file with time stamp
	public String ArchiveFile(String outdir, String strResultFile) {
		SimpleDateFormat formatDate = new SimpleDateFormat("_yyMMdd-HHmm");
		long Time = System.currentTimeMillis();
		String workingDir = System.getProperty("user.dir");
		File targetDir = new File(workingDir + "\\ConsoleReports");
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		File source = new File(workingDir + "\\target\\surefire-reports\\" + strResultFile);
		// File source = new File(workingDir + "\\test-output\\" + strResultFile);
		File destination = new File(targetDir + "\\" + strResultFile);
		File destination1 = new File(targetDir + "\\Test_Automation-TestReport" + formatDate.format(Time) + ".html");
		try {
			Files.copy(source, destination);
			Files.copy(source, destination1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("*****Execution completed, Refer: " + destination1);
		return destination1.toString();
	}
// ================================================================================================================================
	//Utility Methods
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		Arrays.sort(r.toArray(new IInvokedMethod[r.size()]), new TestSorter());
		List<ITestNGMethod> result = Lists.newArrayList();
		for (IInvokedMethod m : r) {
			result.add(m.getTestMethod());
		}
		for (ITestNGMethod m : tests.getAllMethods()) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}

	//Utility Methods
	private void startResultSummaryTable(String style, PrintWriter m_out) {
		tableStart(style, "summary", m_out);
		m_out.println("<tr bgcolor=\"#D6EAF8\"><th>Module</th><th>Test Script</th><th style=\"width:100px\">Scenario</th>"
				+ "<th>Step Details</th><th style=\"width:30px\">Other Details</th><th style=\"width:30px\">Start Time</th>"
				+ "<th style=\"width:30px\">End Time</th><th style=\"width:30px\">Duration (Seconds)</th></tr>");
		m_row = 0;
	}

	//Utility Methods
	private void summaryCell(String v, boolean isgood, PrintWriter m_out) {
		m_out.print("<td style=\"text-align:center\" class=\"numi" + (isgood ? "" : "_attn") + "\">" + v + "</td>");
	}

	//Utility Methods
	private void startSummaryRow(String label, PrintWriter m_out) {
		m_row += 1;
		m_out.print("<tr" + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
				+ "><td style=\"text-align:center;padding-right:2em\">" + label + "</td>");
	}

	//Utility Methods
	private void summaryCell(int v, int maxexpected, PrintWriter m_out) {
		summaryCell(String.valueOf(v), v <= maxexpected, m_out);
	}

	//Utility Methods
	private void tableStart(String cssclass, String id, PrintWriter m_out) {
		m_out.println("<table cellspacing=\"0\" cellpadding=\"0\""
				+ (cssclass != null ? " class=\"" + cssclass + "\"" : " style=\"padding-bottom:2em\"")
				+ (id != null ? " id=\"" + id + "\"" : "") + ">");
		m_row = 0;
	}

	//Utility Methods
	private void titleRow(String label, int cq, PrintWriter m_out) {
		titleRow(label, cq, null, m_out);
	}

	//Utility Methods
	private void titleRow(String label, int cq, String id, PrintWriter m_out) {
		m_out.print("<tr");
		if (id != null) {
			m_out.print(" id=\"" + id + "\"");
		}
		m_out.println("><td align=\"Center\" colspan=\"" + cq + "\"><B>" + label + "</B></td></tr>");
		m_row = 0;
	}

	//Utility Methods
	public class TestSorter implements Comparator<IInvokedMethod> {
		public int compare(IInvokedMethod o1, IInvokedMethod o2) {
			return (int) (o1.getDate() - o2.getDate());
		}
	}
//==============================================================================================================================
	//sendMail - Function to send emails for queried eamil IDs
	public void sendMail(String strNewResultFile, String strToEmail) throws IOException {
		String strSMTP_Host = "smtp.gmail.com";
		String strSMTP_Port = "465";
		final String username = "";//
		final String password = "";
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long Time = System.currentTimeMillis();
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		 
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", strSMTP_Host);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", strSMTP_Port);
		props.setProperty("mail.smtp.socketFactory.port", strSMTP_Port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		
		try {
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			// -- Create a new message --
			Message msg = new MimeMessage(session);
			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(strToEmail, false));
			msg.setSubject("DXL Automation Test Report - " + ft.format(Time));
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(
					"Dear Recipient,\n\nPlease find attached test report for DXL Automation.\n\nRegards,\nDXL Automation Team");
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			Multipart multipart = new MimeMultipart();
			File att = new File(strNewResultFile);
			((MimeBodyPart) messageBodyPart).attachFile(att);
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(messageBodyPart1);
			// Send the complete message parts
			msg.setContent(multipart);
			msg.setSentDate(new Date());
			Transport.send(msg);
			System.out.println("*****Reporter: Email sent to Recipient - " + strToEmail);
		} catch (MessagingException e) {
			System.out.println("Error arising , cause: " + e);
		}
		
	}
//==============================================================================================================================
	//GetEnvironmentVariable - Function to get environment variable details
	public static String GetEnvironmentVariable(String strPOMFile, String strParameter) throws Exception {
		String strReturn = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(strPOMFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("properties");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				strReturn = eElement.getElementsByTagName(strParameter).item(0).getTextContent();
			}
		}
		return strReturn;
	}
//==============================================================================================================================
}
