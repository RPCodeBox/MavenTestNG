/* Author: Rana Pratap (rpsingh@chtsinc.com)
 * Date: 20th July 2018
 * Version: 1.0
 * Purpose: User function to work with XML files at suite level and 
 * GetSuiteVariable(String strPOMXML, String strParameter)
 * SetSuiteVariable(String strPOMXML, String strParameter, String strValue)
 * 
 * GetTestVariable(String strXML, String strParent, String strParameter)
 * SetTestVariable(String strXML, String strParent, String strVariable, String strValue)
 * CreateTestVariable(String strXML, String strParent, String strVariable, String strValue)
 * RemoveTestVariable(String strXML, String strParent, String strVariable)
 * 
 * SaveXmlFile(String strXML, Document doc)
 */

package Utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLUtility {
	public static String GetSuiteVariable(String strPOMXML, String strParameter) throws Exception {
		String strReturn = null;
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strPOMXML);
		doc.getDocumentElement().normalize();
		Node propertyNode = doc.getElementsByTagName("properties").item(0);
		if(propertyNode==null) {
			System.out.println("XML Utility Error: XML Parent node 'properties' - Not found" );
			return strReturn;
		}
	    for (Node propertyName = propertyNode.getFirstChild(); propertyName != null; propertyName = propertyName.getNextSibling()) {
	        if (propertyName instanceof Element && strParameter.equalsIgnoreCase(propertyName.getNodeName())){
	        	strReturn = propertyName.getTextContent();
	        }
	      }
		return strReturn;
	}

	public static Boolean SetSuiteVariable(String strPOMXML, String strParameter, String strValue) throws Exception {
		Boolean strReturn = false;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strPOMXML);
		doc.getDocumentElement().normalize();
		Node propertyNode = doc.getElementsByTagName("properties").item(0);
		if(propertyNode==null) {
			System.out.println("XML Utility Error: XML Parent node 'properties' - Not found" );
			return strReturn;
		}
	    for (Node propertyName = propertyNode.getFirstChild(); propertyName != null; propertyName = propertyName.getNextSibling()) {
	        if (propertyName instanceof Element && propertyName.getNodeName().equalsIgnoreCase(strParameter)){
	        	propertyName.setTextContent(strValue);
	        	if(!SaveXmlFile(strPOMXML, doc)){
	        		return strReturn;
	        	}	
	        	strReturn = true;
	        }
	      }
		return strReturn;
	}

	public static String GetTestVariable(String strXML, String strParent, String strParameter) throws Exception {
		String strReturn = null;
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strXML);
		doc.getDocumentElement().normalize();
		Node variableNode = doc.getElementsByTagName(strParent).item(0);
		if(variableNode==null) {
			System.out.println("XML Utility Error: XML Parent node '" + strParent + "' - Not found" );
			return strReturn;
		}
	    for (Node variableName = variableNode.getFirstChild(); variableName != null; variableName = variableName.getNextSibling()) {
	        if (variableName instanceof Element && strParameter.equalsIgnoreCase(variableName.getNodeName())){
	        	strReturn = variableName.getTextContent();
	        }
	      }
		return strReturn;
	}

	public static Boolean SetTestVariable(String strXML, String strParent, String strVariable, String strValue) throws Exception {
		Boolean strReturn = false;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strXML);
		doc.getDocumentElement().normalize();
		Node variableNode = doc.getElementsByTagName(strParent).item(0);
		if(variableNode==null) {
			System.out.println("XML Utility Error: XML Parent node '" + strParent + "' - Not found" );
			return strReturn;
		}
	    for (Node variableName = variableNode.getFirstChild(); variableName != null; variableName = variableName.getNextSibling()) {
	        if (variableName instanceof Element && variableName.getNodeName().equalsIgnoreCase(strVariable)){
	        	variableName.setTextContent(strValue);
	        	if(!SaveXmlFile(strXML, doc)){
	        		return strReturn;
	        	}	
	        	strReturn = true;
	        }
	      }
		return strReturn;
	}

	public static Boolean CreateTestVariable(String strXML, String strParent, String strVariable, String strValue) throws Exception {
		Boolean strReturn = false;
		int intCount = 0;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strXML);
		doc.getDocumentElement().normalize();
		Node variableNode = doc.getElementsByTagName(strParent).item(0);
		if(variableNode==null) {
			System.out.println("XML Utility Error: XML Parent node '" + strParent + "' - Not found" );
			return strReturn;
		}
	    for (Node variableName = variableNode.getFirstChild(); variableName != null; variableName = variableName.getNextSibling()) {
	        if (variableName instanceof Element && variableName.getNodeName().equalsIgnoreCase(strVariable)){
	        	variableName.setTextContent(strValue);
	        	intCount++;
	        }
	    }
	    if(intCount==0){
	       	Element child = (Element) doc.createElement(strVariable);
	    	variableNode.appendChild(child);
	        child.setNodeValue(strVariable);
	        child.setTextContent(strValue);
	    }
    	if(!SaveXmlFile(strXML, doc)){
    		return strReturn;
    	}	
    	strReturn = true;
		return strReturn;
	}

	public static Boolean RemoveTestVariable(String strXML, String strParent, String strVariable) throws Exception {
		Boolean strReturn = false;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(strXML);
		doc.getDocumentElement().normalize();
		Node variableNode = doc.getElementsByTagName(strParent).item(0);
		if(variableNode==null) {
			System.out.println("XML Utility Error: XML Parent node '" + strParent + "' - Not found" );
			return strReturn;
		}
	    for (Node variableName = variableNode.getFirstChild(); variableName != null; variableName = variableName.getNextSibling()) {
	        if (variableName instanceof Element && variableName.getNodeName().equalsIgnoreCase(strVariable)){
	        	variableNode.removeChild(variableName);
	        	if(!SaveXmlFile(strXML, doc)){
	        		return strReturn;
	        	}	
	        	strReturn = true;
	        }
	      }
		return strReturn;
	}

	private static Boolean SaveXmlFile(String strXML, Document doc) {
		Boolean strReturn = false;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer;
			transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    DOMSource source = new DOMSource(doc);
		    OutputStream stream = new FileOutputStream(strXML);
		    StreamResult sresult = new StreamResult(stream);
		    transformer.transform(source, sresult);
		    strReturn = true;
		} catch (TransformerConfigurationException e) {
			System.out.println("XML Utility Error: '" + e.toString() + "'");
		} catch (TransformerException e) {
			System.out.println("XML Utility Error: '" + e.toString() + "'");
		} catch (FileNotFoundException e) {
			System.out.println("XML Utility Error: '" + e.toString() + "'");
		}
		return strReturn;
	}
}
